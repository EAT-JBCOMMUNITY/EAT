package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddRealmWizard extends WizardWindowWithOptionalFields {

    private static final String REALM = "realm";
    private static final String PRINCIPAL_TRANSFORMER = "principal-transformer";
    private static final String ROLE_DECODER = "role-decoder";
    private static final String ROLE_MAPPER = "role-mapper";

    public AddRealmWizard realm(String value) {
        getEditor().text(REALM, value);
        return this;
    }

    public AddRealmWizard principalTransformer(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PRINCIPAL_TRANSFORMER, value);
        return this;
    }

    public AddRealmWizard roleDecoder(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ROLE_DECODER, value);
        return this;
    }

    public AddRealmWizard roleMapper(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ROLE_MAPPER, value);
        return this;
    }
}
