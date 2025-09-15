package org.jboss.hal.testsuite.page.config.elytron;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jboss.hal.testsuite.finder.FinderNames.SETTINGS;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.ELYTRON_SUBSYTEM_LABEL;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.FACTORY_TRANSFORMER;

public class TransformerPage extends AbstractElytronConfigPage<TransformerPage> {

    @Override
    public TransformerPage navigateToApplication() {
        getSubsystemNavigation(ELYTRON_SUBSYTEM_LABEL).step(SETTINGS, FACTORY_TRANSFORMER).openApplication(30);
        switchTab("Transformer");
        return this;
    }

    /**
     * Select particular Elytron resource in the list on the left side of the application detail with exact label text
     * match.
     */
    public TransformerPage selectResourceWithExactLabelMatch(String resourceLabel) {
        By selector = ByJQuery.selector("div.inline-link:contains('" + resourceLabel + "'):visible");
        Graphene.waitGui().until().element(selector).is().visible();
        WebElement resourceElement = getElementWithExactTextMatch(browser.findElements(selector), resourceLabel);
        resourceElement.click();
        Graphene.waitModel().until().element(resourceElement).attribute("class").contains("link-bar-active");
        Console.withBrowser(browser).waitUntilLoaded();
        return this;
    }

    /*
     * Implementation note - not possible to use Stream API yet since current version of Graphene throws
     * org.jboss.arquillian.graphene.enricher.exception.PageObjectInitializationException when you try it.
     */
    private WebElement getElementWithExactTextMatch(List<WebElement> elementList, String textToBeMatched) {
        WebElement foundElement = null;
        for (WebElement element : elementList) {
            if (element.getText().equals(textToBeMatched)) {
                foundElement = element;
                break;
            }
        }
        if (foundElement == null) {
            throw new NoSuchElementException("Missing element with text exactly matching '" + textToBeMatched + "'.");
        }
        return foundElement;
    }

}
