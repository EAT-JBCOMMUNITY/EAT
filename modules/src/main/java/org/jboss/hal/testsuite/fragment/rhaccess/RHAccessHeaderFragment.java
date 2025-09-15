package org.jboss.hal.testsuite.fragment.rhaccess;

import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Created by mvelas on 3.11.14.
 */
public class RHAccessHeaderFragment extends BaseFragment {

    public static final String CLASS_ROOT = PropUtils.get("rhaccessarea.class");

    public RHAccessMenuFragment openMenu() {
        root.click();
        Console console = Console.withBrowser(browser);
        RHAccessMenuFragment popup = console.openedPopup(RHAccessMenuFragment.class);
        return popup;
    }
}
