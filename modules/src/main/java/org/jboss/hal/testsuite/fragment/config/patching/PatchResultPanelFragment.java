package org.jboss.hal.testsuite.fragment.config.patching;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents panel with error message in patch wizard.
 * @author jbliznak@redhat.com
 */
public class PatchResultPanelFragment extends BaseFragment {

    private static final Logger log = LoggerFactory.getLogger(PatchResultPanelFragment.class);
    /**
     * Selector for this fragment from WizardFragment root
     */
    public static final By SELECTOR =
            ByJQuery.selector("." + PropUtils.get("runtime.patching.wizard.result.fragment.class") + ":visible");
    private static final By ERROR_DETAILS_SELECTOR =
            By.className(PropUtils.get("runtime.patching.wizard.result.error.details.class"));
    private static final By ERROR_DETAILS_DISPLAYED_SELECTOR =
            ByJQuery.selector("." + PropUtils.get("runtime.patching.wizard.result.error.details.class")
                              + "." + PropUtils.get("runtime.patching.wizard.result.error.details.opened.class"));
    private static final By ERROR_DETAILS_HEADER_SELECTOR =
            By.className(PropUtils.get("runtime.patching.wizard.result.error.hideshowdetails.class"));
    private static final By ERROR_MESSAGE_SELECTOR =
            By.className(PropUtils.get("runtime.patching.wizard.result.error.message.class"));
    private static final By SUCCESS_MESSAGE_SELECTOR =
            By.className(PropUtils.get("runtime.patching.wizard.result.success.message.class"));

    /**
     * Decides whether the result of patching process is successful by checking the presence of
     * error message.
     *
     * @return true if the result is successful
     */
    public boolean isSuccessful() {
        try {
            Graphene.waitGui().until().element(ERROR_MESSAGE_SELECTOR).is().visible();
            return false;
        } catch (TimeoutException e) {
            return true;
        }
    }

    /**
     * Show error details
     */
    public void showErrorDetails() {
        if (!isErrorDetailsOpened()) {
            log.debug("Opening error details");
            root.findElement(ERROR_DETAILS_HEADER_SELECTOR).click();
        }
    }

    /**
     * Hide error details
     */
    public void hideErrorDetails() {
        if (isErrorDetailsOpened()) {
            log.debug("Closing error details");
            root.findElement(ERROR_DETAILS_HEADER_SELECTOR).click();
        }
    }

    /**
     * @return whether error details are hidden
     */
    public boolean isErrorDetailsOpened() {
        return !root.findElements(ERROR_DETAILS_DISPLAYED_SELECTOR).isEmpty();
    }

    /**
     * @return Error panel title
     */
    public String getPanelTitle() {
        return root.findElement(By.tagName("h3")).getText();
    }

    /**
     * @return visible error message
     */
    public String getErrorMessage() {
        return root.findElement(ERROR_MESSAGE_SELECTOR).getText();
    }

    /**
     * @return visible result (success) message
     */
    public String getMessage() {
        Graphene.waitModel().until().element(SUCCESS_MESSAGE_SELECTOR).is().visible();

        return root.findElement(SUCCESS_MESSAGE_SELECTOR).getText();
    }

    /**
     * Get error details content. Details panel will be (and stay) opened if not already open.
     * @return error details content as plain text (what you see)
     */
    public String getErrorDetails() {
        showErrorDetails();
        return root.findElement(ERROR_DETAILS_SELECTOR)
                .findElement(By.tagName("pre")).getText();
    }
}
