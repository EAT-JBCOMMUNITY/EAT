package org.jboss.hal.testsuite.mbui;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.cli.Library;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by pcyprian on 26.11.15.
 */
@Deprecated
public class MbuiNavigation {

    @Drone
    private WebDriver browser;

    public MbuiNavigation( WebDriver browser) {
        this.browser = browser;
    }

    //Mbui utils
    public void displayMBuiSubTree(String label) {
        WebElement leftMenu = browser.findElement(By.className("split-west"));
        // (ByJQuery.selector("(tr td:contains(subsystem)).siblings(td:has(img))"));
        WebElement tree =  leftMenu.findElement(By.className("gwt-Tree"));
        tree.findElement(By.xpath("//tr[.//td/div[contains(text(),'" + label + "')]]/td/img")).click();
        Library.letsSleep(1000);
    }

    public void selectItemInMBuiTree(String name) {
        browser.findElement(ByJQuery.selector("div.gwt-TreeItem:contains(" + name + ")")).click();
    }

    public void checkAndAssertMBuiValueOf(String label, String expectedValue) {
        WebElement table = browser.findElement(By.className("fill-layout-width"));
        String value = table.findElement(By.xpath("//tr[.//td/div/div[contains(text(), '" + label + "')]]/td/div/span")).getText();
        assertEquals("Value of " + label + "is differrent in CLI and deployment MBUI table.", expectedValue, value);
        Library.letsSleep(1000);
    }
}
