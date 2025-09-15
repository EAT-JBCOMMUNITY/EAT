package org.jboss.hal.testsuite.fragment.rhaccess;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by mvelas on 4.11.14.
 */
public class RHAccessOpenTicketFragment extends RHAccessFragment {

    protected final By MY_ACCOUNT_BUTTON = ByJQuery.selector("button:contains(My Account)");

    protected final String OWNER_SELECTION_ID = PropUtils.get("rhaccess.open.case.owner.id");
    protected final By OWNER_BUTTON_SELECTOR = ByJQuery.selector("#" + OWNER_SELECTION_ID + " a");

    protected final String SUMMARY_INPUT_ID = PropUtils.get("rhaccess.open.case.summary.id");
    protected final String DESCRIPTION_AREA_CLASS = PropUtils.get("rhaccess.open.case.description.class");

    public void setMyAccount() {
        waitLongUntilElementIs(MY_ACCOUNT_BUTTON, root).enabled();
        root.findElement(MY_ACCOUNT_BUTTON).click();
    }

    public void setOwner(final String ownerName) {
        waitLongUntilElementIs(OWNER_BUTTON_SELECTOR, root).enabled();
        root.findElement(OWNER_BUTTON_SELECTOR).click();
        // TODO pick from drop-down menu
    }

    public void setProduct(final String productName) {
        setDropDownField(PropUtils.get("rhaccess.open.case.product.id"), productName);
    }

    public void setVersion(final String version) {
        setDropDownField(PropUtils.get("rhaccess.open.case.product.version.id"), version);
    }

    protected void setDropDownField(final String selectorId, final String value) {
        By statusSelector = ByJQuery.selector("select[id=" + selectorId + "]");
        waitLongUntilElementIs(statusSelector, root).visible();
        WebElement selectElement = root.findElement(statusSelector);
        selectElement.click();
        Select statusSelect = new Select(selectElement);
        statusSelect.selectByVisibleText(value);
    }

    protected WebElement getField(final String title) {
        return root.findElement(ByJQuery.selector("div.rha-create-field:contains(" + title + ")"));
    }

    public void setSummary(final String summary) {
        root.findElement(By.id(SUMMARY_INPUT_ID)).sendKeys(summary);
    }

    public void setDescription(final String description) {
        root.findElement(ByJQuery.selector("textarea." + DESCRIPTION_AREA_CLASS)).sendKeys(description);
    }

    public void next() {
        clickEnabledButton("Next");
    }

    public void submit() {
        clickEnabledButton("Submit");
    }

    protected void clickEnabledButton(final String textLabel) {
        WebElement button = getButton(textLabel);
        Graphene.waitGui().until().element(button).is().enabled();
        button.click();
    }

    public void setGroup(final String group) {
        // setDropDownField("Case Group:", group);
        WebElement fieldElement = getField("Case Group:");
        fieldElement.click();
        By groupSelector = ByJQuery.selector("li:contains(" + group + ")");
        WebElement groupElement = fieldElement.findElement(groupSelector);
        groupElement.click();
    }
}
