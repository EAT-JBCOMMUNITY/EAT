package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * Abstraction over page for hosts configuration in Undertow subsystem
 */
public class UndertowHostPage extends UndertowHTTPPage {

    public ConfigFragment switchToFilterReferencesSubTab() {
        return getConfig().switchTo("Reference to Filter");
    }

    /**
     * Adds new reference to filter in UI for selected host if current view is reference filter sub tab
     * @param filterName name of filter which will be referenced
     */
    public void addReferenceToFilter(String filterName) {
        WizardWindow wizardWindow = getConfigFragment().getResourceManager().addResource();
        wizardWindow.getEditor().text("name", filterName);
        wizardWindow.finish();
    }
}
