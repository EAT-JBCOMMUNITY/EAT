package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.config.undertow.UndertowFragment;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 15.9.15.
 */
public class UndertowPage extends ConfigurationPage implements Navigatable {

    private static final String
            TAB_PANEL_CLASS = PropUtils.get("page.tabpanel.class"),
            CONFIG_AREA_CLASS = PropUtils.get("configarea.content.class");

    private static final By CONTENT_ROOT = ByJQuery.selector("table." + TAB_PANEL_CLASS + ":has(." + CONFIG_AREA_CLASS + ":visible):visible");


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
        navigation.step(FinderNames.SUBSYSTEM, "Undertow").selectRow();
        Application.waitUntilVisible();
    }

    public UndertowFragment getConfigFragment() {
        WebElement editPanel = browser.findElement(CONTENT_ROOT);
        return Graphene.createPageFragment(UndertowFragment.class, editPanel);
    }

    @Override
    public void switchSubTab(String identifier) {
        super.switchSubTab(identifier);
        Console.withBrowser(browser).waitUntilLoaded();
    }

    @Override
    public ConfigAreaFragment getConfig() {
        WebElement root = browser.findElement(CONTENT_ROOT);
        return Graphene.createPageFragment(ConfigAreaFragment.class, root);
    }

}
