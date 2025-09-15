package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddServiceLoaderWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String MODULE = "module";

    public AddServiceLoaderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddServiceLoaderWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }
}
