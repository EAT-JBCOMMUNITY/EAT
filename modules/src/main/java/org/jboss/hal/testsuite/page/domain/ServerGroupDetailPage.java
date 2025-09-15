package org.jboss.hal.testsuite.page.domain;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Represents the page with server group details (Attributes, JVM Configuration and System Properties).
 *
 * @author msmerek
 */
public class ServerGroupDetailPage extends ConfigPage {

    public void switchToSystemProperties() {
        getConfig().switchTo("System Properties");
    }

    public void switchToJVMConfiguration() {
        getConfig().switchTo("JVM Configuration");
    }

    public boolean isButtonVisible(String buttonLabel) {
        List<WebElement> elements = getContentRoot().findElements(ByJQuery.selector("button:contains(" + buttonLabel + "):visible"));

        return !elements.isEmpty();
    }

}
