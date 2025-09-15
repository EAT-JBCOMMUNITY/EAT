package org.jboss.hal.testsuite.fragment.runtime.configurationchanges;

import org.jboss.hal.testsuite.fragment.WindowFragment;

/**
 * Abstraction for dialog which appears after clicking on 'Enable' button on configuration changes monitoring page
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 9/12/16.
 */
public class EnableConfigurationChangesDialog extends WindowFragment {

    public EnableConfigurationChangesDialog maxHistory(int maxHistory) {
        getEditor().text("max-history", String.valueOf(maxHistory));
        return this;
    }

}
