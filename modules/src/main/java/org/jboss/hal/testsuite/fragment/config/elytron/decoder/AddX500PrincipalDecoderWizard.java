package org.jboss.hal.testsuite.fragment.config.elytron.decoder;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddX500PrincipalDecoderWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String ATTRIBUTE_NAME = "attribute-name";
    private static final String OID = "oid";

    public AddX500PrincipalDecoderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddX500PrincipalDecoderWizard attributeName(String value) {
        getEditor().text(ATTRIBUTE_NAME, value);
        return this;
    }

    public AddX500PrincipalDecoderWizard oid(String value) {
        getEditor().text(OID, value);
        return this;
    }
}
