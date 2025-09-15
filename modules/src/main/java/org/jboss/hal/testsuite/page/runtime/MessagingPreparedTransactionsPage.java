package org.jboss.hal.testsuite.page.runtime;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.page.config.ConfigurationPage;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;

public class MessagingPreparedTransactionsPage extends ConfigurationPage implements Navigatable {

    @Override
    public void navigate() {
        FinderNavigation finderNavigation;
        if (ConfigUtils.isDomain()) {
            finderNavigation = new FinderNavigation(browser, DomainRuntimeEntryPoint.class)
                    .step(FinderNames.BROWSE_DOMAIN_BY, FinderNames.HOSTS)
                    .step(FinderNames.HOST, "master")
                    .step(FinderNames.SERVER, "server-one");
        } else {
            finderNavigation = new FinderNavigation(browser, StandaloneRuntimeEntryPoint.class)
                    .step(FinderNames.SERVER, FinderNames.STANDALONE_SERVER);
        }
        finderNavigation.step(FinderNames.MONITOR, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, "Messaging - ActiveMQ")
                .selectRow()
                .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        getResourceManager().getResourceTable()
                .getRowByText(0, "default")
                .view();
        Console.withBrowser(browser).waitUntilLoaded();
        switchSubTab("Prepared Transactions");
    }

    public void selectPreparedTransaction(String xId) {
        getResourceManager().selectByName(xId);
    }

    public void refresh() {
        clickButton("Refresh");
    }
}
