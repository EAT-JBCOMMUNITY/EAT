package org.jboss.hal.testsuite.fragment.config.messaging;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProviderSettingsWindow extends WizardWindow {

    private static final By CONFIG_FRAGMENT_SELECTOR = ByJQuery.selector(".default-tabpanel");

    public void switchTo(String label) {
        By selector = ByJQuery.selector("div.gwt-Label:contains('" + label + "')");
        Graphene.waitModel(browser).until().element(selector).is().visible();
        browser.findElement(selector).click();
        Graphene.waitModel(browser).until().element(CONFIG_FRAGMENT_SELECTOR).is().visible();
    }

    public ProviderSettingsWindow switchToClusterCredentialReferenceTab() {
        switchTo("Cluster Credential Reference");
        return this;
    }

    public ProviderSettingsWindow switchToJournalTab() {
        switchTo("Journal");
        return this;
    }

    public ProviderSettingsWindow switchToSecurityTab() {
        switchTo("Security");
        return this;
    }

    public ProviderSettingsWindow maximize() {
        maximizeWindow();
        return this;
    }

    public ConfigFragment getConfigFragment() {
        final WebElement contentRoot = root.findElement(CONFIG_FRAGMENT_SELECTOR);
        ConfigFragment configFragment = Graphene.createPageFragment(ConfigFragment.class, contentRoot);
        maximize();
        return configFragment;
    }
}
