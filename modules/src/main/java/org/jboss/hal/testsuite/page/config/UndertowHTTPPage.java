package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 15.9.15.
 */
public class UndertowHTTPPage extends UndertowPage implements Navigatable {

    @Override
    public void navigate() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, "full");
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navigation.step(FinderNames.SUBSYSTEM, "Undertow")
                .step("Settings", "HTTP")
                .selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        switchTab("HTTP Server");
        Console.withBrowser(browser)
                .waitUntilLoaded()
                .dismissReloadRequiredWindowIfPresent();
        Console.withBrowser(browser).waitForContent();
    }

    public UndertowHTTPPage selectHTTPServer(String serverName) {
        getResourceManager().selectByName(serverName);
        return this;
    }

    public UndertowHTTPPage viewHTTPServer(String serverName) {
        getResourceManager().viewByName(serverName);
        Console.withBrowser(browser).waitUntilLoaded();
        return this;
    }

    public UndertowHTTPPage switchToAJPListeners() {
        switchSubTab("AJP Listener");
        return this;
    }

    public UndertowHTTPPage switchToHTTPListeners() {
        switchSubTab("HTTP Listener");
        return this;
    }

    public UndertowHTTPPage switchToHTTPSListeners() {
        switchSubTab("HTTPS Listener");
        return this;
    }

    public UndertowHTTPPage switchToHosts() {
        switchSubTab("Hosts");
        return this;
    }

    public void selectItemInTableByText(String text) {
        getResourceManager().selectByName(text);
    }

}
