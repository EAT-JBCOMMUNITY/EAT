package org.jboss.hal.testsuite.fragment.shared;


import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.openqa.selenium.WebElement;

public class FormItemTableFragment extends BaseFragment {

    public String getValueOf(String label) {
        return getRowByTitle(label).findElement(ByJQuery.selector(".form-item-value")).getText();
    }

    private WebElement getRowByTitle(String title) {
        return root.findElement(ByJQuery.selector("tr.form-attribute-row:contains('" + title + "')"));
    }
}
