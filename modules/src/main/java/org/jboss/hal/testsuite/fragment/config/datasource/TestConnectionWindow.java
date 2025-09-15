package org.jboss.hal.testsuite.fragment.config.datasource;

import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Created by jcechace on 02/03/14.
 */
public class TestConnectionWindow extends WindowFragment {

    public boolean isSuccessful() {
        String title = getTitle().toLowerCase();
        String expectedSuccessTitle = PropUtils.get("config.datasources.configarea.connection.test.success.label");

        return title.contains(expectedSuccessTitle.toLowerCase());
    }
}
