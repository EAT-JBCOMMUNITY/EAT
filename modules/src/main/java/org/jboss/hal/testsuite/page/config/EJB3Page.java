package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.config.container.EJB3BeanPoolsFragment;
import org.jboss.hal.testsuite.fragment.config.container.EJB3ServiceFragment;
import org.jboss.hal.testsuite.fragment.config.container.EJB3ThreadPoolsFragment;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#ejb3")
public class EJB3Page extends ConfigPage implements Navigatable {

    public EJB3BeanPoolsFragment beanPools() {
        switchTab("Bean Pools");
        return Graphene.createPageFragment(EJB3BeanPoolsFragment.class, getContentRoot().findElement(By.id(PropUtils.get("page.content.id"))));
    }

    public EJB3ThreadPoolsFragment threadPools() {
        switchSubTab("Thread Pools");
        return Graphene.createPageFragment(EJB3ThreadPoolsFragment.class, getContentRoot().findElement(By.id(PropUtils.get("page.content.id"))));
    }

    public EJB3ServiceFragment service() {
        switchTab("Services");
        return Graphene.createPageFragment(EJB3ServiceFragment.class, getContentRoot().findElement(By.id(PropUtils.get("page.content.id"))));
    }

    public void navigate() {
        FinderNavigation finderNavigation;
        if (ConfigUtils.isDomain()) {
            finderNavigation = new FinderNavigation(browser, DomainConfigurationPage.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, "full")
                    .step(FinderNames.SUBSYSTEM, "EJB 3");
        } else {
            finderNavigation = new FinderNavigation(browser, StandaloneConfigurationPage.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS)
                    .step(FinderNames.SUBSYSTEM, "EJB 3");
        }
        finderNavigation.selectRow().invoke("View");
        Application.waitUntilVisible();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }
}
