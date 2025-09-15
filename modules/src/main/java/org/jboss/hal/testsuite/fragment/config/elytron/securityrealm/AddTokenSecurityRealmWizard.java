package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddTokenSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddTokenSecurityRealmWizard> {

    public AddTokenSecurityRealmWizard principalClaim(String principalClaim) {
        getEditor().text("principal-claim", principalClaim);
        return this;
    }
}
