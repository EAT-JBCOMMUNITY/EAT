package org.jboss.hal.testsuite.page;

import org.jboss.hal.testsuite.fragment.runtime.ServerContextPicker;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.WebElement;

/**
 * Created by mvelas on 31.3.2014.
 */
public abstract class RuntimePage extends BasePage {

    /**
     * @return pop-up fragment for switching host/server context in domain mode
     */
    private ServerContextPicker openSwitchPopUp() {
        WebElement changeButton = getNavigation().getRoot().findElement(ServerContextPicker.BUTTON_SELECTOR);
        changeButton.click();

        Console console = Console.withBrowser(browser);
        return console.openedPopup(ServerContextPicker.class);
    }

    /**
     * Call only in domain mode!
     */
    public void switchContext(String host, String server) {
        ServerContextPicker switchPopUp = openSwitchPopUp();
        switchPopUp.changeContext(host);
        switchPopUp.changeContext(server);
    }

    /**
     * Call only in domain mode!
     */
    public String getServerContext() {
        ServerContextPicker switchPopUp = openSwitchPopUp();
        return switchPopUp.getCurrentServerName();
    }

    /**
     * Call only in domain mode!
     */
    public String getHostContext() {
        ServerContextPicker switchPopUp = openSwitchPopUp();
        return switchPopUp.getCurrentHostName();
    }
}
