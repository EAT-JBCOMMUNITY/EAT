package org.jboss.hal.testsuite.page.config.elytron;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.util.Console;

public class SecurityRealmMapperPage extends ConfigPage {

    @Override
    public void navigate() {
        getSubsystemNavigation("Security - Elytron")
                .step(FinderNames.SETTINGS, "Security Realm / Authentication")
                .selectRow()
                .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        switchTab("Security Realm Mapper");
    }

    public SecurityRealmMapperPage switchToSimpleRegexRealmMappers() {
        switchSubTab("Simple Regex Realm Mapper");
        return this;
    }

    public SecurityRealmMapperPage switchToConstantRealmMappers() {
        switchSubTab("Constant Realm Mapper");
        return this;
    }

    public SecurityRealmMapperPage switchToCustomRealmMappers() {
        switchSubTab("Custom Realm Mapper");
        return this;
    }
}
