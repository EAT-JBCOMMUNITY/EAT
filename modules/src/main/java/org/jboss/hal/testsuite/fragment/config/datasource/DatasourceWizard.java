package org.jboss.hal.testsuite.fragment.config.datasource;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcechace
 */
public class DatasourceWizard extends WizardWindow {

    private static final Logger logger = LoggerFactory.getLogger(DatasourceWizard.class);

    private static final By DETECTED_DRIVER_BUTTON = By.xpath(".//div[text()='Detected Driver']");
    private static final Location ORIGIN_LOCATION = Location.TEMPLATE_SELECTION;

    private Location currentLocation = ORIGIN_LOCATION;

    public enum DatasourceType {
        NON_XA, XA
    }

    /**
     * Locations within datasource wizard which user can navigate into
     */
    public enum Location {

        TEMPLATE_SELECTION(0, 0, "template selection"),
        DATASOURCE_ATTRIBUTES(1, 1, "datasource attributes"),
        JDBC_DRIVER(2, 2, "JDBC driver"),
        CONNECTION_SETTINGS(3, 4, "connection settings"),
        XA_PROPERTIES(-1, 3, "XA properties"),
        TEST_CONNECTION(4, 5, "test connection"),
        SUMMARY(5, 6, "summary");

        private int index;
        private int xaIndex;
        private String name;

        Location(int index, int xaIndex, String name) {
            this.index = index;
            this.xaIndex = xaIndex;
            this.name = name;
        }

        /**
         * Get index (order) of this location in wizard
         * @param type type of datasource for which the wizard was opened
         * @return index
         */
        public int getIndex(DatasourceType type) {
            int exportedIndex;
            if (type == DatasourceType.NON_XA) {
                exportedIndex = index;
            } else {
                exportedIndex = xaIndex;
            }
            if (exportedIndex < 0) {
                throw new IllegalStateException("Invalid index - maybe wrong datasource type?");
            } else {
                return exportedIndex;
            }
        }

        public String getName() {
            return name;
        }
    }

    /**
     * Tests connection and *DOES NOT* check if wizard is in correct {@link Location}
     * @return {@link TestConnectionWindow}
     */
    public TestConnectionWindow testConnectionUnchecked() {
        String label = PropUtils.get("config.datasources.configarea.connection.test.label");
        clickButton(label);
        Console.withBrowser(browser).waitUntilFinished();

        String windowTitle =  PropUtils.get("config.datasources.window.connection.test.head.label");

        return Console.withBrowser(browser).openedWindow(windowTitle, TestConnectionWindow.class);
    }

    /**
     * Checks if wizard is in correct {@link Location} and tests connection
     * @return {@link TestConnectionWindow}
     */
    public TestConnectionWindow testConnection() {
        checkIfCurrentLocationIsExpected(Location.TEST_CONNECTION);
        return testConnectionUnchecked();
    }

    /**
     * Switches to detected driver tab and *DOES NOT* check if wizard is at expected {@link Location}
     */
    public void switchToDetectedDriverUnchecked() {
        root.findElement(DETECTED_DRIVER_BUTTON).click();
    }

    /**
     * Checks if wizard is at expected {@link Location} and switches to detected driver tab.
     */
    public void switchToDetectedDriver() {
        checkIfCurrentLocationIsExpected(Location.JDBC_DRIVER);
        switchToDetectedDriverUnchecked();
    }

    public ResourceTableFragment getResourceTable() {
        return Graphene.createPageFragment(ResourceManager.class, root).getResourceTable();
    }

    /**
     * Selects datasource template time on first page of datasource wizard.
     * @param templateLabel label of template which will be used
     * @return this
     */
    public DatasourceWizard selectTemplateUnchecked(String templateLabel) {
        By selectListItemSelector = ByJQuery.selector(".choose_template:contains('" + templateLabel + "')");
        getRoot().findElement(selectListItemSelector).findElement(By.tagName("input")).click();
        return this;
    }

    /**
     * Selects datasource template time on first page of datasource wizard.
     * @param templateLabel label of template which will be used
     * @return this
     */
    public DatasourceWizard selectTemplate(String templateLabel) {
        checkIfCurrentLocationIsExpected(Location.TEMPLATE_SELECTION);
        return selectTemplateUnchecked(templateLabel);
    }

    /**
     * Selects driver from list of detected drivers and *DOES NOT* check if wizard is at expected {@link Location}.
     * @param driverName name of driver to be selected
     */
    public void selectDriverUnchecked(String driverName) {
        getResourceTable().selectRowByText(0, driverName);
    }

    /**
     * Checks if wizard is at expected {@link Location} and selects driver from list of detected drivers.
     * @param driverName name of driver to be selected
     */
    public void selectDriver(String driverName) {
        checkIfCurrentLocationIsExpected(Location.JDBC_DRIVER);
        selectDriverUnchecked(driverName);
    }

    /**
     * Navigate to {@link Location} within this wizard. Be aware that each wizard instance has its own navigation.
     * @param newLocation new location which will be wizard navigated into
     * @param type type of datasource for which the wizard is opened
     * @return this
     */
    public DatasourceWizard goToLocation(Location newLocation, DatasourceType type) {
        int difference = newLocation.getIndex(type) - currentLocation.getIndex(type);
        logger.debug("Moving to '{}'.", newLocation.getName());
        if (difference == 0) {
            logger.debug("Already at desired location.");
            return this;
        }
        if (difference < 0) {
            logger.debug("Moving {} steps back.", Math.abs(difference));
            for (int i = 0; i > difference; i--) {
                back();
            }
        } else {
            logger.debug("Moving {} steps forward.", difference);
            for (int i = 0; i < difference; i++) {
                next();
            }
        }
        currentLocation = newLocation;
        return this;
    }

    private void checkIfCurrentLocationIsExpected(Location expectedLocation) {
        if (currentLocation != expectedLocation) {
            throw new IllegalStateException("Expected '" + expectedLocation.getName() + "', got '" + currentLocation.getName() + "' location.");
        }
    }

}
