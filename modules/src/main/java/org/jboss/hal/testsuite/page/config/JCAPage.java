package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.jca.JCAConfigArea;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by pcyprian on 24.8.15.
 */
public class JCAPage extends ConfigurationPage implements Navigatable {

    @Drone
    private WebDriver browser;

    public JCAConfigArea getConfig() {
        return getConfig(JCAConfigArea.class);
    }

    public void switchToConnectionDefinitions() {
        WebElement viewPanel = browser.findElement(By.className("paged-view-navigation-container"));
        WebElement editLink = viewPanel.findElement(By.linkText("Connection Definitions"));
        editLink.click();
    }

    public void switchToArchiveValidation() {
        WebElement archive = browser.findElement(ByJQuery.selector("div.gwt-Label:contains(Archive Validation)"));
        archive.click();
    }

    public void switchToSizing() {
        WebElement archive = browser.findElement(ByJQuery.selector("div.gwt-Label:contains(Sizing)"));
        archive.click();
    }

    public void switchToBeanValidation() {
        WebElement bean = browser.findElement(ByJQuery.selector("div.gwt-Label:contains(Bean Validation)"));
        bean.click();
    }

    public void switchToBootstrapContextsTab() {
        switchTab("Bootstrap Contexts");
    }

    public void switchToWorkManagerTab() {
        switchTab("Work Manager");
    }

    public void clickView() {
        WebElement view = browser.findElement(By.className("viewlink-cell"));
        view.click();
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

    public ConfigFragment getWindowFragment() {
        WebElement editPanel = browser.findElement(By.className("default-window-content"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

    public void navigate() {
        FinderNavigation navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, "JCA");

        navigation.selectRow().invoke("View");
        Application.waitUntilVisible();
    }
}
