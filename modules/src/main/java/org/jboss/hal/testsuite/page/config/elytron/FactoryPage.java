package org.jboss.hal.testsuite.page.config.elytron;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.jboss.hal.testsuite.finder.FinderNames.SETTINGS;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.ELYTRON_SUBSYTEM_LABEL;

public class FactoryPage extends AbstractElytronConfigPage<FactoryPage> {

    @Override
    public FactoryPage navigateToApplication() {
        getSubsystemNavigation(ELYTRON_SUBSYTEM_LABEL).step(SETTINGS, "Factory").openApplication();
        return this;
    }

    public FactoryPage selectFactory(final String factoryLabel) {
        switchSubTab(factoryLabel);
        return this;
    }

    public ConfigFragment getWindowFragment() {
        WebElement editPanel = browser.findElement(By.className("default-window-content"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }
}
