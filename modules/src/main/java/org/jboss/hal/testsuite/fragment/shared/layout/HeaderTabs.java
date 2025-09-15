package org.jboss.hal.testsuite.fragment.shared.layout;

import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Created by jcechace on 15/02/14.
 */
public class HeaderTabs extends BaseFragment {

    public WebElement getItemByLabel(String label) {
        ByJQuery selector = ByJQuery.selector("*[#role='menuitem']:contains('" + label + "')");
        WebElement link = root.findElement(selector);
        return link;
    }

    public WebElement getItem(String id) {
        By selector = By.id(id);
        WebElement link = root.findElement(selector);
        return link;
    }

    public void clickItemByLabel(String label) {
        WebElement link = getItemByLabel(label);
        link.click();
    }

    public void clickItem(String id) {
        WebElement link = getItem(id);
        link.click();
    }


    // Convenience methods

    public void goToConfiguration() {
        clickItem(PropUtils.get("header.tabs.config.id"));
    }

    public void goToRuntime() {
        clickItem(PropUtils.get("header.tabs.runtime.id"));
    }

    public void goToAdministration() {
        clickItem(PropUtils.get("header.tabs.admin.id"));
    }
}
