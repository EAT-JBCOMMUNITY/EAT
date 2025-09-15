package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.runtime.configurationchanges.EnableConfigurationChangesDialog;
import org.jboss.hal.testsuite.fragment.runtime.configurationchanges.table.ConfigurationChangesTableFragment;
import org.jboss.hal.testsuite.page.BasePage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Abstraction over Configuration changes page
 */
public class ConfigurationChangesPage extends BasePage implements Navigatable {

    @Override
    public void navigate() {
        FinderNavigation navigation;

        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainRuntimeEntryPoint.class)
                    .step(FinderNames.BROWSE_DOMAIN_BY, FinderNames.HOSTS)
                    .step(FinderNames.HOST, ConfigUtils.getDefaultHost());
        } else {
            navigation = new FinderNavigation(browser, StandaloneRuntimeEntryPoint.class)
                    .step(FinderNames.SERVER, "Standalone Server");
        }

        navigation.selectRow().invoke("Configuration Changes");
        Application.waitUntilVisible();
    }

    public EnableConfigurationChangesDialog openEnableConfigurationChangesDialog() {
        clickButton("Enable");
        return Console.withBrowser(browser).openedWindow(EnableConfigurationChangesDialog.class);
    }

    public ConfigurationChangesTableFragment getConfigurationChangesTable() {
        By rootSelector = ConfigurationChangesTableFragment.SELECTOR;
        WebElement root = getContentRoot().findElement(rootSelector);
        return Graphene.createPageFragment(ConfigurationChangesTableFragment.class, root);
    }

    public void disable() {
        clickButton("Disable");
    }

    public void refresh() {
        clickButton("Refresh");
    }

    public Filter filter() {
        return new Filter(this);
    }

    /**
     * Abstraction over quick filter feature
     */
    public static final class Filter {

        private WebElement filterInput;
        private ConfigurationChangesPage page;

        private Filter(ConfigurationChangesPage page) {
            this.page = page;
            this.filterInput = page.getContentRoot().findElement(ByJQuery.selector(".gwt-TextBox"));
        }

        private ConfigurationChangesTableFragment.State changeState(CharSequence charSequence) {
            filterInput.click();
            filterInput.sendKeys(charSequence);
            return page.getConfigurationChangesTable().getState();
        }

        /**
         * Appends one symbol to text in input
         * @param symbol symbol to append
         * @return {@link org.jboss.hal.testsuite.fragment.runtime.configurationchanges.table.ConfigurationChangesTableFragment.State} after change
         */
        public ConfigurationChangesTableFragment.State appendSymbol(char symbol) {
            return changeState(String.valueOf(symbol));
        }

        /**
         * Removes one symbol from input using backspace
         * @return {@link org.jboss.hal.testsuite.fragment.runtime.configurationchanges.table.ConfigurationChangesTableFragment.State} after change
         */
        public ConfigurationChangesTableFragment.State removeSymbol() {
            return changeState(Keys.BACK_SPACE);
        }

        /**
         * Sets string to quick filter input field
         * @param filter string which will be set to input
         * @return {@link org.jboss.hal.testsuite.fragment.runtime.configurationchanges.table.ConfigurationChangesTableFragment.State} after change
         */
        public ConfigurationChangesTableFragment.State setFilter(String filter) {
            return changeState(filter);
        }

        /**
         * Clear input field using ctrl+a and delete
         * @return {@link org.jboss.hal.testsuite.fragment.runtime.configurationchanges.table.ConfigurationChangesTableFragment.State} after change
         */
        public ConfigurationChangesTableFragment.State clearFilter() {
            return changeState(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
        }

        /**
         * Retrieves current value of text input of filter
         * @return value of filter's text input
         */
        public String getCurrentFilter() {
            return filterInput.getAttribute("value");
        }
    }

}
