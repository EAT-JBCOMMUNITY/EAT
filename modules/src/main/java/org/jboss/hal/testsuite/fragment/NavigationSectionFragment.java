package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.jboss.hal.testsuite.util.Console;

/**
 * Created by jcechace on 16/02/14.
 */
public class NavigationSectionFragment extends BaseFragment {

    /**
     *  Click left menu link based on the value of token attribute
     *
     * @param token
     */
    public void goToItem(String token) {
        ByJQuery selector = ByJQuery.selector(".lhs-tree-item[token='" + token + "']");
        WebElement item = root.findElement(selector);
        item.click();

        Console.withBrowser(browser).waitForContent();
    }

    /**
     *  Click left menu link inside given group based on the value of token attribute
     *
     * @param groupId
     * @param token
     */
    public void goToItem(String groupId, String token) {
        By groupSelector = By.id(groupId);
        WebElement group = root.findElement(groupSelector);

        By groupItemSelector = By.className("gwt-TreeItem");
        WebElement groupItem = group.findElement(groupItemSelector);

        By itemSelector = ByJQuery.selector(".lhs-tree-item[token='" + token + "']");
        WebElement item = root.findElement(itemSelector);

        if (!item.isDisplayed()) {
            groupItem.click();
            Graphene.waitGui().until().element(item).is().visible();
        }

        item.click();

        Console.withBrowser(browser).waitForContent();
    }
}
