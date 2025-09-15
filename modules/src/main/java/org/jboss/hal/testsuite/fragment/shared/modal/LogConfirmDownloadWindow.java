package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jcechace
 */
public class LogConfirmDownloadWindow extends ConfirmationWindow {
    private static final Logger log = LoggerFactory.getLogger(LogConfirmDownloadWindow.class);


    /**
     * Click the confirm button and return the instance of DowloadWindow
     * @return
     */
    public LogDownloadWindow confirmAndNextWindow() {
        confirm();
        try {
            By selector = By.id(PropUtils.get("logviewer.window.pending.id"));
            Graphene.waitModel().until().element(selector).is().present();
            return Console.withBrowser(browser).openedWindow(LogDownloadWindow.class, selector);
        } catch (TimeoutException e) {
            log.info("No following window is present after click on confirmation button.");
            return null;
        }
    }
}
