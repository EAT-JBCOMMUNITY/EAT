package org.jboss.hal.testsuite.fragment.config.elytron.decoder;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddCustomPrincipalDecoderWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String CLASS_NAME = "class-name";
    private static final String MODULE = "module";

    public AddCustomPrincipalDecoderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddCustomPrincipalDecoderWizard className(String value) {
        getEditor().text(CLASS_NAME, value);
        return this;
    }

    public AddCustomPrincipalDecoderWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }
}
