package org.jboss.hal.testsuite.fragment.formeditor;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.Library;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by jcechace on 03/03/14.
 */
public class Editor extends BaseFragment {

    private static final Logger log = LoggerFactory.getLogger(Editor.class);

    /**
     * Returns either a input of type text or textarea element with given identifier (name or id).
     *
     * @param identifier
     * @return a text element
     */
    public WebElement getText(String identifier) {
        return findTextElement(identifier);
    }

    /**
     * Sets the value of given text element.
     *
     * @param identifier
     * @param value
     */
    public void text(String identifier, String value) {
        WebElement input = getText(identifier);
        if (!input.isDisplayed()) {
            Console.withBrowser(browser).pageDown();
        }
        input.clear();
        log.debug("setting value '{}' to the text element '{}'", value, identifier);
        Graphene.waitGui().until().element(input).value().equalTo("");
        input.sendKeys(value);
        Graphene.waitGui().until().element(input).value().equalTo(value);
    }

    /**
     * Enters text as humanly as possible. E.g. makes pauses between characters and utilizes keyboard shortcuts. <b>
     * Use only as a workaround when {@link #text(String, String)} is not working properly!</b>
     * @param identifier identifier of element to be written to
     * @param value a value to be entered
     */
    public void enterTextLikeHuman(String identifier, String value) {
        WebElement element = getText(identifier);
        if (!element.isDisplayed()) {
            Console.withBrowser(browser).pageDown();
        }
        element.click();
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        Library.letsSleep(30);
        element.sendKeys(Keys.DELETE);
        for (char character : value.toCharArray()) {
            element.sendKeys(String.valueOf(character));
            Library.letsSleep(20);
        }
    }

    /**
     * Reads the value from text element with given identifier.
     *
     * @param identifier
     * @return a value of text element
     */
    public String text(String identifier) {
        WebElement input = getText(identifier);

        return input.getAttribute("value");
    }


    private WebElement findSelect(String identifier) {
        By selector = ByJQuery.selector(
                "select[id$='" + identifier + "']:visible," +
                        "select[name='" + identifier + "']:visible, " +
                        "tr[data-dmr-attr='" + identifier + "'] select:visible, "
        );

        return findElement(selector, root);
    }

    /**
     * Sets the value of given select
     *
     * @param identifier
     * @param value
     */
    public void select(String identifier, String value) {
        Select select = new Select(findSelect(identifier));
        select.selectByVisibleText(value);
    }

