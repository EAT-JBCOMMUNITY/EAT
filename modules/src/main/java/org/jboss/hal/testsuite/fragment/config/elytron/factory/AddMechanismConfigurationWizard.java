package org.jboss.hal.testsuite.fragment.config.elytron.factory;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

public class AddMechanismConfigurationWizard extends WizardWindow {
    private static final String MECHANISM_NAME = "mechanism-name";
    private static final String HOST_NAME = "host-name";
    private static final String PROTOCOL = "protocol";
    private static final String PRE_REALM_PRINCIPAL_TRANSFORMER = "pre-realm-principal-transformer";
    private static final String POST_REALM_PRINCILAP_TRANSFORMER = "post-realm-principal-transformer";
    private static final String FINAL_PRINCIPAL_TRANSFORMER = "final-principal-transformer";
    private static final String REALM_MAPPER = "realm-mapper";
    private static final String CREDENTIAL_SECURITY_FACTORY = "credential-security-factory";

    public AddMechanismConfigurationWizard mechanismName(String value) {
        getEditor().text(MECHANISM_NAME, value);
        return this;
    }

    public AddMechanismConfigurationWizard hostName(String value) {
        getEditor().text(HOST_NAME, value);
        return this;
    }

    public AddMechanismConfigurationWizard protocol(String value) {
        getEditor().text(PROTOCOL, value);
        return this;
    }

    public AddMechanismConfigurationWizard preRealmPrincipalTransformer(String value) {
        getEditor().text(PRE_REALM_PRINCIPAL_TRANSFORMER, value);
        return this;
    }

    public AddMechanismConfigurationWizard postRealmPrincipalTransformer(String value) {
        getEditor().text(POST_REALM_PRINCILAP_TRANSFORMER, value);
        return this;
    }

    public AddMechanismConfigurationWizard finalPrincipalTransformer (String value) {
        getEditor().text(FINAL_PRINCIPAL_TRANSFORMER, value);
        return this;
    }

    public AddMechanismConfigurationWizard realmMapper(String value) {
        getEditor().text(REALM_MAPPER, value);
        return this;
    }

    public AddMechanismConfigurationWizard credentialSecurityFactory(String value) {
        getEditor().text(CREDENTIAL_SECURITY_FACTORY, value);
        return this;
    }
}
