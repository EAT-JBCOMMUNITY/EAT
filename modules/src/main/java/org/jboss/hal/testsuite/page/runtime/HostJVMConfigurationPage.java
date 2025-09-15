package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.page.config.ConfigurationPage;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Abstraction over page for configuration of JVM for given host
 */
public class HostJVMConfigurationPage extends ConfigurationPage {

    @Override
    public void navigate() {
        new FinderNavigation(browser, DomainRuntimeEntryPoint.class)
                .step(FinderNames.BROWSE_DOMAIN_BY, FinderNames.HOSTS)
                .step(FinderNames.HOST, ConfigUtils.getDefaultHost())
                .selectRow()
                .invoke("JVM");
        Application.waitUntilVisible();
    }

    public void selectJVMConfiguration(String name) {
        getResourceManager().getResourceTable().selectRowByText(0, name);
    }

    @Override
    public ConfigFragment getConfigFragment() {
        String cssClass = PropUtils.get("configarea.class");
        By selector = ByJQuery.selector("." + cssClass + ":visible");
        WebElement editPanel = browser.findElement(selector);
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }
}
