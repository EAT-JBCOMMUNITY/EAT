package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddFilesystemSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddFilesystemSecurityRealmWizard> {

    public AddFilesystemSecurityRealmWizard path(String path) {
        getEditor().text("path", path);
        return this;
    }

}
