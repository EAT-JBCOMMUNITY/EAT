package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;

/**
 * Abstraction over JMS Bridge UI configuration
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 9/19/16.
 */
public class JMSBridgePage extends MessagingPage implements Navigatable {

    /**
     * Performs navigation to JMS bridge configuration in messaging subsystem configuration
     */
    public void navigate() {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, ConfigUtils.getDefaultProfile());
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navigation.step(FinderNames.SUBSYSTEM, MESSAGING_SUBSYSTEM_LABEL)
                .step(SETTINGS_LABEL, JMS_BRIDGE_LABEL)
                .selectRow()
                .invoke(FinderNames.VIEW);

        Application.waitUntilVisible();
    }

    /**
     * Removes JMS Bridge in UI
     * @param bridgeName name of bridge to be removed
     */
    public void removeJMSBridge(String bridgeName) {
        selectInTable(bridgeName);

        clickButton("Remove");

        Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
    }

    public void switchToSourceContext() {
        getConfig().getTabByLabel("Source Context").click();
    }

    public void switchToTargetContext() {
        getConfig().getTabByLabel("Target Context").click();
    }

    public void switchToTargetCredentialReference() {
        getConfig().getTabByLabel("Target Credential Reference").click();
    }

    public void switchToSourceCredentialReference() {
        getConfig().getTabByLabel("Source Credential Reference").click();
    }

    public WizardWindow openAddContextPropertyWindow() {
        getConfig().clickButton("Add");
        return Console.withBrowser(browser).openedWindow(WizardWindow.class);
    }

    public void selectJMSBridgeInTable(String jmsBridgeName) {
        getResourceManager().getResourceTable().selectRowByText(0, jmsBridgeName);
    }

    public ConfirmationWindow selectContextParameterAndClickRemove(String contextParameterKey) {
        getConfig().getResourceTable().getRowByText(0, contextParameterKey).click();
        getConfig().clickButton("Remove");
        return Console.withBrowser(browser).openedWindow(ConfirmationWindow.class);
    }

}
