package org.jboss.hal.testsuite.fragment.config.elytron.decoder;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddAggregatePrincipalDecoderWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String PRINCIPAL_DECODERS = "principal-decoders";

    public AddAggregatePrincipalDecoderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddAggregatePrincipalDecoderWizard  principalDecoders(String value) {
        getEditor().text(PRINCIPAL_DECODERS, value);
        return this;
    }
}
