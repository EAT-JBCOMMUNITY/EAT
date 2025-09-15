package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddSaslPatternFilterWizard extends WizardWindowWithOptionalFields {
    private static final String PREDEFINED_FILTER = "predefined-filter";
    private static final String PATTERN_FILTER = "pattern-filter";
    private static final String ENABLING = "enabling";

    public AddSaslPatternFilterWizard predefinedFilter(String value) {
        getEditor().select(PREDEFINED_FILTER, value);
        return this;
    }

    public AddSaslPatternFilterWizard patternFilter(String value) {
        getEditor().text(PATTERN_FILTER, value);
        return this;
    }

    public AddSaslPatternFilterWizard enabling(boolean value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().checkbox(ENABLING, value);
        return this;
    }

}
