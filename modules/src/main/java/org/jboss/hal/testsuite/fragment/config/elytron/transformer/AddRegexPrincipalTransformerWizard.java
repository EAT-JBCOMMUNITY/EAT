package org.jboss.hal.testsuite.fragment.config.elytron.transformer;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddRegexPrincipalTransformerWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String PATTERN = "pattern";
    private static final String REPLACEMENT = "replacement";

    public AddRegexPrincipalTransformerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddRegexPrincipalTransformerWizard pattern(String value) {
        getEditor().text(PATTERN, value);
        return this;
    }

    public AddRegexPrincipalTransformerWizard replacement(String value) {
        getEditor().text(REPLACEMENT, value);
        return this;
    }

}
