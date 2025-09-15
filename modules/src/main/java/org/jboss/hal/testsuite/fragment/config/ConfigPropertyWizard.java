package org.jboss.hal.testsuite.fragment.config;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ConfigPropertyWizard extends WizardWindow {

    private static final String NAME = "key";
    private static final String VALUE = "value";

    public ConfigPropertyWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public ConfigPropertyWizard value(String value) {
        getEditor().text(VALUE, value);
        return this;
    }
}
