package org.jboss.hal.testsuite.page.config.elytron;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Abstract Elytron subsystem configuration page with methods common to various particular Elytron configuration pages.
 *
 * @param <PARTICULAR_PAGE> refers to the child type -  it's needed to parent methods be able to return the child type.
 */
public abstract class AbstractElytronConfigPage<PARTICULAR_PAGE extends AbstractElytronConfigPage<PARTICULAR_PAGE>>
        extends ConfigPage {

    public abstract PARTICULAR_PAGE navigateToApplication();

    public boolean resourceIsPresentInMainTable(String resourceName) {
        return getConfigFragment().resourceIsPresent(resourceName);
    }

    public ConfigFragment switchToConfigAreaTab(final String tabName) {
        return getConfig().switchTo(tabName);
    }

    public boolean resourceIsPresentInConfigAreaTable(String resourceName) {
        return resourceIsPresentInConfigAreaTable(resourceName, 0);
    }

    public boolean resourceIsPresentInConfigAreaTable2ndColumn(String resourceName) {
        return resourceIsPresentInConfigAreaTable(resourceName, 1);
    }

    private boolean resourceIsPresentInConfigAreaTable(String resourceName, int tableColumnIndex) {
        By configAreaSelector = ByJQuery.selector("." + PropUtils.get("configarea.class") + ":visible");
        WebElement configAreaRoot = getContentRoot().findElement(configAreaSelector);
        ConfigFragment configFragment = Graphene.createPageFragment(ConfigFragment.class, configAreaRoot);
        return configFragment.resourceIsPresent(resourceName, tableColumnIndex);
    }

    public ResourceManager getConfigAreaResourceManager() {
        By configAreaContentSelector = ByJQuery.selector("." + PropUtils.get("configarea.content.class") + ":visible");
        return Graphene.createPageFragment(ResourceManager.class, browser.findElement(configAreaContentSelector));
    }

    /**
     * Select particular Elytron resource in the list on the left side of the application detail.
     */
    @SuppressWarnings("unchecked")
    public PARTICULAR_PAGE selectResource(String resourceLabel) {
        switchSubTab(resourceLabel);
        return (PARTICULAR_PAGE) this;
    }
}
