package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.openqa.selenium.WebElement;

public class UndertowFiltersPage extends ConfigurationPage implements Navigatable {

    @Override
    public void navigate() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, ConfigUtils.getDefaultProfile());
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navigation.step(FinderNames.SUBSYSTEM, "Web/HTTP - Undertow")
                .step("Settings", "Filters")
                .selectRow()
                .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }

    /**
     * Switch to desired filter type
     * @param type name of filters in Web Console UI
     */
    public void selectFilterType(String type) {
        switchSubTab(type);
    }

    public ConfigFragment getConfigFragment() {
        WebElement editPanel = browser.findElement(ByJQuery.selector(".master_detail-detail:visible"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

}
