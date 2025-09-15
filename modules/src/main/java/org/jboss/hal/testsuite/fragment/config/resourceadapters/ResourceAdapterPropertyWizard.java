package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * Class representing a dialog window for creating new resource adapter property
 */
public class ResourceAdapterPropertyWizard  extends WizardWindow {
    private static final String NAME = "name";
    private static final String VALUE = "value";

    /**
     * Sets the name field in the dialog window
     * @param value to be filled into the name field
     */
    public ResourceAdapterPropertyWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    /**
     * Sets the value field in the dialog window
     * @param value to be filled into the value field
     */
    public ResourceAdapterPropertyWizard value(String value) {
        getEditor().text(VALUE, value);
        return this;
    }
}
