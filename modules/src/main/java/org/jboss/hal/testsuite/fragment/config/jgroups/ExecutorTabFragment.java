package org.jboss.hal.testsuite.fragment.config.jgroups;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by jkasik on 22.7.15.
 */
public class ExecutorTabFragment extends BaseFragment {

    public void openExecutors() {
        By selector = ByJQuery.selector("a");
        WebElement link = root.findElement(selector);
        link.click();
        Graphene.waitGui().until().element(root).attribute("class").not().contains("gwt-DisclosurePanel-closed");
    }
}
