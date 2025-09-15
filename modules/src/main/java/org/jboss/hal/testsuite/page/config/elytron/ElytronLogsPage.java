package org.jboss.hal.testsuite.page.config.elytron;

import static org.jboss.hal.testsuite.finder.FinderNames.SETTINGS;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.ELYTRON_SUBSYTEM_LABEL;

public class ElytronLogsPage extends AbstractElytronConfigPage<ElytronLogsPage> {

    @Override
    public ElytronLogsPage navigateToApplication() {
        getSubsystemNavigation(ELYTRON_SUBSYTEM_LABEL).step(SETTINGS, "Other").openApplication();
        switchTab("Logs");
        return this;
    }

}
