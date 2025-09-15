package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by pcyprian on 22.10.15.
 */
public class DeploymentScannerPage extends ConfigurationPage {

    public void navigateToDeploymentScanners() {
        FinderNavigation navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, "Deployment Scanners");

        navigation.selectRow().invoke("View");
        Application.waitUntilVisible();
    }

    public ConfigFragment getWindowFragment() {
        WebElement editPanel = browser.findElement(By.className("default-window-content"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

    public ConfigFragment getMainConfigFragment() {
        return Graphene.createPageFragment(ConfigFragment.class, getContentRoot());
    }
}
