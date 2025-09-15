package org.jboss.hal.testsuite.page;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.AdvancedSelectBox;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.jboss.hal.testsuite.page.config.DomainConfigEntryPoint;
import org.jboss.hal.testsuite.page.config.StandaloneConfigEntryPoint;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by jcechace on 22/02/14.
 */
public class ConfigPage extends BasePage {

    public <T extends WizardWindow> T addResource(Class<T> clazz, String label) {
        clickButton(label);

        T wizard = Console.withBrowser(browser).openedWizard(clazz);

        return wizard;
    }

    public void pickProfile(String label) {
        AdvancedSelectBox picker = getProfilePicker();
        picker.pickOption(label);

        Console.withBrowser(browser).waitUntilFinished();
    }

    public void pickHost(String label) {
        AdvancedSelectBox picker = getHostPicker();
        picker.pickOption(label);

        Console.withBrowser(browser).waitUntilFinished();
    }

    public AdvancedSelectBox getHostPicker() {
        return getContextPicker(PropUtils.get("navigation.context.host.id"));
    }


    public AdvancedSelectBox getProfilePicker() {
        return getContextPicker(PropUtils.get("navigation.context.profile.id"));
    }

    private AdvancedSelectBox getContextPicker(String label) {
        WebElement pickerRoot = getContextPickerRootByLabel(label);
        AdvancedSelectBox selectBox = Graphene.createPageFragment(AdvancedSelectBox.class,
                pickerRoot);

        return selectBox;

    }

    private WebElement getContextPickerRootByLabel(String label) {
        By selector = By.id(label);
        WebElement element = browser.findElement(selector);
        return element;
    }

    @Deprecated
    public ResourceTableFragment getResourceTable() {
        String cssClass = PropUtils.get("resourcetable.class");
        By selector = ByJQuery.selector("." + cssClass + ":visible");
        WebElement tableRoot = getContentRoot().findElement(selector);
        ResourceTableFragment table = Graphene.createPageFragment(ResourceTableFragment.class, tableRoot);

        return table;
    }

    @Deprecated
    public <T extends WizardWindow> T addResource(Class<T> clazz) {
        String label = PropUtils.get("config.shared.add.label");
        return addResource(clazz, label);
    }

    @Deprecated
    public WizardWindow addResource() {
        return addResource(WizardWindow.class);
    }

    @Deprecated
    public <T extends WindowFragment> T removeResource(String name, Class<T> clazz) {
        selectByName(name);
        String label = PropUtils.get("config.shared.remove.label");
        clickButton(label);

        T window = Console.withBrowser(browser).openedWindow(clazz);

        return window;
    }

    @Deprecated
    public ConfirmationWindow removeResource(String name) {
        return removeResource(name, ConfirmationWindow.class);
    }

    /**
     * Select resource based on its name in firt column of resource table.
     *
     * @param name Name of the resource.
     */
    @Deprecated
    public ResourceTableRowFragment selectByName(String name) {
        return getResourceTable().selectRowByText(0, name);
    }

    /**
     * Select resource based on its name in first column of resource table and then
     * click on view option
     *
     * @param name Name of the resource.
     */
    @Deprecated
    public void viewByName(String name) {
        ResourceTableRowFragment row = selectByName(name);
        row.view();
    }

    public void navigate(String profile) {
        Graphene.goTo(DomainConfigEntryPoint.class);
        Console.withBrowser(browser).waitUntilLoaded();
        pickProfile(profile);
        navigate();
    }

    /**
     * @param subsystemName - subsystem label in finder navigation
     * @return {@link FinderNavigation} with prefilled steps to subsystem with provided subsystem label.
     * In case of managed domain the subsystem is selected under full profile.
     */
    protected FinderNavigation getSubsystemNavigation(String subsystemName) {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, ConfigUtils.getDefaultProfile());
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        return navigation.step(FinderNames.SUBSYSTEM, subsystemName);
    }

}
