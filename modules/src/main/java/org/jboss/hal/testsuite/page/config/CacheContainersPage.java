package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.finder.Row;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.infinispan.CacheContainerWizard;
import org.jboss.hal.testsuite.fragment.config.infinispan.CacheContainersFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#infinispan")
public class CacheContainersPage extends ConfigurationPage implements Navigatable {

    private static final By CONTENT = By.id(PropUtils.get("page.content.id"));

    public CacheContainersFragment content() {
        return Graphene.createPageFragment(CacheContainersFragment.class, getContentRoot().findElement(CONTENT));
    }

    private FinderNavigation createBaseNavigation() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, "full-ha");
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navigation.step(FinderNames.SUBSYSTEM, "Infinispan");
        return navigation;
    }

    public void navigate() {
        createBaseNavigation().selectColumn();
    }

    public FinderNavigation getNavigationToCacheContainer(String cacheContainer) {
        return createBaseNavigation().step("Cache Container", cacheContainer);
    }

    private Row selectCacheContainerRowAndDismissReloadRequiredWindow(String cacheContainer) {
        Row row = getNavigationToCacheContainer(cacheContainer).selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        return row;
    }

    private void navigateAndInvokeActionOnCacheContainer(String cacheContainer, String action) {
        selectCacheContainerRowAndDismissReloadRequiredWindow(cacheContainer).invoke(action);
    }

    public void invokeTransportSettings(String cacheContainer) {
        navigateAndInvokeActionOnCacheContainer(cacheContainer, "Transport Settings");
    }

    public void invokeContainerSettings(String cacheContainer) {
        navigateAndInvokeActionOnCacheContainer(cacheContainer, "Container Settings");
    }

    public CacheContainerWizard invokeAddCacheContainer() {
        createBaseNavigation().step("Cache Container").selectColumn();
        return getResourceManager().addResource(CacheContainerWizard.class);
    }

    public void navigateAndRemoveCacheContainer(String cacheContainer) {
        navigateAndInvokeActionOnCacheContainer(cacheContainer, "Remove");
        Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirmAndDismissReloadRequiredMessage();
    }

    public ConfigFragment getSettingsConfig() {
        By selector = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");
        WebElement formRoot = browser.findElement(selector);
        return Graphene.createPageFragment(ConfigFragment.class, formRoot);
    }
}
