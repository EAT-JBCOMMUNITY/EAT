package org.jboss.hal.testsuite.page.home;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.fragment.homepage.HomepageModuleFragment;
import org.jboss.hal.testsuite.fragment.homepage.HomepageNeedHelpSectionFragment;
import org.jboss.hal.testsuite.page.BasePage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

/**
 * @author jcechace
 */
@Location("#home")
public class HomePage extends BasePage implements Navigatable {

    private static final String
        MODULE_CLASS = PropUtils.get("homepage.module.class"),
        MODULE_HEADER_CLASS = PropUtils.get("homepage.module.header.class"),
        MODULE_COLUMN_CLASS = PropUtils.get("homepage.module.column.class");

    public void navigate() {
        browser.navigate().refresh();
        Graphene.goTo(HomePage.class);
        Console.withBrowser(browser).waitUntilLoaded().maximizeWindow();
    }

    /**
     * @return null if no matching element is found
     */
    public HomepageModuleFragment getModule(String title) {

        By anyModuleSelector = By.className(MODULE_CLASS);
        By moduleTitleSelector = ByJQuery.selector("." + MODULE_HEADER_CLASS + ":contains('" + title + "'):visible");

        Optional<WebElement> module = getContentRoot().findElements(anyModuleSelector).stream().filter(anyModule -> {
            return !anyModule.findElements(moduleTitleSelector).isEmpty();
        }).findFirst();

        if (module.isPresent()) {
            return Graphene.createPageFragment(HomepageModuleFragment.class, module.get());
        }
        return null;
    }

    public HomepageNeedHelpSectionFragment getNeedHelpSection(String sectionLabel) {
        By select =
                ByJQuery.selector("." + MODULE_CLASS + ":has(." + MODULE_HEADER_CLASS + ":contains('Need Help?'))"
                        + " ." + MODULE_COLUMN_CLASS + ":has(p:contains('" + sectionLabel + "'))");
        WebElement sectionRoot = browser.findElement(select);
        return Graphene.createPageFragment(HomepageNeedHelpSectionFragment.class, sectionRoot);
    }

}
