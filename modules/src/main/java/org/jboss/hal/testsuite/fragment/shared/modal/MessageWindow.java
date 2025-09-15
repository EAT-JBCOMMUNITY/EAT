package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * Window with message details for message opened from notification area.
 *
 * @author Jan Bliznak (jbliznak@redhat.com)
 */
public class MessageWindow extends WindowFragment {

    public static final String CLASS_SUCCESSFUL_FLAG = PropUtils.get("message.window.success.class");
    public static final String ID_MESSAGE_DETAIL = PropUtils.get("message.window.detail.id");

    public String getDateString() {

        //return root.findElement(By.xpath("//h3/../text()")).getText();
        //this is ugly but only WebElement can be found and xpath above would result to text node
        //text of parent element containing title element
        By xpath = By.xpath("//" + PropUtils.get("modals.window.content.title.tag") + "/..");
        String wholeText = root.findElement(xpath).getText();
        int indexOfTitle = wholeText.indexOf(this.getTitle());
        return wholeText.substring(0, indexOfTitle).trim();
    }

    public String getMessageDetail() {
        return root.findElement(By.id(ID_MESSAGE_DETAIL)).getText();
    }

    public boolean isSuccess() {
        return !root.findElements(By.className(CLASS_SUCCESSFUL_FLAG)).isEmpty();
    }
}
