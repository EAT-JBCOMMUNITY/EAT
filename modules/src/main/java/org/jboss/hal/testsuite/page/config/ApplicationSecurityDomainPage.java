package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.undertow.AddApplicationSecurityDomainWizard;
import org.jboss.hal.testsuite.fragment.config.undertow.AddSSOWizard;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Class representing page for setting Application Security Domain in Undertow subsystem
 */
public class ApplicationSecurityDomainPage extends ConfigurationPage {

    public void navigate() {
        getSubsystemNavigation("Undertow")
                .step(FinderNames.SETTINGS, "HTTP")
                .selectRow()
                .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        switchTab("Application Security Domain");
    }

    public AddApplicationSecurityDomainWizard addApplicationSecurityDomain() {
        return getResourceManager().addResource(AddApplicationSecurityDomainWizard.class);
    }

    @Override
    public ConfigAreaFragment getConfig() {
        final By selector = ByJQuery.selector(".default-tabpanel.master_detail-detail:visible");
        final WebElement root = getContentRoot().findElement(selector);
        return Graphene.createPageFragment(ConfigAreaFragment.class, root);
    }

    public AddSSOWizard enableSSO() {
        clickButton("Enable Single Sign On");
        return Console.withBrowser(browser).openedWizard(AddSSOWizard.class);
    }

    public ConfirmationWindow disableSSO() {
        clickButton("Disable Single Sign On");
        return Console.withBrowser(browser).openedWindow(ConfirmationWindow.class);
    }

    public ConfigFragment switchToSSOConfigTab() {
        return getConfig().switchTo("Single Sign On");
    }

    public ConfigFragment swithToSSOCredentialReferenceTab() {
        return getConfig().switchTo("SSO - Credential Reference");
    }

}
