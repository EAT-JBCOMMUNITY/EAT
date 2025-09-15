package org.jboss.hal.testsuite.fragment.config.security;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 16.10.15.
 */
public class SecurityDomainAddWizard extends WizardWindow {

    public SecurityDomainAddWizard name(String name) {
        getEditor().text("name", name);
        return this;
    }

    public SecurityDomainAddWizard cacheType(String cache) {
        getEditor().select("cache-type", cache);
        return this;
    }
}
