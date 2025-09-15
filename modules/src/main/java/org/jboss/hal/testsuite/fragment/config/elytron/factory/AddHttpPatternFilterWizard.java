package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddHttpPatternFilterWizard extends WizardWindowWithOptionalFields {
    private static final String PATTERN_FILTER = "pattern-filter";
    private static final String ENABLING = "enabling";

    public AddHttpPatternFilterWizard patternFilter(String value) {
        getEditor().text(PATTERN_FILTER, value);
        return this;
    }

    public AddHttpPatternFilterWizard enabling(boolean value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().checkbox(ENABLING, value);
        return this;
    }
}
