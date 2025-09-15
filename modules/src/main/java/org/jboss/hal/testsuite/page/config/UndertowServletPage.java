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
public class UndertowServletPage extends UndertowPage implements Navigatable {

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
                .step("Settings", "Servlet/JSP")
                .selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }

    public UndertowServletPage selectServletContainer(String containerName) {
        getResourceManager().selectByName(containerName);
        return this;
    }

    public UndertowServletPage viewServletContainer(String containerName) {
        getResourceManager().viewByName(containerName);
        Console.withBrowser(browser).waitUntilLoaded();
        return this;
    }

    public void switchToJSP() {
        switchSubTab("JSP");
    }

    public void switchToWebSockets() {
        switchSubTab("Web Sockets");
    }

    public void switchToSessions() {
        switchSubTab("Sessions");
    }

    public void switchToCookies() {
        switchSubTab("Cookies");
    }

    public void switchToJSPDevelopment() {
        getConfig().switchTo("Development");
    }


    public void switchToJSPAttributes() {
        getConfig().switchTo("Attributes");
    }
}
