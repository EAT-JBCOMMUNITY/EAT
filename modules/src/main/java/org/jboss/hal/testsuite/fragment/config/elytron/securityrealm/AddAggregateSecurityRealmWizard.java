package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddAggregateSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddAggregateSecurityRealmWizard> {

    public AddAggregateSecurityRealmWizard authenticationRealm(String authenticationRealm) {
        getEditor().text("authentication-realm", authenticationRealm);
        return this;
    }

    public AddAggregateSecurityRealmWizard authorizationRealm(String authorizationRealm) {
        getEditor().text("authorization-realm", authorizationRealm);
        return this;
    }
}
