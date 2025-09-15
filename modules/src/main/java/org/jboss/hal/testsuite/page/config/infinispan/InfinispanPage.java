package org.jboss.hal.testsuite.page.config.infinispan;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.page.BasePage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.page.config.DomainConfigEntryPoint;
import org.jboss.hal.testsuite.page.config.StandaloneConfigEntryPoint;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;

public class InfinispanPage extends BasePage implements Navigatable {

    private static final String INFINISPAN_LABEL = "Infinispan";
    private static final String CACHE_CONTAINER_LABEL = "Cache Container";

    public void navigateToCacheContainer(String cacheContainer) {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, "full-ha");
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navigation.step(FinderNames.SUBSYSTEM, INFINISPAN_LABEL)
                .step(CACHE_CONTAINER_LABEL, cacheContainer);
        navigation.selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible(50);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }

    public void navigate() {
        navigateToCacheContainer("hibernate");
    }

    public InfinispanPage localCaches() {
        switchTab("Local Caches");
        return this;
    }

    public InfinispanPage replicatedCaches() {
        switchTab("Replicated Caches");
        return this;
    }

    public InfinispanPage distributed() {
        switchTab("Distributed Caches");
        return this;
    }

    public InfinispanPage invalidation() {
        switchTab("Invalidation Caches");
        return this;
    }

    public boolean editTextAndSave(String identifier, String value) {
        ConfigFragment configFragment = getConfigFragment();
        Editor editor = configFragment.edit();
        editor.text(identifier, value);
        return configFragment.save();
    }

    public boolean selectOptionAndSave(String identifier, String value) {
        ConfigFragment configFragment = getConfigFragment();
        configFragment.edit().select(identifier, value);
        return configFragment.save();
    }

    public Boolean editCheckboxAndSave(String identifier, Boolean value) {
        ConfigFragment configFragment = getConfigFragment();
        configFragment.edit().checkbox(identifier, value);
        return configFragment.save();
    }

    public Boolean isErrorShownInForm() {
        By selector = ByJQuery.selector("div.form-item-error-desc:visible");
        return isElementVisible(selector);
    }

    private Boolean isElementVisible(By selector) {
        try {
            Graphene.waitAjax().until().element(selector).is().visible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectCache(String name) {
        getResourceManager().selectByName(name);
    }
}
