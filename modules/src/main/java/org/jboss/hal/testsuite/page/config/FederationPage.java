package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.WindowState;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.util.Console;

/**
 * @author jcechace
 */
public class FederationPage extends ConfigurationPage {

    private static final String
        PICKETLINK = "PicketLink",
        FEDERATION = "Federation",
        SERVICE_PROVIDER = "Service Provider";

    public WizardWindow addFederationWindow() {
        getSubsystemNavigation(PICKETLINK).step(FEDERATION).selectColumn().invoke(FinderNames.ADD);
        return Console.withBrowser(browser).openedWizard();
    }

    public WindowState removeFederation(String federationName) {
        getSubsystemNavigation(PICKETLINK).step(FEDERATION, federationName).selectRow()
            .invoke(FinderNames.REMOVE);
        return Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
    }

    public FederationPage navigateToFederation(String federationName) {
        getSubsystemNavigation(PICKETLINK).step(FEDERATION, federationName).selectRow()
            .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        return this;
    }

    public WizardWindow addSpWindow(String federationName) {
        getSubsystemNavigation(PICKETLINK).step(FEDERATION, federationName).step(SERVICE_PROVIDER)
            .selectColumn().invoke(FinderNames.ADD);
        return Console.withBrowser(browser).openedWizard();
    }

    public WindowState removeSp(String federationName, String spName) {
        getSubsystemNavigation(PICKETLINK).step(FEDERATION, federationName).step(SERVICE_PROVIDER, spName)
            .selectRow().invoke(FinderNames.REMOVE);
        return Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
    }

    public FederationPage navigateToServiceProvider(String federationName, String spName) {
        getSubsystemNavigation(PICKETLINK).step(FEDERATION, federationName).step(SERVICE_PROVIDER, spName)
            .selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        return this;
    }

    public ConfigFragment switchConfigAreaTabTo(String tabLabel) {
        return getConfig(ConfigAreaFragment.class).switchTo(tabLabel);
    }

}
