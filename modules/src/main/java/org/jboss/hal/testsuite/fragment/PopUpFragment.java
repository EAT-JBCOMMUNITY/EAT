package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by jcechace on 18/02/14.
 */
public class PopUpFragment extends BaseFragment {
    public static final By ROOT_SELECTOR = By.className(PropUtils.get("modals.popup.class"));

    public void clickLinkByLabel(String label) {
        ByJQuery selector = ByJQuery.selector(".inline-link a:contains('" + label + "')");
        WebElement link = root.findElement(selector);
        link.click();
    }

}
