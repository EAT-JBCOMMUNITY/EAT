package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class UserMenuFragment extends PopUpFragment {

    public void logout() {
        clickMenuItem("Logout");
        Console.withBrowser(browser).openedWindow().clickButton(PropUtils.get("modals.confirmation.confirm.label"));
    }

    public void clickMenuItem(String label) {
        By selector = ByJQuery.selector("." + PropUtils.get("modals.menu.item.class") + ":contains('" + label + "')");
        root.findElement(selector).click();
    }
}
