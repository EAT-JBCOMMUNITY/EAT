package org.jboss.hal.testsuite.page.runtime;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.MetricsAreaFragment;
import org.jboss.hal.testsuite.page.MetricsPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;

/**
 * Page representing
 */
public class UndertowServerMetricsPage extends MetricsPage implements Navigatable {

    public static final String MONITORED_SERVER = "server-one";

    @Override
    public void navigate() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainRuntimeEntryPoint.class)
                    .step(FinderNames.BROWSE_DOMAIN_BY, FinderNames.HOSTS)
                    .step(FinderNames.HOST, ConfigUtils.getDefaultHost())
                    .step(FinderNames.SERVER, MONITORED_SERVER);
        } else {
            navigation = new FinderNavigation(browser, StandaloneRuntimeEntryPoint.class)
                    .step(FinderNames.SERVER, FinderNames.STANDALONE_SERVER);
        }
        navigation = navigation.step(FinderNames.MONITOR, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, "Web/HTTP - Undertow");

        navigation.selectRow().invoke("View");
        Application.waitUntilVisible();

        getResourceManager().viewByName("default-server");
        Console.withBrowser(browser).waitUntilLoaded();
    }

    public MetricsAreaFragment getRequestPerConnectorMetricsArea() {
        return getMetricsArea("HTTP Requests");
    }
}
