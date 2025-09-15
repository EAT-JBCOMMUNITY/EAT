package org.jboss.hal.testsuite.fragment.shared.table;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author jcechace
 *
 * This class represents a simple information table -- used for example on overview page to display
 * information about EAP distribution in use
 */
public class InfoTable extends BaseFragment {
    private static String KEY_CLASS = PropUtils.get("infotable.key.class");

    /**
     * Returns a value (second column) based on key (first column)
     * @param key a key to search for
     * @return value for given key
     */
    public String get(String key) {
        By keySelector = ByJQuery.selector("." + KEY_CLASS + ":contains('" + key + "')");
        WebElement keyCell = root.findElement(keySelector);

        WebElement valCell = keyCell.findElement(By.xpath("./following::td"));

        return valCell.getText();
    }
}
