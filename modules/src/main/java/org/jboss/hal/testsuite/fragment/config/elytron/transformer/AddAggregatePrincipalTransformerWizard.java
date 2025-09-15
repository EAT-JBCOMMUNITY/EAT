package org.jboss.hal.testsuite.fragment.config.elytron.transformer;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddAggregatePrincipalTransformerWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String PRINCIPAL_TRANSFORMERS = "principal-transformers";

    public AddAggregatePrincipalTransformerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddAggregatePrincipalTransformerWizard principalTransformers(String value) {
        getEditor().text(PRINCIPAL_TRANSFORMERS, value);
        return this;
    }
}
