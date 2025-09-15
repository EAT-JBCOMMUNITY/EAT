package org.jboss.hal.testsuite.fragment.config.jgroups;

import org.jboss.hal.testsuite.fragment.config.ConfigPropertyWizard;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 9.10.15.
 */
public class JGroupsProtocolPropertyWizard extends ConfigPropertyWizard {

    private static final String KEY = "key";


    public ConfigPropertyWizard key(String value) {
        getEditor().text(KEY, value);
        return this;
    }

}
