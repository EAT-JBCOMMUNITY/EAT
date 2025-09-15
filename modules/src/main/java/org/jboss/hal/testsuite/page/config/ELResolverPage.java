package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.page.home.HomePage;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by pcyprian on 26.10.15.
 */
public class ELResolverPage extends HomePage {

    public void openExpressionResolver() {
        WebElement tools = browser.findElement(ByJQuery.selector("div.gwt-HTML.footer-link:contains(Tools)"));
        tools.click();
        browser.findElement(By.xpath("//div[@class='popupContent']//a[contains(text(), 'Expression Resolver')]")).click();
    }

    public String resolveSystemProperty(String name) {
        WindowFragment configFragment = Console.withBrowser(browser).openedWindow();
        configFragment.getEditor().text("input", "${" + name + ":default_value}");
        configFragment.clickButton("Resolve");

        return configFragment.getEditor().text("output");
    }
}
