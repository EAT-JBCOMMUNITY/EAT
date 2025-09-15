package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.cli.Library;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * Created by jcechace on 01/03/14.
 */
public class ConfigFragment extends BaseFragment {

    public ResourceManager getResourceManager() {
        return Graphene.createPageFragment(ResourceManager.class, root);
    }

    private By getEditButtonSelector() {
        return ByJQuery.selector("." + PropUtils.get("configarea.edit.button.class") + ":visible");
    }

    public WebElement getEditButton() {
        return root.findElement(getEditButtonSelector());
    }

    public boolean isInEditMode() {
        return !isElementVisible(getEditButtonSelector());
    }

    /**
     * Clicks the edit button in config area and returns the editor
     *
     * @return Editor
     */
    public Editor edit() {
        WebElement button = getEditButton();
        button.click();

        Graphene.waitGui().until().element(button).is().not().visible();

        return getEditor();
    }

    /**
     * Click the save button in read-write (form) mode.
     *
     * @return True if configuration switched into read-only mode. False otherwise
     */
    public boolean save() {
        WebElement button = getButton(PropUtils.get("configarea.save.button.label"));
        if (!button.isDisplayed()) {
            Console.withBrowser(browser).pageDown();
            Library.letsSleep(100);
        }
        button.click();
        try {
            Graphene.waitModel().until().element(getEditButton()).is().visible();
            return true;
        } catch (WebDriverException e) {
            return false;
        }
    }

    /**
     * Click cancel and switch back to read-only mode.
     */
    public void cancel() {
        clickButton(PropUtils.get("configarea.cancel.button.label"));
        Graphene.waitModel().until().element(getEditButton()).is().visible();
    }

    /**
     * Calls  {@link #save()}  save} method and asserts the output
     *
     * @param expected <code>true</code>if wizard is expected to finish, <code>false</code> otherwise
     */
    @Deprecated
    public void saveAndAssert(boolean expected) {
        boolean finished = this.save();

        if (expected) {
            Assert.assertTrue("Config was supposed to be saved successfully, read view should be active.", finished);
        } else {
            Assert.assertFalse("Config wasn't supposed to be saved, read-write view should be active.", finished);
        }
    }

    public boolean resourceIsPresent(String name, int tableColumnIndex) {
        long start = System.currentTimeMillis();
        while (getResourceManager().getResourceTable().getRowByText(tableColumnIndex, name) == null) {
            if (System.currentTimeMillis() >= start + 2000) return false;
            Library.letsSleep(200);
        }
        return true;
    }

    public boolean resourceIsPresent(String name) {
        return resourceIsPresent(name, 0);
    }

    public Boolean editTextAndSave(String identifier, String value) {
        edit().text(identifier, value);
        return save();
    }

    public Boolean editCheckboxAndSave(String identifier, Boolean value) {
        edit().checkbox(identifier, value);
        return save();
    }

    public Boolean selectOptionAndSave(String identifier, String value) {
        edit().select(identifier, value);
        return save();
    }

    public Boolean isErrorShownInForm() {
        By selector = ByJQuery.selector("div.form-item-error-desc:visible");
        return isElementVisible(selector);
    }

    private Boolean isElementVisible(By selector) {
        try {
            Graphene.waitAjax().until().element(selector).is().visible();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
