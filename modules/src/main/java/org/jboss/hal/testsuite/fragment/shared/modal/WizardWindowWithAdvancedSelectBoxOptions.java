package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@link WizardWindow} which has select box on its first page
 */
public class WizardWindowWithAdvancedSelectBoxOptions extends WizardWindow {

    /**
     * Picks a option by its label in select box
     * @param label label of desired option
     * @return this
     */
    public WizardWindowWithAdvancedSelectBoxOptions pick(String label) {
        List<WebElement> elements = getOptionElements();

        for (WebElement elem : elements) {
            if (elem.getText().equalsIgnoreCase(label)) {
                elem.click();
                Console.withBrowser(browser).waitUntilFinished();
                return this;
            }
        }
        throw new NoSuchElementException("Unable to find option with value " + label);
    }

    /**
     * Obtains {@link WebElement}s of available options in select box
     * @return list of labels of options
     */
    public List<WebElement> getOptionElements() {
        String cssClass = PropUtils.get("components.selectbox.item.class");
        By selector = By.className(cssClass);

        return root.findElements(selector);
    }

    /**
     * Obtains labels of available options in select box
     * @return list of labels of options
     */
    public List<String> getOptions() {
        return getOptionElements().stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    /**
     * Clicks on 'Continue' button to get on next page
     * @return this
     */
    public WizardWindowWithAdvancedSelectBoxOptions clickContinue() {
        clickButton("Continue");

        Console.withBrowser(browser).waitUntilFinished();
        return this;
    }
}
