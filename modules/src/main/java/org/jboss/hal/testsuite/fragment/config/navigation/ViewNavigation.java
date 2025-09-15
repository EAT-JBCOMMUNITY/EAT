package org.jboss.hal.testsuite.fragment.config.navigation;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author jcechace
 */
public class ViewNavigation extends BaseFragment {
    public void switchView(String label) {
        String cssClass = PropUtils.get("page.content.viewnav.link.class");
        By selector = ByJQuery.selector("." + cssClass + ":contains(" + label + ")");

        WebElement link = root.findElement(selector);
        link.click();

        String cssClassActive = PropUtils.get("page.content.viewnav.link.active.class");
        Graphene.waitGui().until().element(link).attribute("class").contains(cssClassActive);
    }
}
