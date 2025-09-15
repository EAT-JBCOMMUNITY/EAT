package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.Column;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.Row;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.WindowState;
import org.jboss.hal.testsuite.fragment.config.datasource.PoolConfig;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ResourceAdapterWizard;
import org.jboss.hal.testsuite.fragment.config.resourceadapters.ResourceAdaptersConfigArea;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#profile")
public class ResourceAdaptersPage extends ConfigurationPage {

    private static final String
        RA_SUBSYSTEM_LABEL = "Resource Adapters",
        RA_COLUMN_LABEL = "Resource Adapter";

    private static final String CAPACITY_DECREMENTER_CLASS_IDENTIFIER = "capacity-decrementer-class";
    private static final String CAPACITY_DECREMENTER_PROPERTIES_IDENTIFIER = "capacity-decrementer-properties";

    private static final String CAPACITY_INCREMENTER_CLASS_IDENTIFIER = "capacity-incrementer-class";
    private static final String CAPACITY_INCREMENTER_PROPERTIES_IDENTIFIER = "capacity-incrementer-properties";

    public ResourceAdaptersConfigArea getConfigArea() {
        return getConfig(ResourceAdaptersConfigArea.class);
    }

    public ResourceAdaptersPage navigateToResourceAdapter(String resourceAdapterName) {
        Row row = getSubsystemNavigation(RA_SUBSYSTEM_LABEL).step(RA_COLUMN_LABEL, resourceAdapterName).selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        row.invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        return this;
    }

    public ResourceAdapterWizard addResourceAdapter() {
        Column column = getSubsystemNavigation(RA_SUBSYSTEM_LABEL).step(RA_COLUMN_LABEL).selectColumn();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        column.invoke(FinderNames.ADD);
        return Console.withBrowser(browser).openedWizard(ResourceAdapterWizard.class);
    }

    public WindowState removeResourceAdapter(String raName) {
        Row row = getSubsystemNavigation(RA_SUBSYSTEM_LABEL).step(RA_COLUMN_LABEL, raName).selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        row.invoke(FinderNames.REMOVE);
        return Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
    }

    public ConfigFragment switchConfigAreaTabTo(String tabLabel) {
        return getConfig(ConfigAreaFragment.class).switchTo(tabLabel);
    }

    public void switchToConnectionDefinitions() {
        WebElement viewPanel = browser.findElement(By.className("paged-view-navigation-container"));
        WebElement editLink = viewPanel.findElement(By.linkText("Connection Definitions"));
        editLink.click();
    }

    public PoolConfig getPoolConfig() {
        return getConfigArea().switchTo("Pool", PoolConfig.class);
    }

    public void setDecrementerClass(String value) {
        getConfigFragment()
                .getEditor()
                .select(CAPACITY_DECREMENTER_CLASS_IDENTIFIER, value);
    }

    public void unsetDecrementerClass() {
        setDecrementerClass("");
    }

    public void setDecrementerProperty(String key, String value) {
        String propertyValue = String.format("%s=%s", key, value);
        getConfigFragment().getEditor().text(CAPACITY_DECREMENTER_PROPERTIES_IDENTIFIER, propertyValue);
    }

    public void unsetDecrementerProperty() {
        getConfigFragment().getEditor().text(CAPACITY_DECREMENTER_PROPERTIES_IDENTIFIER, "");
    }

    public void setIncrementerClass(String value) {
        getConfigFragment()
                .getEditor()
                .select(CAPACITY_INCREMENTER_CLASS_IDENTIFIER, value);
    }

    public void unsetIncrementerClass() {
        setIncrementerClass("");
    }

    public void setIncrementerProperty(String key, String value) {
        String propertyValue = String.format("%s=%s", key, value);
        getConfigFragment().getEditor().text(CAPACITY_INCREMENTER_PROPERTIES_IDENTIFIER, propertyValue);
    }

    public void unsetIncrementerProperty() {
        getConfigFragment().getEditor().text(CAPACITY_INCREMENTER_PROPERTIES_IDENTIFIER, "");
    }
}
