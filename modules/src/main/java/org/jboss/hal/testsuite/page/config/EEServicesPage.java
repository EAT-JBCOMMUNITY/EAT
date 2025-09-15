package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.config.ee.EEConfigFragment;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 11.9.15.
 */
public class EEServicesPage extends ConfigurationPage implements Navigatable {

    public EEConfigFragment getConfigFragment() {
        WebElement editPanel = browser.findElement(By.className("default-tabpanel"));
        return  Graphene.createPageFragment(EEConfigFragment.class, editPanel);
    }

    public void navigate() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class);
            navigation.step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, "full")
                    .step(FinderNames.SUBSYSTEM, "EE");
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class);
            navigation.step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS)
                    .step(FinderNames.SUBSYSTEM, "EE");
        }
        navigation.selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        switchTab("Services");
    }

}
