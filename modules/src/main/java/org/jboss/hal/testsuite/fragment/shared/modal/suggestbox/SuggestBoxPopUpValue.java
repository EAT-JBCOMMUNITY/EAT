package org.jboss.hal.testsuite.fragment.shared.modal.suggestbox;

import org.openqa.selenium.WebElement;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 8/22/16.
 *
 *  This class represents one line in opened suggest box pop up.
 */
public class SuggestBoxPopUpValue {

    private WebElement webElement;

    SuggestBoxPopUpValue(WebElement webElement) {
        this.webElement = webElement;
    }

    public void select() {
        webElement.click();
    }

    @Override
    public String toString() {
        return webElement.getText();
    }
}
