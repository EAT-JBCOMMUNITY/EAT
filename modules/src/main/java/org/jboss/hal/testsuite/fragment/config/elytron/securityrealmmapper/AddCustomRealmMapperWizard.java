package org.jboss.hal.testsuite.fragment.config.elytron.securityrealmmapper;

public class AddCustomRealmMapperWizard extends AbstractAddRealmMapperWizard<AddCustomRealmMapperWizard> {

    public AddCustomRealmMapperWizard className(String className) {
        getEditor().text("class-name", className);
        return this;
    }

    public AddCustomRealmMapperWizard module(String module) {
        getEditor().text("module", module);
        return this;
    }
}
