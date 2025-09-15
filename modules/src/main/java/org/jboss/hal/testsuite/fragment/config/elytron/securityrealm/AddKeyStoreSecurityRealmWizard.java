package org.jboss.hal.testsuite.fragment.config.elytron.securityrealm;

public class AddKeyStoreSecurityRealmWizard extends AbstractAddSecurityRealmWizard<AddKeyStoreSecurityRealmWizard> {

    public AddKeyStoreSecurityRealmWizard keyStore(String keyStore) {
        getEditor().text("key-store", keyStore);
        return this;
    }

}
