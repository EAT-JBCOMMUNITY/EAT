package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.WindowState;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;

/**
 * Created by jcechace on 21/02/14.
 */
public class ConfirmationWindow extends WindowFragment {
    public WindowState confirm() {
        return confirm(false);
    }

    /**
     * Server configuration changed popup message that server requires restart is expected
     * and should be closed by clicking Dismiss button.
     */
    public WindowState confirmAndDismissReloadRequiredMessage() {
        return confirm(true);
    }

    private WindowState confirm(boolean dismissExpectedRealoadWindow) {
        String confirmButtonLabel = PropUtils.get("modals.confirmation.confirm.label");
        clickButton(confirmButtonLabel);

        if (dismissExpectedRealoadWindow) {
            Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        }

        waitUntilClosed();
        closed = true;
        return new WindowState(this);
    }
}
