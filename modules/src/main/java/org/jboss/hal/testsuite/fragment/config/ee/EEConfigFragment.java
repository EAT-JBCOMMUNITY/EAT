package org.jboss.hal.testsuite.fragment.config.ee;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.openqa.selenium.By;


/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 11.9.15.
 */
public class EEConfigFragment extends ConfigFragment {

    public Boolean isErrorShownInForm() {
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

}
