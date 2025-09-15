package org.jboss.hal.testsuite.fragment.rhaccess;

import java.util.List;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by mvelas on 6.11.14.
 */
public class RHAccessModifyTicketFragment extends RHAccessFragment {

    public List<WebElement> searchFor (final String query) {
        By queryInputSelector = ByJQuery.selector("div.input-group input");
        waitLongUntilElementIs(queryInputSelector, root).enabled();
        root.findElement(queryInputSelector).sendKeys(query);

        clickButton("Search");

        waitLongUntilElementIs(ByJQuery.selector("table td a"), root).visible();
        return root.findElements(ByJQuery.selector("table td a"));
    }

    public void pickCase(final WebElement anchor) {
        anchor.click();
    }

    public void setStatusTo(final String status) {
        selectValue("status", status);
    }

    protected void selectValue(final String selectName, final String newValue) {
        By statusSelector = ByJQuery.selector("select[name=" + selectName + "]");
        waitLongUntilElementIs(statusSelector, root).visible();
        WebElement selectElement = root.findElement(statusSelector);
        selectElement.click();
        Select statusSelect = new Select(selectElement);
        statusSelect.selectByVisibleText(newValue);
    }

    public void submitUpdate() {
        clickButton("Update Details");
    }
}
