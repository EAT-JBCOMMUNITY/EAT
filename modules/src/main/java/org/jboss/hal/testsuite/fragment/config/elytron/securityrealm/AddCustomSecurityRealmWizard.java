package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddCustomSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddCustomSecurityRealmWizard> {

    public AddCustomSecurityRealmWizard className(String className) {
        getEditor().text("class-name", className);
        return this;
    }

    public AddCustomSecurityRealmWizard module(String module) {
        getEditor().text("module", module);
        return this;
    }
}
