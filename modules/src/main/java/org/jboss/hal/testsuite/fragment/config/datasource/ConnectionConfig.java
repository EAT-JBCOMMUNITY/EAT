package org.jboss.hal.testsuite.fragment.config.datasource;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Created by jcechace on 02/03/14.
 */
public class ConnectionConfig extends ConfigFragment {
    public TestConnectionWindow testConnection() {
        String label = PropUtils.get("config.datasources.configarea.connection.test.label");
        clickButton(label);
        TestConnectionWindow window =  Console.withBrowser(browser).openedWindow(TestConnectionWindow.class);

        return window;
    }
}
