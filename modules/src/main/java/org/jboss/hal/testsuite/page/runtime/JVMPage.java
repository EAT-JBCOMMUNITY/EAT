package org.jboss.hal.testsuite.page.runtime;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.MetricsAreaFragment;
import org.jboss.hal.testsuite.page.MetricsPage;
import org.jboss.hal.testsuite.util.ConfigUtils;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class JVMPage extends MetricsPage {

    @Override
    public void navigate() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainRuntimeEntryPoint.class)
                    .step(FinderNames.BROWSE_DOMAIN_BY, FinderNames.HOSTS)
                    .step(FinderNames.HOST, "master")
                    .step(FinderNames.SERVER, "server-one");
        }
        else {
            navigation = new FinderNavigation(browser, StandaloneRuntimeEntryPoint.class)
                    .step(FinderNames.SERVER, FinderNames.STANDALONE_SERVER);
        }

        navigation.step(FinderNames.MONITOR, "JVM").selectRow().invoke("View");
        Application.waitUntilVisible();
    }

    public MetricsAreaFragment getHeapUsageMetricsArea() {
        return getMetricsArea("Heap Usage");
    }

    public MetricsAreaFragment getNonHeapUsageMetricsArea() {
        return getMetricsArea("Non Heap Usage");
    }

    public MetricsAreaFragment getThreadUsageMetricsArea() {
        return getMetricsArea("Thread Usage");
    }
}
