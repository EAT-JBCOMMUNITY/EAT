package org.jboss.hal.testsuite.fragment.config.elytron.decoder;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddConcatenatingPrincipalDecoderWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String PRINCIPAL_DECODERS = "principal-decoders";

    public AddConcatenatingPrincipalDecoderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddConcatenatingPrincipalDecoderWizard  principalDecoders(String value) {
        getEditor().text(PRINCIPAL_DECODERS, value);
        return this;
    }
}