    /**
     * Returns the value of select with given identifier
     *
     * @param identifier
     * @return text of selected option
     */
    public String select(String identifier) {
        Select select = new Select(findSelect(identifier));
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Returns a password input with given identifier
     *
     * @param identifier
     * @return a password input
     */
    public WebElement getPassword(String identifier) {
        return findInputElement("password", identifier);
    }

    public void password(String identifier, String value) {
        WebElement input = getPassword(identifier);
        input.clear();
        Graphene.waitGui().until().element(input).value().equalTo("");
        input.sendKeys(value);
        log.debug("password '{}' was set", input.getText());
    }

    /**
     * Returns file input element with a given identifier
     *
     * @param identifier
     * @return a file input element
     */
    public WebElement getFileInputElement(String identifier) {
        return findInputElement("file", identifier);
    }

    /**
     * @param fileToUpload
     * @param identifier
     */
    public void uploadFile(File fileToUpload, String identifier) {
        WebElement fileInput = getFileInputElement(identifier);
        log.debug("uploading file '{}'", fileToUpload.toString());
        fileInput.sendKeys(fileToUpload.getAbsolutePath());
        Graphene.waitGui().until().element(fileInput).value().equalTo(fileToUpload.getName());
    }


    /**
     * Returns a checkbox with given identifier.
     *
     * @param identifier
     * @return a checkbox element
     */
    public WebElement getCheckbox(String identifier) {
        return findInputElement("checkbox", identifier);
    }

    /**
     * Sets the value of checkbox with given identifier.
     *
     * @param identifier
     * @param value
     */
    public void checkbox(String identifier, boolean value) {
        WebElement input = getCheckbox(identifier);
        boolean current = input.isSelected();

        log.debug("{} checkbox '{}'", (value ? "setting" : "unsetting"), identifier);
        if (value != current) {
            input.click();
        }

        if (value) {
            Graphene.waitGui().until().element(input).is().selected();
        } else {
            Graphene.waitGui().until().element(input).is().not().selected();
        }
    }

    /**
     * Reads  the value of checkbox with given identifier.
     *
     * @param identifier
     * @return value of checkbox
     */
    public boolean checkbox(String identifier) {
        WebElement input = getCheckbox(identifier);

        boolean res = input.isSelected();
        log.debug("checkbox '{}' {} set", identifier, (res ? "is" : "isn't"));

        return res;
    }


    /**
     * Returns a property editor object found within editor's root.
     *
     * @return a property editor
     */
    public PropertyEditor properties() {
        PropertyEditor properties = Console.withBrowser(browser).getPropertyEditor(root);

        return properties;
    }

    private WebElement findTextElement(String identifier) {
        WebElement text = null;
        try {
            text = findInputElement("text", identifier);
        } catch (NoSuchElementException | TimeoutException ignore) {
            log.debug("not found - looking for textarea '{}'", identifier);

            //String byIdSelector = "textarea[id$='" + identifier + "']:visible";
            //String byNameSelector = "textarea[name='" + identifier + "']:visible";
            String byIdSelector = "input[id$='" + identifier + "']:visible";
            String byNameSelector = "input[name='" + identifier + "']:visible";
            String byDmrAttrSelector = "tr[data-dmr-attr='" + identifier + "'] textarea:visible";
            By selector = ByJQuery.selector(byIdSelector + ", " + byNameSelector + ", " + byDmrAttrSelector);
            text = findElement(selector, root);
        }

        return text;
    }

    /**
     * If element is not displayed try to show him by PAGE_DOWN on root of this fragment
     *
     * @param type
     * @param identifier
     * @return
     */
    private WebElement findInputElement(String type, String identifier) {
        log.debug("looking for the '{}' input element identified by '{}'", type, identifier);

        String byIdSelector = "input[type='" + type + "'][id$='_" + identifier + "']:visible";
        String byNameSelector = "input[type='" + type + "'][name='" + identifier + "']:visible";
        String byDmrAttrSelector = "tr[data-dmr-attr='" + identifier + "'] input:visible";
        By selector = ByJQuery.selector(byIdSelector + ", " + byNameSelector + ", " + byDmrAttrSelector);

        Graphene.waitGui().withTimeout(500, TimeUnit.MILLISECONDS).until().element(selector).is().present();
        WebElement input = findElement(selector, root);
        if (!input.isDisplayed()) {
            // maybe just too long form
            Console.withBrowser(browser).pageDown();
        }
        return input;
    }

    private WebElement findElement(By selector, WebElement root) {
        return Console.withBrowser(browser).findElement(selector, root);
    }

    /**
     * @param name name of the radio button input elements
     * @return radio button related to the presented name
     */
    private RadioButtonGroup findRadioButton(String name) {
        log.debug("looking for the radio buttons for '{}'", name);
        RadioButtonGroup button = new RadioButtonGroup(name, root);
        return button;
    }

    /**
     * Select the index-th radio button of given name
     *
     * @param name  name of the radio button input elements
     * @param index index of the radio button to select
     */
    public void radioButton(String name, int index) {
        RadioButtonGroup button = findRadioButton(name);
        log.debug("picking {}-th radio button", index);
        button.pick(index);
        Graphene.waitGui().until().element(button.getInputElement(index)).is().selected();
    }

    /**
     * Select the radio button of given name and value
     *
     * @param name  name of the radio button input elements
     * @param value value of the radio button to select
     */
    public void radioButton(String name, String value) {
        RadioButtonGroup button = findRadioButton(name);
        log.debug("picking radio button with value '{}'", value);
        button.pick(value);
        Graphene.waitGui().until().element(button.getInputElement(value)).is().selected();
    }

    /**
     * @param name name of the radio button input elements
     * @return the value of selected radio button of given name
     */
    public String radioButton(String name) {
        RadioButtonGroup button = findRadioButton(name);

        String value = button.getValue();
        log.debug("Selected radio button has value {}", value);

        return value;
    }
}
