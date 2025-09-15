package org.jboss.hal.testsuite.util;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

/**
 *  This class is pure EVIL and as such any method which uses it is in need of refactoring.
 *
 * @author jcechace
 */
public class Workaround {
    private WebDriver browser;

    public static Workaround withBrowser(WebDriver browser) {
        Workaround workaround = new Workaround();
        workaround.browser = browser;

        return workaround;
    }


    /**
     * @deprecated
     * <br /> TODO: fix problem with window not being opened after link is clicked
     */
    public void clickLinkUntilWindowIsOpened(WebElement link, By selector) {
        boolean done = false;
        int attempts = 10;

        do {
            attempts = attempts - 1;
            try {
                link.click();
                Graphene.waitGui().until().element(selector).is().present();
                done = true;
            } catch (TimeoutException e) {
                if (attempts == 0) {
                    throw e;
                }
            }
        } while (done == false && attempts > 0);
    }

    // TODO: using flat wait due to IOOBE in fluent waiting API
    public void waitUntilWindowIsClosed(int windowCount, boolean fail) {
        Graphene.waitGui().withTimeout(1, TimeUnit.SECONDS);
        int newWindowCount = Console.withBrowser(browser).getWindowCount();
        // no window was closed and method is supposed to fail
        if (fail && newWindowCount >=  windowCount) {
            throw new TimeoutException();
        }
    }
}
