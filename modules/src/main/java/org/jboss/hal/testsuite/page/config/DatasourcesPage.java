package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.Column;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.finder.Row;
import org.jboss.hal.testsuite.fragment.config.datasource.DatasourceConfigArea;
import org.jboss.hal.testsuite.fragment.config.datasource.DatasourceWizard;
import org.jboss.hal.testsuite.fragment.config.datasource.PoolConfig;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;

public class DatasourcesPage extends ConfigurationPage implements Navigatable {

    private static final String CAPACITY_DECREMENTER_CLASS_IDENTIFIER = "capacityDecrementerClass";
    private static final String CAPACITY_DECREMENTER_PROPERTIES_IDENTIFIER = "capacityDecrementerProperties";

    private static final String CAPACITY_INCREMENTER_CLASS_IDENTIFIER = "capacityIncrementerClass";
    private static final String CAPACITY_INCREMENTER_PROPERTIES_IDENTIFIER = "capacityIncrementerProperties";

    @Override
    public DatasourceConfigArea getConfig() {
        return getConfig(DatasourceConfigArea.class);
    }

    @Deprecated
    public void switchToXA() {
        selectMenu("XA");
        Console.withBrowser(browser).waitUntilLoaded();
    }

    /**
     * Get already opened {@link DatasourceWizard}
     * @return opened {@link DatasourceWizard}
     */
    public DatasourceWizard getDatasourceWizard() {
        return Console.withBrowser(browser).openedWizard(DatasourceWizard.class);
    }

    public PoolConfig getPoolConfig() {
        return getConfig().switchTo("Pool", PoolConfig.class);
    }

    private enum DatasourceType {
        XA("XA", "XA Datasource"),
        NON_XA("Non-XA", "Datasource");

        private String typeColumnLabel;
        private String finalColumnLabel;

        DatasourceType(String typeColumnLabel, String finalColumnLabel) {
            this.typeColumnLabel = typeColumnLabel;
            this.finalColumnLabel = finalColumnLabel;
        }

        public String getTypeColumnLabel() {
            return typeColumnLabel;
        }

        public String getFinalColumnLabel() {
            return finalColumnLabel;
        }
    }

    private enum Action {
        VIEW(FinderNames.VIEW),
        ADD(FinderNames.ADD);

        private String actionLabel;

        Action(String actionLabel) {
            this.actionLabel = actionLabel;
        }

        public String getActionLabel() {
            return actionLabel;
        }
    }

    private FinderNavigation createNavigationToDatasourcesColumn() {
        return createNavigationToDatasourcesColumn(ConfigUtils.getDefaultProfile());
    }

    private FinderNavigation createNavigationToDatasourcesColumn(String profileName) {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigurationPage.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, profileName);
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        return navigation.step(FinderNames.SUBSYSTEM, "Datasources");
    }

    /**
     * Navigates to datasource subsystem and invokes action on found row with datasource
     * @param action {@link Action} which will be performed on found row
     * @param type type of datasource
     * @param name name of datasource
     */
    private void invokeActionOnDatasourceRow(Action action, DatasourceType type, String name) {
        Row row = createNavigationToDatasourcesColumn()
                .step("Type", type.getTypeColumnLabel())
                .step(type.getFinalColumnLabel(), name)
                .selectRow(true);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        row.invoke(action.getActionLabel());
    }

    /**
     * Navigates to datasource subsystem and invokes action on final column containing datasource names
     * @param action {@link Action} which will be performed on found column
     * @param type type of datasource
     */
    private void invokeActionOnDatasourceColumn(Action action, DatasourceType type) {
        invokeActionOnDatasourceColumn(action, type, ConfigUtils.getDefaultProfile());
    }

    /**
     * Navigates to datasource subsystem and invokes action on final column containing datasource names
     * @param action {@link Action} which will be performed on found column
     * @param type type of datasource
     * @param profileName name of profile used in navigation in domain mode
     */
    private void invokeActionOnDatasourceColumn(Action action, DatasourceType type, String profileName) {
        Column column = createNavigationToDatasourcesColumn(profileName)
                .step("Type", type.getTypeColumnLabel())
                .step(type.getFinalColumnLabel())
                .selectColumn(true);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        column.invoke(action.getActionLabel());
        Console.withBrowser(browser).waitUntilLoaded();
    }

    @Override
    public void navigate() {
        createNavigationToDatasourcesColumn().selectColumn();
        Console.withBrowser(browser).waitUntilLoaded();
    }

    /**
     * Invokes add action on non-XA datasource column
     */
    public void invokeAddDatasource() {
        invokeActionOnDatasourceColumn(Action.ADD, DatasourceType.NON_XA);
    }

    /**
     * Invokes add action on non-XA datasource column using desired profile in navigation
     */
    public void invokeAddDatasourceOnProfile(String profileName) {
        invokeActionOnDatasourceColumn(Action.ADD, DatasourceType.NON_XA, profileName);
    }

    /**
     * Invokes add action on XA datasource column
     */
    public void invokeAddXADatasource() {
        invokeActionOnDatasourceColumn(Action.ADD, DatasourceType.XA);
    }

    /**
     * Invokes view action on non-XA datasource
     * @param name name of datasource
     */
    public void invokeViewDatasource(String name) {
        invokeActionOnDatasourceRow(Action.VIEW, DatasourceType.NON_XA, name);
        Application.waitUntilVisible();
    }

    /**
     * Invokes view action on XA datasource
     * @param name name of datasource
     */
    public void invokeViewXADatasource(String name) {
        invokeActionOnDatasourceRow(Action.VIEW, DatasourceType.XA, name);
        Application.waitUntilVisible();
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
