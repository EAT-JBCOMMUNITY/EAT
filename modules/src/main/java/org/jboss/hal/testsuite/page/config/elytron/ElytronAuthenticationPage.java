package org.jboss.hal.testsuite.page.config.elytron;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.util.Console;

public class ElytronAuthenticationPage extends ConfigPage {

    @Override
    public void navigate() {
        getSubsystemNavigation("Security - Elytron")
                .step(FinderNames.SETTINGS, "Security Realm / Authentication")
                .selectRow()
                .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        switchTab("Authentication");
    }

    public ElytronAuthenticationPage switchToAuthenticationConfiguration() {
        switchSubTab("Authentication Configuration");
        return this;
    }

    public ElytronAuthenticationPage switchToAuthenticationContext() {
        switchSubTabByExactText("Authentication Context");
        return this;
    }
}
