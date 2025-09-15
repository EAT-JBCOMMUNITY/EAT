package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.runtime.elytron.AliasWizard;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.page.MetricsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ElytronRuntimePage extends MetricsPage {

    private static final String ElYTRON_RUNTIME_LABEL = "Security - Elytron";
    private static final String REFRESH_BUTTON_LABEL = "Refresh";
    private static final String ALIASES_TAB_LABEL = "Aliases";

    @Override
    public void navigate() {
        navigate2runtimeSubsystem(ElYTRON_RUNTIME_LABEL);
    }

    public void navigateToServer(String serverName) {
        navigate2runtimeSubsystem(ElYTRON_RUNTIME_LABEL, serverName);
    }

    public void refreshCredentialStores() {
        clickButton(REFRESH_BUTTON_LABEL);
    }

    public AliasWizard invokeAddAlias() {
        return getConfig().switchTo(ALIASES_TAB_LABEL).getResourceManager().addResource(AliasWizard.class);
    }

    public ResourceManager getAliases() {
        return getConfig().switchTo(ALIASES_TAB_LABEL).getResourceManager();
    }

    public ResourceManager getCredentialStores() {
        return getResourceManager();
    }

    public void selectCredentialStore(String credentialStoreName) {
        getCredentialStores().selectByName(credentialStoreName);
    }

    public void selectAlias(String aliasName) {
        getAliases().selectByName(aliasName);
    }

    public ConfigFragment getWindowFragment() {
        WebElement windowElement = browser.findElement(By.className("default-window-content"));
        return Graphene.createPageFragment(ConfigFragment.class, windowElement);
    }
}
