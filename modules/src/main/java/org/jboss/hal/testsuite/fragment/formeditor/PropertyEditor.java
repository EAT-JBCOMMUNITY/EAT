package org.jboss.hal.testsuite.fragment.formeditor;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author jcechace
 */
public class PropertyEditor extends BaseFragment {
    private ResourceTableFragment table;

    public void add(String key, String value) {
        ResourceTableRowFragment row = clickAddButton();

        setValue(row, value);
        setKey(row, key);
    }

    private ResourceTableRowFragment clickAddButton() {
        String cssClass = PropUtils.get("editor.shared.property.add.id");
        By selector = ByJQuery.selector("button[id$='" + cssClass + "']");
        WebElement button = root.findElement(selector);

        button.click();
        waitForNewLine();

        ResourceTableRowFragment row = getTable().getLastRow();

        return row;
    }

    private void waitForNewLine() {
        String emptyNameLabel = PropUtils.get("editor.shared.property.empty.key.label");


        String emptyValueLabel = PropUtils.get("editor.shared.property.empty.value.label");
        By selector = ByJQuery.selector("tr:contains('" + emptyNameLabel  + "')" +
                ":contains('" + emptyValueLabel  + "')");

        Graphene.waitGui().until().element(getTable().getRoot(), selector);
    }


     private void setKey(ResourceTableRowFragment row,  String text) {
         setTextValue(row, 0, text);
     }


    private void setValue(ResourceTableRowFragment row,  String text) {
        setTextValue(row, 1, text);
    }

    private void setTextValue(ResourceTableRowFragment row,  int index, String text) {
        WebElement cell = row.getCell(index);
        cell.click();

        By selector = ByJQuery.selector("input");

        Graphene.waitModel().until().element(cell, selector).is().present();

        WebElement input = cell.findElement(selector);

        input.clear();

        input.sendKeys(text);

        Graphene.waitGui().until().element(input).attribute("value").contains(text);
    }

    private ResourceTableFragment getTable() {
        if (table != null) {
            return table;
        }
        // else initialize table and return it
        table = Console.withBrowser(browser)
                .getTableByHeader("Key", ResourceTableFragment.class, root);

        return table;
    }

}

