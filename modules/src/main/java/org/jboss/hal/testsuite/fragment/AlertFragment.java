package org.jboss.hal.testsuite.fragment;


/**
 * Area where are messages displayed as popup element in center of page,
 * Area is closed after few seconds automatically usually.
 */
public class AlertFragment extends BaseFragment {
    public static final String ROOT_SELECTOR = "div[role='alert'] div.notification-display";

    public String getMessage() {
        return root.getText().trim();
    }
}
