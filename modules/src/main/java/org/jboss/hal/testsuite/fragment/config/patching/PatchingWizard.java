package org.jboss.hal.testsuite.fragment.config.patching;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.util.PropUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rhatlapa (rhatlapa@redhat.com)
 */
public class PatchingWizard extends WizardWindow {

    private static final Logger log = LoggerFactory.getLogger(PatchingWizard.class);


    /**
     * select radio button whether servers on active host should be restarted
     * @param shutdown (whether servers are supposed to be stopped not
     */
    public void shutdownServer(boolean shutdown) {
        String identifier = PropUtils.get("runtime.patching.stopservers.radio");
        if (shutdown) {
            getEditor().radioButton(identifier, 0);
        } else {
            getEditor().radioButton(identifier, 1);
        }
    }

    /**
     * select radio button whether server should or shouldn't be restarted
     * @param restart (whether server is supposed to be restarted or not
     */
    public void restartSever(boolean restart) {
        String identifier = PropUtils.get("runtime.patching.restarthost.radio");
        if (restart) {
            getEditor().radioButton(identifier, 0);
        } else {
            getEditor().radioButton(identifier, 1);
        }
    }

    /**
     * @return abstraction for patching wizard result, null if no result visible at current wizard
     *         state
     */
    public PatchResultPanelFragment getResultPanel() {
        if (hasResultPanel()) {
            log.debug("Panel visible, creating result fragment");

            return Graphene.createPageFragment(PatchResultPanelFragment.class,
                                               root.findElement(PatchResultPanelFragment.SELECTOR));
        } else {
            log.debug("No panel visible at this state");
            return null;
        }
    }

    /**
     * @return whether wizard at current step has visible panel with result message
     */
    public boolean hasResultPanel() {
        return !root.findElements(PatchResultPanelFragment.SELECTOR).isEmpty();
    }

    /**
     *
     * @return false when no result visible at current wizard state, otherwise whether the result
     *         message state success
     */
    public boolean isSuccess() {
        PatchResultPanelFragment p = getResultPanel();

        return p != null && p.isSuccessful();
    }

    /**
     *
     * @return null when no result visible at current wizard state, otherwise result message
     */
    public String getResultMessage() {
        PatchResultPanelFragment p = getResultPanel();

        if (p == null) {
            return null;
        } else {
            return p.isSuccessful() ? p.getMessage() : p.getErrorMessage() + "\nError details: " + p.getErrorDetails();
        }
    }
}
