package org.jboss.hal.testsuite.fragment.config.elytron.transformer;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddRegexValidatingPrincipalTransformerWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String PATTERN = "pattern";

    public AddRegexValidatingPrincipalTransformerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddRegexValidatingPrincipalTransformerWizard pattern(String value) {
        getEditor().text(PATTERN, value);
        return this;
    }

}
