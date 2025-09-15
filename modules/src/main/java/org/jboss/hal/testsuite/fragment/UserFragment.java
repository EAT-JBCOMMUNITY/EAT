package org.jboss.hal.testsuite.fragment;

import org.jboss.hal.testsuite.util.Console;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class UserFragment extends BaseFragment {

    public UserMenuFragment openMenu() {
        root.click();
        UserMenuFragment popup = Console.withBrowser(browser).openedPopup(UserMenuFragment.class);
        return popup;
    }

}
