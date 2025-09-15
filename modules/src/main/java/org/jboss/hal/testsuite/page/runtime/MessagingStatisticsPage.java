package org.jboss.hal.testsuite.page.runtime;

import org.jboss.hal.testsuite.page.MetricsPage;
import org.jboss.hal.testsuite.util.Console;

/**
 * UI abstraction for JMS provider runtime statistics page
 */
public class MessagingStatisticsPage extends MetricsPage {

    public MessagingStatisticsPage navigateToDefaultProviderStats() {
        navigate2runtimeSubsystem("Messaging - ActiveMQ");
        getResourceManager().getResourceTable().getRowByText(0, "default").view();
        Console.withBrowser(browser).waitUntilLoaded();
        return this;
    }

    public MessagingStatisticsPage switchToPooledConnectionFactory() {
        switchSubTab("Pooled Connection Factory");
        return this;
    }

    public MessagingStatisticsPage refresh() {
        clickButton("Refresh");
        return this;
    }

    public MessagingStatisticsPage selectConnectionFactory(String connectionFactoryName) {
        getResourceManager().selectByName(connectionFactoryName);
        return this;
    }
}
