package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddProviderLoaderWizard extends WizardWindow {

    private static final String NAME = "name";
    private static final String ARGUMENT = "argument";
    private static final String CLASS_NAMES = "class-names";
    private static final String CONFIGURATION = "configuration";
    private static final String MODULE = "module";
    private static final String PATH = "path";
    private static final String RELATIVE_TO = "relative-to";

    public AddProviderLoaderWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddProviderLoaderWizard argument(String value) {
        getEditor().text(ARGUMENT, value);
        return this;
    }

    public AddProviderLoaderWizard classNames(String value) {
        getEditor().text(CLASS_NAMES, value);
        return this;
    }

    public AddProviderLoaderWizard configuration(String value) {
        getEditor().text(CONFIGURATION, value);
        return this;
    }

    public AddProviderLoaderWizard module(String value) {
        getEditor().text(MODULE, value);
        return this;
    }

    public AddProviderLoaderWizard path(String value) {
        getEditor().text(PATH, value);
        return this;
    }

    public AddProviderLoaderWizard relativeTo(String value) {
        getEditor().text(RELATIVE_TO, value);
        return this;
    }
}
