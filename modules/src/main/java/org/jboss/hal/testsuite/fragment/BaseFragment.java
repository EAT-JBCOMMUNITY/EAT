package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.fragment.Root;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jcechace on 19/02/14.
 */
public class BaseFragment {
    @Drone
    protected WebDriver browser;

    @Root
    protected WebElement root;

    public WebElement getRoot() {
        return root;
    }

    public Editor getEditor() {
        Editor editor = Graphene.createPageFragment(Editor.class, root);

        return editor;
    }

    public WebElement getButton(String label) {
        By selector = ByJQuery.selector("" +
                        "button#" + label + ":visible," +
                        "button:contains('" + label + "'):visible," +
                        "div.btn:contains('" + label + "'):visible"
        );
        WebElement button = root.findElement(selector);
        return button;
    }

    public void clickButton(String label) {
        WebElement button = getButton(label);
        button.click();
    }

    /**
     * @param dmrAttributeName .. {@code data-dmr-attr} attribute of {@code <tr>} tag
     */
    public String getAttributeValue(String dmrAttributeName) {
        return root.findElement(ByJQuery.selector(
                "tr[data-dmr-attr='" + dmrAttributeName + "'] .form-item-value:visible")).getAttribute("textContent");
    }
}
