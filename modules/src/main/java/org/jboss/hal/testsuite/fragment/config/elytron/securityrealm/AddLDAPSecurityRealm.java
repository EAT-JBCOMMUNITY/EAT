package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddLDAPSecurityRealm extends AbstractAddSecurityRealmWizard<AddLDAPSecurityRealm> {

    public AddLDAPSecurityRealm dirContext(String dirContext) {
        getEditor().text("dir-context", dirContext);
        return this;
    }

    public AddLDAPSecurityRealm identityMappingRdnIdentifier(String rdnIdentifier) {
        getEditor().text("identity-mapping-rdn-identifier", rdnIdentifier);
        return this;
    }

}
