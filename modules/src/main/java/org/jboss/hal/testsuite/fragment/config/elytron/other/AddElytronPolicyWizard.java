package org.jboss.hal.testsuite.fragment.config.elytron.other;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;

public class AddElytronPolicyWizard extends WizardWindow {
    private static final String POLICY = "policy";
    private static final String NEXT_LABEL = PropUtils.get("modals.wizard.next.label");

    public JACCPolicyWizard jaccPolicy() {
        getEditor().radioButton(POLICY, 1);
        clickButton(NEXT_LABEL);
        return Console.withBrowser(browser).openedWizard(JACCPolicyWizard.class);
    }

    public CustomPolicyWizard customPolicy() {
        getEditor().radioButton(POLICY, 0);
        clickButton(NEXT_LABEL);
        return Console.withBrowser(browser).openedWizard(CustomPolicyWizard.class);
    }
}
