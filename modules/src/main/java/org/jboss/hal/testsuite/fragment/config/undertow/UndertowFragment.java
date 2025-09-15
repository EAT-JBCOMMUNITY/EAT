package org.jboss.hal.testsuite.fragment.config.undertow;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.cli.Library;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 15.9.15.
 */
public class UndertowFragment extends ConfigFragment {

    public Boolean isErrorShowedInForm() {
        By selector = ByJQuery.selector("div.form-item-error-desc:visible");
        return isElementVisible(selector);
    }

    public Boolean isElementVisible(By selector) {
        try {
            Graphene.waitAjax().until().element(selector).is().visible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Boolean editTextAndSave(String identifier, String value) {
        Editor editor = edit();
        editor.text(identifier, value);
        return save();
    }

    public void editTextHumanly(Editor editor, String identifier, String value) {
        WebElement input = editor.getText(identifier);
        while (!input.isDisplayed()) {
            Console.withBrowser(browser).pageDown();
        }
        input.click();
        int length = input.getAttribute("value").length();
        input.click();

        //clear one by one
        for (int i = 0; i < length; i++) {
            input.sendKeys(Keys.DELETE);
            input.click();
            Library.letsSleep(100);
        }

        Graphene.waitGui().until().element(input).value().equalTo("");
        input.click();

        //write one by one
        for (char c : value.toCharArray()) {
            input.sendKeys(Character.toString(c));
            input.click();
            Library.letsSleep(100);
        }
        input.click();
        Graphene.waitGui().until().element(input).value().equalTo(value);
    }

    public Boolean editCheckboxAndSave(String identifier, Boolean value) {
        edit().checkbox(identifier, value);
        return save();
    }

    public Boolean selectOptionAndSave(String identifier, String value) {
        edit().select(identifier, value);
        return save();
    }

}
