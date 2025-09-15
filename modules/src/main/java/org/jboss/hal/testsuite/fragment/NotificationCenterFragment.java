package org.jboss.hal.testsuite.fragment;

import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Represents notification area with messages.
 * @author jbliznak@redhat.com
 */
public class NotificationCenterFragment extends BaseFragment {

    public static final String CLASS_ROOT = PropUtils.get("notificationarea.class");

    /**
     * @return visible text of this element
     */
    public String getText() {
        return root.getText();
    }

    /**
     * @return count of messages displayed in this element
     */
    public int getMessagesCount() {
        String[] split = getText().split(" ");
        return Integer.parseInt(split[split.length - 1]);
    }

    /**
     * Open popup window with messages by clicking on this element
     * @return opened popup window with messages
     */
    public MessagesListFragment openMessagesList() {
        root.click();
        Console console = Console.withBrowser(browser);
        MessagesListFragment popup = console.openedPopup(MessagesListFragment.class);
        return popup;
    }
}
