package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderFragment;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.config.messaging.AddClusterConnectionWizard;
import org.jboss.hal.testsuite.fragment.config.messaging.AddMessagingProviderWindow;
import org.jboss.hal.testsuite.fragment.config.messaging.ProviderSettingsWindow;
import org.jboss.hal.testsuite.fragment.config.messaging.MessagingConfigArea;
import org.jboss.hal.testsuite.fragment.config.messaging.AddBridgeWizard;
import org.jboss.hal.testsuite.fragment.config.messaging.AddBroadcastGroupWizard;
import org.jboss.hal.testsuite.fragment.config.messaging.AddDiscoveryGroupWizard;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ConfigPropertiesFragment;
import org.jboss.hal.testsuite.fragment.config.ConfigPropertyWizard;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class MessagingPage extends ConfigPage implements Navigatable {

    protected static final String
        MESSAGING_PROVIDER_LABEL = "Messaging Provider",
        MESSAGING_SUBSYSTEM_LABEL = "Messaging - ActiveMQ",
        JMS_BRIDGE_LABEL = "JMS Bridge",
        SETTINGS_LABEL = "Settings";

    public ConfigFragment getConfigFragment() {
        WebElement editPanel = browser.findElement(ByJQuery.selector(".master_detail-detail:visible"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

    public ConfigFragment getWindowFragment() {
        WebElement editPanel = browser.findElement(By.className("default-window-content"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

    private void invokeActionOnMessagingProviderColumn(String action) {
       FinderFragment finderFragment = getSubsystemNavigation(MESSAGING_SUBSYSTEM_LABEL)
                .step(SETTINGS_LABEL, MESSAGING_PROVIDER_LABEL)
                .step(MESSAGING_PROVIDER_LABEL)
                .selectColumn();
       Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
       finderFragment.invoke(action);
    }

    private void invokeActionOnMessagingProviderRow(String action, String name) {
       FinderFragment finderFragment = getSubsystemNavigation(MESSAGING_SUBSYSTEM_LABEL)
                .step(SETTINGS_LABEL, MESSAGING_PROVIDER_LABEL)
                .step(MESSAGING_PROVIDER_LABEL, name)
                .selectRow();
       Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
       finderFragment.invoke(action);
    }

    private void invokeActionOnMessagingProviderRowWithProfile(String action, String name, String profile) {
        FinderFragment finderFragment = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                .step(FinderNames.PROFILE, profile)
                .step(FinderNames.SUBSYSTEM, MESSAGING_SUBSYSTEM_LABEL)
                .step(SETTINGS_LABEL, MESSAGING_PROVIDER_LABEL)
                .step(MESSAGING_PROVIDER_LABEL, name)
                .selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        finderFragment.invoke(action);
    }

    public ConfirmationWindow removeMessagingProvider(String name) {
        invokeActionOnMessagingProviderRow(FinderNames.REMOVE, name);
        return Console.withBrowser(browser).openedWindow(ConfirmationWindow.class);
    }

    public void viewClusteringSettings(String name) {
        invokeActionOnMessagingProviderRow("Clustering", name);
        Application.waitUntilVisible();
    }

    public void viewClusteringSettingsOnProfile(String name, String profile) {
        invokeActionOnMessagingProviderRowWithProfile("Clustering", name, profile);
        Application.waitUntilVisible();
    }

    public void viewConnectionSettings(String name) {
        invokeActionOnMessagingProviderRow("Connections", name);
        Application.waitUntilVisible();
    }

    public void viewQueuesAndTopics(String name) {
        invokeActionOnMessagingProviderRow("Queues/Topics", name);
        Application.waitUntilVisible();
    }

    public AddMessagingProviderWindow addMessagingProvider() {
        invokeActionOnMessagingProviderColumn(FinderNames.ADD);
        return Console.withBrowser(browser).openedWindow(AddMessagingProviderWindow.class);
    }


    public void invokeProviderSettings(String providerName) {
        invokeActionOnMessagingProviderRow("Provider Settings", providerName);
    }

    private void switchToTabInConfig(String label) {
        getConfig().switchTo(label);
    }

    //Provider settings pop up

    public ProviderSettingsWindow providerSettingsWindow() {
        return Console.withBrowser(browser).openedWindow(ProviderSettingsWindow.class);
    }

    //Bridges

    public void switchToConnectionManagementTab() {
        switchToTabInConfig("Connection Management");
    }

    public void switchToCredentialReference() {
        switchToTabInConfig("Credential Reference");
    }

    public void switchToDiscovery() {
        switchView("Discovery");
    }

    public void switchToConnector() {
        switchView("Connector");
    }

    public void switchToAcceptor() {
        switchView("Acceptor");
    }

    public void switchToConnectorServices() {
        switchView("Connector Services");
    }

    public void switchToConnectionFactories() {
        switchView("Connection Factories");
    }

    public void switchToSecuritySettings() {
        switchView("Security Settings");
    }

    public void switchToAddressSettings() {
        switchView("Address Settings");
    }

    public void switchToConnections() {
        switchView("Cluster Connection");
    }

    public void switchToDiverts() {
        switchView("Diverts");
    }

    public void switchToBridges() {
        switchView("Bridges");
    }

    public void switchToJmsQueuesTopics() {
        switchView("JMS Queues/Topics");
    }

    public void selectInTable(String value, int column) {
        getResourceManager().getResourceTable().selectRowByText(column, value);
    }

    public void selectInTable(String text) {
        selectInTable(text, 0);
    }

    public void switchType(String type) {
        Select select = new Select(browser.findElement(ByJQuery.selector(".gwt-ListBox:visible")));
        select.selectByValue("Type: " + type);
    }

    public void switchToGenericType() {
        switchType("Generic");
    }

    public void switchToInVmType() {
        switchType("In-VM");
    }

    public void switchToRemoteType() {
        switchType("Remote");
    }

    public AddBridgeWizard addBridge() {
        return getResourceManager().addResource(AddBridgeWizard.class);
    }

    public AddBroadcastGroupWizard addBroadcastGroup() {
        return getResourceManager().addResource(AddBroadcastGroupWizard.class);
    }

    public void addRemoteAcceptor(String name, String socketBinding) {
        clickButton(FinderNames.ADD);
        ConfigFragment windowFragment = getWindowFragment();
        Editor editor = windowFragment.getEditor();
        editor.text("name", name);
        editor.text("socketBinding", socketBinding);
        windowFragment.save();
    }

    public void addRemoteConnector(String name, String socketBinding) {
        addRemoteAcceptor(name, socketBinding);
    }

    public AddDiscoveryGroupWizard addDiscoveryGroup() {
        return getResourceManager().addResource(AddDiscoveryGroupWizard.class);
    }

    public void addInVmAcceptor(String name, String server) {
        clickButton("Add");
        getWindowFragment().getEditor().text("name", name);
        getWindowFragment().getEditor().text("serverId", server);
        getWindowFragment().clickButton("Save");
    }

    public void addGenericAcceptor(String name, String binding, String factoryClass) {
        clickButton("Add");
        getWindowFragment().getEditor().text("name", name);
        getWindowFragment().getEditor().text("socketBinding", binding);
        getWindowFragment().getEditor().text("factoryClass", factoryClass);
        getWindowFragment().clickButton("Save");
    }

    public void addDiverts(String name, String divert, String forward) {
        clickButton("Add");
        WindowFragment window = Console.withBrowser(browser).openedWindow();
        Editor editor = window.getEditor();
        editor.text("routingName", name);
        editor.text("divertAddress", divert);
        editor.text("forwardingAddress", forward);
        window.clickButton("Save");
    }

    public void addConnetorServices(String name, String factoryClass) {
        clickButton("Add");
        getWindowFragment().getEditor().text("name", name);
        getWindowFragment().getEditor().text("factoryClass", factoryClass);
        getWindowFragment().clickButton("Save");
    }

    public void addSecuritySettings(String pattern, String role) {
        clickButton("Add");
        getWindowFragment().getEditor().text("pattern", pattern);
        getWindowFragment().getEditor().text("role", role);
        getWindowFragment().clickButton("Save");
    }

    public void addAddressSettings(String pattern) {
        clickButton("Add");
        getWindowFragment().getEditor().text("pattern", pattern);
        getWindowFragment().clickButton("Save");
    }

    public void addQueue(String name, String jndiName) {
        clickButton("Add");
        ConfigFragment configFragment = getWindowFragment();
        Editor editor = configFragment.getEditor();
        editor.text("name", name);
        editor.text("entries", jndiName);
        configFragment.clickButton("Save");
    }

    public void addTopic(String name, String jndiName) {
        addQueue(name, jndiName);
    }

    public void addFactory(String name, String jndiName, String connector) {
        clickButton("Add");
        getWindowFragment().getEditor().text("name", name);
        getWindowFragment().getEditor().text("entries", jndiName);
        getWindowFragment().getEditor().text("connectors", connector);
        getWindowFragment().clickButton("Save");
    }

    public AddClusterConnectionWizard addClusterConnection() {
        return getResourceManager().addResource(AddClusterConnectionWizard.class);
    }

    public void clickAdvanced() {
        WebElement advanced = browser.findElement(By.linkText("Advanced"));
        advanced.click();
    }

    public Editor edit() {
        WebElement button = getEditButton();
        button.click();
        Graphene.waitGui().until().element(button).is().not().visible();
        return getConfig().getEditor();
    }

    private WebElement getEditButton() {
        By selector = ByJQuery.selector("." + PropUtils.get("configarea.edit.button.class") + ":visible");
        return getContentRoot().findElement(selector);
    }

    public void remove() {
        clickButton("Remove");
        try {
            Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirmAndDismissReloadRequiredMessage();
        } catch (TimeoutException ignored) {
        }
    }

    public void remove(String text) {
        selectInTable(text);
        remove();
    }


    public MessagingConfigArea getConfig() {
        return getConfig(MessagingConfigArea.class);
    }

    public boolean addProperty(String key, String value) {
        ConfigPropertiesFragment properties = getConfig().propertiesConfig();
        ConfigPropertyWizard wizard = properties.getResourceManager().addResource(ConfigPropertyWizard.class);
        wizard.name(key).value(value).clickSave();

        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();

        return wizard.isClosed();
    }

    public void removeProperty(String key) {
        ConfigPropertiesFragment config = getConfig().propertiesConfig();
        ResourceManager properties = config.getResourceManager();
        try {
            properties.removeResource(key).confirmAndDismissReloadRequiredMessage();
        } catch (TimeoutException ignored) {

        }
    }
}
