package org.jboss.hal.testsuite.page.config.elytron;

import static org.jboss.hal.testsuite.finder.FinderNames.SETTINGS;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.ELYTRON_SUBSYTEM_LABEL;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.OTHER;

public class SSLPage extends AbstractElytronConfigPage<SSLPage> {

    @Override
    public SSLPage navigateToApplication() {
        getSubsystemNavigation(ELYTRON_SUBSYTEM_LABEL).step(SETTINGS, OTHER).openApplication(30);
        switchTab("SSL");
        return this;
    }

}
