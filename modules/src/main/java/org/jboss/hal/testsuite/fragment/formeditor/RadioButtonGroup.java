package org.jboss.hal.testsuite.fragment.formeditor;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Implementation of radio input element.
 * Created by mvelas on 24.3.2014.
 */
public class RadioButtonGroup {

    /**
     * All radio buttons relevant to single choice.
     */
    private List<WebElement> choices;

    /**
     * Finds all radio input elements related to choice of given name.
     * @param name name of the value the radio buttons set
     */
    public RadioButtonGroup(String name, WebElement root) {
        ByJQuery selector = ByJQuery.selector("input:radio[name=" + name + "]");
        choices = root.findElements(selector);
    }

    /**
     * @param i index of the radio button
     * @return radio input of i-th choice
     */
    public WebElement getInputElement(int i) {
        return choices.get(i);
    }

    /**
     * @param value value of the radio input element
     * @return radio input of given value
     */
    public WebElement getInputElement(String value) throws ElementNotFoundException {
        for (WebElement input : choices) {
            if (input.getAttribute("value").equals(value)) {
                return input;
            }
        }
        throw new ElementNotFoundException("radio-input", "value", value);
    }

    /**
     * Picks the radio button.
     * @param index index of the button to select
     */
    public void pick(int index) {
        getInputElement(index).click();
    }

    /**
     * Picks the radio button.
     * @param value value of the button to select
     */
    public void pick(String value) {
        getInputElement(value).click();
    }

    /**
     * @return index of currently selected radio button
     */
    public int getSelectedIndex() {
        int i = 0;
        for (WebElement choice : choices) {
            if (choice.isSelected()) {
                return i;
            }
            i++;
        }
        return -1;  // nothing found
    }

    /**
     * @return value of currently selected radio button
     */
    public String getValue() {
        return getInputElement(getSelectedIndex()).getAttribute("value");
    }
}
