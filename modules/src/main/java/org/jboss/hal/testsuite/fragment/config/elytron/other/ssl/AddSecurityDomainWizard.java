package org.jboss.hal.testsuite.fragment.config.elytron.other.ssl;

import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindowWithOptionalFields;

public class AddSecurityDomainWizard extends WizardWindowWithOptionalFields {

    private static final String NAME = "name";
    private static final String REALM_NAME = "realm-name";
    private static final String OUTFLOW_ANONYMOUS = "outflow-anonymous";
    private static final String OUTFLOW_SECURITY_DOMAINS = "outflow-security-domains";
    private static final String PERMISSION_MAPPER = "permission-mapper";
    private static final String POST_REALM_PRINCIPAL_TRANSFORMER = "post-realm-principal-transformer";
    private static final String PRE_REALM_PRINCIPAL_TRANSFORMER = "pre-realm-principal-transformer";
    private static final String PRINCIPAL_DECODER = "principal-decoder";
    private static final String REALM_MAPPER = "realm-mapper";
    private static final String ROLE_MAPPER = "role-mapper";
    private static final String SECURITY_EVENT_LISTENER = "security-event-listener";
    private static final String TRUSTED_SECURITY_DOMAINS = "trusted-security-domains";
    private static final String REALM_PRINCIPAL_TRANSFORMER = "realm-principal-transformer";
    private static final String REALM_ROLE_DECODER = "realm-role-decoder";
    private static final String REALM_ROLE_MAPPER = "realm-role-mapper";

    public AddSecurityDomainWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public AddSecurityDomainWizard realmName(String value) {
        getEditor().text(REALM_NAME, value);
        return this;
    }

    public AddSecurityDomainWizard outflowAnonymous(boolean value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().checkbox(OUTFLOW_ANONYMOUS, value);
        return this;
    }

    public AddSecurityDomainWizard outflowSecurityDomains(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(OUTFLOW_SECURITY_DOMAINS, value);
        return this;
    }

    public AddSecurityDomainWizard permissionMapper(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PERMISSION_MAPPER, value);
        return this;
    }

    public AddSecurityDomainWizard postRealmPrincipalTransformer(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(POST_REALM_PRINCIPAL_TRANSFORMER, value);
        return this;
    }

    public AddSecurityDomainWizard preRealmPrincipalTransformer(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PRE_REALM_PRINCIPAL_TRANSFORMER, value);
        return this;
    }

    public AddSecurityDomainWizard principalDecoder(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(PRINCIPAL_DECODER, value);
        return this;
    }

    public AddSecurityDomainWizard realmMapper(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(REALM_MAPPER, value);
        return this;
    }

    public AddSecurityDomainWizard roleMapper(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(ROLE_MAPPER, value);
        return this;
    }

    public AddSecurityDomainWizard securityEventListener(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(SECURITY_EVENT_LISTENER, value);
        return this;
    }

    public AddSecurityDomainWizard trustedSecurityDomains(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(TRUSTED_SECURITY_DOMAINS, value);
        return this;
    }

    public AddSecurityDomainWizard realmPrincipalTransformer(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(REALM_PRINCIPAL_TRANSFORMER, value);
        return this;
    }

    public AddSecurityDomainWizard realmRoleDecoder(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(REALM_ROLE_DECODER, value);
        return this;
    }

    public AddSecurityDomainWizard realmRoleMapper(String value) {
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text(REALM_ROLE_MAPPER, value);
        return this;
    }
}
