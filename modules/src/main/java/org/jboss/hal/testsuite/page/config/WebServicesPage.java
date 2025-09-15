package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.webservices.WebServicesConfigurationWizard;
import org.jboss.hal.testsuite.fragment.config.webservices.WebServicesHandlerChainHandlerWizard;
import org.jboss.hal.testsuite.fragment.config.webservices.WebServicesHandlerChainWizard;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#webservices")
public class WebServicesPage extends ConfigPage implements Navigatable {

    private static final String WEB_SERVICES_SUBSYSTEM_LABEL = "Web Services";
    private static final String HANDLER_CLASSES_TAB_LABEL = "Handler classes";


    public void navigate() {
        getSubsystemNavigation(WEB_SERVICES_SUBSYSTEM_LABEL)
                .selectRow()
                .invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }

    public ConfigFragment getWindowFragment() {
        WebElement editWindowPanel = browser.findElement(By.className("default-window-content"));
        return Graphene.createPageFragment(ConfigFragment.class, editWindowPanel);
    }

    public void switchToEndpointConfiguration() {
        switchTab("Endpoint Configuration");
    }

    public void switchToClientConfiguration() {
        switchTab("Client Configuration");
    }

    public void switchToProperties() {
        getConfig().getTabByLabel("Properties").click();
    }

    public void switchToClientConfigurationHandlerChainTab(String clientConfigurationName, String handlerChainTabLabel) {
        switchToClientConfiguration();
        getResourceManager()
                .selectByName(clientConfigurationName)
                .view();
        switchSubTab(handlerChainTabLabel);
    }

    public void switchToEndpointConfigurationHandlerChainTab(String endpointConfigurationName, String handlerChainTabLabel) {
        switchToEndpointConfiguration();
        getResourceManager()
                .selectByName(endpointConfigurationName)
                .view();
        switchSubTab(handlerChainTabLabel);
    }
    public void createHandlerChainInUI(String handlerChainName, String handlerChainProtocolBindings) {
        getResourceManager()
                .addResource(WebServicesHandlerChainWizard.class)
                .name(handlerChainName)
                .protocolBindings(handlerChainProtocolBindings)
                .saveAndDismissReloadRequiredWindowWithState()
                .assertWindowClosed();
    }

    public void createHandlerChainHandlerInUI(String handlerChainName, String handlerChainHandlerName, String handlerChainHandlerClass) {
        getResourceManager().selectByName(handlerChainName);
        getConfig()
                .switchTo(HANDLER_CLASSES_TAB_LABEL)
                .getResourceManager()
                .addResource(WebServicesHandlerChainHandlerWizard.class)
                .name(handlerChainHandlerName)
                .className(handlerChainHandlerClass)
                .saveAndDismissReloadRequiredWindowWithState()
                .assertWindowClosed();
    }

    public void createConfigurationInUI(String configurationName) {
        getResourceManager().addResource(WebServicesConfigurationWizard.class)
                .name(configurationName)
                .saveAndDismissReloadRequiredWindowWithState()
                .assertWindowClosed();
    }


}
