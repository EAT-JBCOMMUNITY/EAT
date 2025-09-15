package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author jcechace
 */
public class ConfigAreaFragment extends  BaseFragment {

    public WebElement getTabByLabel(String label) {
        String tabClass = PropUtils.get("configarea.tab.class");
        By selector = ByJQuery.selector("." + tabClass + ":contains('" + label + "')");
        List<WebElement> items = root.findElements(selector);

        for (WebElement item : items) {
            if (item.isDisplayed() && item.getText().equals(label)) {
                return item;
            }
        }

        throw new NoSuchElementException(selector.toString());
    }

    public void clickTabByLabel(String label) {
        WebElement item = getTabByLabel(label);
        item.click();
        String selectedTabClass = PropUtils.get("configarea.tab.selected.class");
        Graphene.waitGui().until().element(item).attribute("class").contains(selectedTabClass);
    }

    public <T extends ConfigFragment> T switchTo(String label, Class<T> clazz) {
        clickTabByLabel(label);
        By selector = By.className(PropUtils.get("configarea.content.class"));
        WebElement contentRoot = root.findElement(selector);
        T content = Graphene.createPageFragment(clazz, contentRoot);

        return content;
    }

    public ConfigFragment switchTo(String label) {
        return switchTo(label, ConfigFragment.class);
    }

    public ResourceManager getResourceManager() {
        return Graphene.createPageFragment(ResourceManager.class, root);
    }

}
