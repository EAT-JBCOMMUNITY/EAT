package org.jboss.hal.testsuite.fragment;

import org.jboss.hal.testsuite.fragment.shared.modal.MessageWindow;
import java.util.ArrayList;
import java.util.List;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Represents messages popup window which is opened by clicking on notification center area.
 * Every method here works only when this popup window is open/visible, it is responsibility of
 * user.
 * <p/>
 * @author jbliznak@redhat.com
 */
public class MessagesListFragment extends PopUpFragment {

    public static final String CLASS_MSG_DIV = PropUtils.get("messagelist.list.class");
    public static final String CLEAR_BUTTON_LABEL = PropUtils.get("messagelist.clear.label");

    /**
     * @return total count of displayed messages
     */
    public int getCount() {
        return getMessageElements().size();
    }

    /**
     * @return messages entries
     */
    public List<MessageListEntryFragment> getMessages() {
        List<MessageListEntryFragment> msgs = new ArrayList<MessageListEntryFragment>();
        for (WebElement table : getMessageElements()) {
            msgs.add(Graphene.createPageFragment(MessageListEntryFragment.class, table));
        }
        return msgs;
    }

    /**
     * @return unread messages entries
     */
    public List<MessageListEntryFragment> getUnreadMessages() {
        List<MessageListEntryFragment> msgs = new ArrayList<MessageListEntryFragment>();

        for (MessageListEntryFragment m : getMessages()) {
            if (m.isUnread()) {
                msgs.add(m);
            }
        }

        return msgs;
    }

    /**
     * @return whether there are any messages in the list
     */
    public boolean hasMessages() {
        return !getMessageElements().isEmpty();
    }

    /**
     * @return whether there are any unread messages in the list
     */
    public boolean hasUnreadMessages() {
        return !getUnreadMessages().isEmpty();
    }

    /**
     * Clear popup list by clicking on Clear button.
     * Note: this closes the message list making references to this fragment invalid.
     */
    public void clear() {
        clickLinkByLabel(CLEAR_BUTTON_LABEL);
    }

    /**
     * Open specified message.
     * Note: this closes the message list making references to this fragment invalid.
     *
     * @param idx zerobased index of message in message list (most recent equals to 0)
     * @return window with message details
     */
    public MessageWindow openMessage(int idx) {
        getMessageElements().get(idx).click();
        Console console = Console.withBrowser(this.browser);
        return (MessageWindow) console.openedWindow(MessageWindow.class);
    }

    private List<WebElement> getMessageElements() {
        return root
                .findElement(By.className(CLASS_MSG_DIV))
                .findElements(ByJQuery.selector("div:first div table"));
    }

    /**
     * @return messages as POJOs
     */
    public List<MessageListEntry> getMessagesAsData() {
        List<MessageListEntryFragment> msgs = this.getMessages();
        List<MessageListEntry> list = new ArrayList<MessageListEntry>();

        for (MessageListEntryFragment m : msgs) {
            list.add(new MessageListEntry(m));
        }
        return list;
    }
}
