package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddAggregateHttpServerWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String HTTP_SERVER_MECHANISM_FACTORIES = "http-server-mechanism-factories";

    public AddAggregateHttpServerWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddAggregateHttpServerWizard httpServerMechanismFactories(String value) {
        getEditor().text(HTTP_SERVER_MECHANISM_FACTORIES, value);
        return this;
    }


}
