package org.jboss.hal.testsuite.fragment.config.elytron.other;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class JACCPolicyWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String POLICY = "policy";
    private static final String CONFIGURATION_FACTORY = "configuration-factory";
    private static final String MODULE = "module";

    public JACCPolicyWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public JACCPolicyWizard policy(String value) {
        getEditor().text(POLICY, value);
        return this;
    }

    public JACCPolicyWizard configurationFactory(String value) {
        getEditor().text(CONFIGURATION_FACTORY, value);
        return this;
    }

    public JACCPolicyWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }


}
