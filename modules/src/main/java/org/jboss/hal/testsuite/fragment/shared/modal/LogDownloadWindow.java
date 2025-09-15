package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.WindowFragment;

import java.util.concurrent.TimeUnit;

/**
 * @author jcechace
 */
public class LogDownloadWindow extends WindowFragment {

    /**
     * For a given times waits until the root element is not present (download is finished)
     *
     * @param time wait time
     * @param unit time unit
     */
    public void waitUntilFinished(long time, TimeUnit unit) {
        Graphene.waitModel().withTimeout(time, unit).until().element(root).is().not().present();
    }

    /**
     * Waits until the root element is not present (download is finished) up to 1 minute.
     */
    public void waitUntilFinished() {
        waitUntilFinished(2, TimeUnit.MINUTES);
    }

}
