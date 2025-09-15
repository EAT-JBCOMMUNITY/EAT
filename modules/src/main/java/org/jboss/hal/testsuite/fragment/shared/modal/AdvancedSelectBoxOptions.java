package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.PopUpFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jcechace
 */
public class AdvancedSelectBoxOptions extends PopUpFragment {

    public void pick(String label) {
        List<WebElement> elements = getOptionElements();

        for (WebElement elem : elements) {
            if (elem.getText().equalsIgnoreCase(label)) {
                elem.click();
                Graphene.waitGui().until().element(root).is().not().present();
                return;
            }
        }

        throw new NoSuchElementException("Unable to find option with value " + label);
    }

    public List<WebElement> getOptionElements() {
        String cssClass = PropUtils.get("components.selectbox.item.class");
        By selector = By.className(cssClass);

        List<WebElement> elements = root.findElements(selector);

        return elements;
    }


    public List<String> getOptions() {
        List<WebElement> elements = getOptionElements();
        List<String> options = new ArrayList<String>();

        for (WebElement elem : elements) {
            options.add(elem.getText());
        }

        return options;
    }
}
