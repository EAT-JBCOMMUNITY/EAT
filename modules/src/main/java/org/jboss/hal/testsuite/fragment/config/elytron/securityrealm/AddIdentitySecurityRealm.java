package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddIdentitySecurityRealm extends AbstractAddSecurityRealmWizard<AddIdentitySecurityRealm> {

    public AddIdentitySecurityRealm identity(String identity) {
        getEditor().text("identity", identity);
        return this;
    }

}
