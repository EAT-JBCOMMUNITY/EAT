package org.jboss.hal.testsuite.fragment.rhaccess;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by mvelas on 3.11.14.
 */
public class RHAccessSearchFragment extends RHAccessFragment {

    protected final By SEARCH_INPUT = By.id(PropUtils.get("rhaccess.search.input.id"));
    protected final By SEARCH_SUBMIT = ByJQuery.selector(
            "span." + PropUtils.get("rhaccess.search.submit.class") + " button");

    protected final By SEARCH_RESULTS_SELECTOR = ByJQuery.selector(
            "#" + PropUtils.get("rhaccess.search.results.id") + " a");


    public List<WebElement> searchSolutions(final String query) {
        root.findElement(SEARCH_INPUT).sendKeys(query);
        Graphene.waitGui().until().element(root, SEARCH_SUBMIT).is().enabled();
        root.findElement(SEARCH_SUBMIT).click();

        waitLongUntilElementIs(SEARCH_RESULTS_SELECTOR).present();
        return root.findElements(SEARCH_RESULTS_SELECTOR);
    }
}
