package org.jboss.hal.testsuite.fragment;

/**
 * Data wrapper for single message in messages popup window.
 *
 * @author jbliznak@redhat.com
 */
public class MessageListEntry {

    private boolean isSuccess;
    private String text;
    private boolean isUnread;

    public MessageListEntry(MessageListEntryFragment f) {
        this.isSuccess = f.isSuccess();
        this.isUnread = f.isUnread();
        this.text = f.getText();
    }

    /**
     * @return whether message is still unread
     */
    public boolean isUnread() {
        return isUnread;
    }

    /**
     * @return displayed message text
     */
    public String getText() {
        return this.text;
    }

    /**
     * @return current message describe successful operation
     */
    public boolean isSuccess() {
        return isSuccess;
    }

    @Override
    public String toString() {
        return "MessageListEntry {" + "isSuccess=" + isSuccess + ", text=" + text + ", isUnread=" + isUnread + '}';
    }
}
