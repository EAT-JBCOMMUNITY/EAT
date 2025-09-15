package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.iiop.IIOPConfigArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by pcyprian on 12.8.15.
 */
public class IIOPPage extends ConfigurationPage {

    public void switchToEditMode() {
        WebElement viewPanel = browser.findElement(By.className("form-view-panel"));
        WebElement editLink = viewPanel.findElement(By.className("form-edit-button"));
        editLink.click();
        waitUntilPanelIsVisible();
    }

    public void waitUntilPanelIsVisible() {
        // wait until we switched to edit mode
        Graphene.waitGui().until().element(By.className("form-edit-panel")).is().visible();
    }

    public void waitUntilPropertiesAreVisible() {
        // wait until we switched to properties
        Graphene.waitGui().until().element(By.className("default-cell-table")).is().visible();
    }

    public ConfigFragment getConfigFragment() {
        WebElement editPanel = browser.findElement(By.className("default-tabpanel"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

    public IIOPConfigArea getConfig() {
        return getConfig(IIOPConfigArea.class);
    }
}
