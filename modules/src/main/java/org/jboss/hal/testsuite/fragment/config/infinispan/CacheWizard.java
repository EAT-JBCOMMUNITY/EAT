package org.jboss.hal.testsuite.fragment.config.infinispan;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class CacheWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String MODE = "mode";

    public CacheWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public CacheWizard mode(String value) {
        getEditor().select(MODE, value);
        return this;
    }

}
