package org.jboss.hal.testsuite.fragment.runtime;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 23.10.15.
 */
public class HostPropertiesWizard extends WizardWindow {

    private static final String NAME = "key";
    private static final String VALUE = "value";
    private static final String BOOT_TIME = "bootTime";

    public HostPropertiesWizard name(String name) {
        getEditor().text(NAME, name);
        return this;
    }

    public HostPropertiesWizard value(String value) {
        getEditor().text(VALUE, value);
        return this;
    }

    public HostPropertiesWizard bootTime(boolean value) {
        getEditor().checkbox(BOOT_TIME, value);
        return this;
    }
}
