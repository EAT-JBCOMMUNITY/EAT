package org.jboss.hal.testsuite.fragment.config.interfaces;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class NetworkInterfaceWizard extends WizardWindow {

    public static final String NAME = "name";
    public static final String INET_ADDRESS = "inetAddress";
    public static final String ADDRESS_WILDCARD = "addressWildcard";

    public NetworkInterfaceWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public NetworkInterfaceWizard addressWildcard(String value) {
        getEditor().select(ADDRESS_WILDCARD, value);
        return this;
    }

    public NetworkInterfaceWizard inetAddress(String value) {
        getEditor().text(INET_ADDRESS, value);
        return this;
    }
}
