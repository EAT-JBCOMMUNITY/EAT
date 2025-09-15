package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddPropertiesSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddPropertiesSecurityRealmWizard> {

    public AddPropertiesSecurityRealmWizard usersPropertiesPath(String usersPropertiesPath) {
        getEditor().text("users-properties-path", usersPropertiesPath);
        return this;
    }

    public AddPropertiesSecurityRealmWizard relativeTo(String relativeTo) {
        maximizeWindow();
        openOptionalFieldsTabIfNotAlreadyOpened();
        getEditor().text("relative-to", relativeTo);
        return this;
    }
}
