package org.jboss.hal.testsuite.fragment.config.elytron.securityrealmmapper;

public class AddConstantRealmMapperWizard extends AbstractAddRealmMapperWizard<AddConstantRealmMapperWizard> {

    public AddConstantRealmMapperWizard realm(String realm) {
        getEditor().text("realm-name", realm);
        return this;
    }

}
