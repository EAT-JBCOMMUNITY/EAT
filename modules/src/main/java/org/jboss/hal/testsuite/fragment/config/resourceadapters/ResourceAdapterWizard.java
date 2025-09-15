package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ResourceAdapterWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String ARCHIVE = "archive";
    private static final String MODULE = "module";
    private static final String TX = "transaction-support";

    public ResourceAdapterWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public ResourceAdapterWizard archive(String value) {
        getEditor().text(ARCHIVE, value);
        return this;
    }

    public ResourceAdapterWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }

    public ResourceAdapterWizard tx(String value) {
        getEditor().select(TX, value);
        return this;
    }
}
