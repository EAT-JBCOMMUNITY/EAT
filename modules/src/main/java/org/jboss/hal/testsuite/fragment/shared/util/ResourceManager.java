package org.jboss.hal.testsuite.fragment.shared.util;

import com.google.common.base.Predicate;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.fragment.shared.table.GenericResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ResourceManager extends BaseFragment {

    private static final Logger log = LoggerFactory.getLogger(ResourceManager.class);

    private static final By RHS_CONTENT_PANEL_SELECTOR = ByJQuery.selector(".rhs-content-panel:visible");
    private static final By BOTTOM_CONFIG_SELECTOR = ByJQuery.selector("table.fill-layout-width:visible");
    private static final By WIZARD_TABLE_SELECTOR = ByJQuery.selector(".default-cell-table.master_detail-master");
    private static final By POPUP_LAYOUT_WIDTH_SELECTOR = ByJQuery.selector("table.fill-popupLayout-width:visible");

    /**
     * Get resource table on page
     * @param clazz Class for the {@link ResourceTableFragment} to be converted to
     * @return {@link ResourceTableFragment} if found
     */
    public <T extends GenericResourceTableFragment> T getResourceTable(Class<T> clazz) {

        WebElement tableRoot;
        if (!getRoot().findElements(RHS_CONTENT_PANEL_SELECTOR).isEmpty()) {
            log.debug("Found table at top of page!");
            tableRoot = getRoot().findElement(RHS_CONTENT_PANEL_SELECTOR);
        } else if (!getRoot().findElements(BOTTOM_CONFIG_SELECTOR).isEmpty()) {
            // when table root is in bottom config, there is no rhs-content-panel
            log.debug("Found table in config!");
            tableRoot = getRoot().findElement(BOTTOM_CONFIG_SELECTOR);
        } else if (root.getAttribute("class").equals("default-window") && //root is wizard
                !getRoot().findElements(WIZARD_TABLE_SELECTOR).isEmpty()) {
            // when table is in wizard
            log.debug("Found table in wizard!");
            tableRoot = getRoot().findElement(WIZARD_TABLE_SELECTOR);
        } else if (!getRoot().findElements(POPUP_LAYOUT_WIDTH_SELECTOR).isEmpty()) {
            log.debug("Found table using non-standard selector '{}'!", POPUP_LAYOUT_WIDTH_SELECTOR.toString());
            tableRoot = getRoot().findElement(POPUP_LAYOUT_WIDTH_SELECTOR);
        } else {
            throw new NotFoundException("No resource table was found within specified root!");
        }

        return Graphene.createPageFragment(clazz, tableRoot);
    }

    public ResourceTableFragment getResourceTable() {
        return getResourceTable(ResourceTableFragment.class);
    }

    /**
     * Select resource based on its name in first column of resource table.
     *
     * @param name Name of the resource.
     */
    public ResourceTableRowFragment selectByName(String name) {
        return getResourceTable().selectRowByText(0, name);
    }

    public <T extends WizardWindow> T addResource(Class<T> clazz, String label) {
        clickButton(label);

        return Console.withBrowser(browser).openedWizard(clazz);
    }

    public <T extends WizardWindow> T addResource(Class<T> clazz) {
        String label = PropUtils.get("config.shared.add.label");
        return addResource(clazz, label);
    }

    public WizardWindow addResource() {
        return addResource(WizardWindow.class);
    }

    public <T extends WindowFragment> T removeResource(String name, Class<T> clazz) {
        selectByName(name);
        invokeRemove();
        return Console.withBrowser(browser).openedWindow(clazz);
    }

    public void invokeRemove() {
        String label = PropUtils.get("config.shared.remove.label");
        clickButton(label);
    }

    public ConfirmationWindow removeResource(String name) {
        return removeResource(name, ConfirmationWindow.class);
    }

    /**
     * When 'Reload required' window is expected use {@link ResourceManager#removeResource(String)}
     * .{@link ConfirmationWindow#confirmAndDismissReloadRequiredMessage()} instead!
     */
    public void removeResourceAndConfirm(String name) {
        ConfirmationWindow confirmationWindow = removeResource(name, ConfirmationWindow.class);
        confirmationWindow.confirm();
    }

    /**
     * Select resource based on its name in first column of resource table and then
     * click on view option
     *
     * @param name Name of the resource.
     */
    public void viewByName(String name) {
        ResourceTableRowFragment row = selectByName(name);
        row.view();
    }

    public List<String> listResources() {
        return getResourceTable().getTextInColumn(0);
    }

    /**
     * Checks if given resource is present in resource table
     * @param name name of resource
     * @return true if resource is present, false otherwise
     */
    public boolean isResourcePresent(String name) {
        try {
            Graphene.waitModel(browser)
                    .pollingEvery(100, TimeUnit.MILLISECONDS)
                    .withTimeout(2000, TimeUnit.MILLISECONDS)
                    .until((WebDriver input) -> getResourceTable().getRowByText(0, name));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

}
