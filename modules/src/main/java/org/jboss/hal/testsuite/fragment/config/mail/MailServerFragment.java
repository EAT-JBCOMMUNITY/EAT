package org.jboss.hal.testsuite.fragment.config.mail;

import org.jboss.hal.testsuite.fragment.ConfigFragment;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class MailServerFragment extends ConfigFragment {

    public void removeAndConfirm(String name) {
        getResourceManager().removeResourceAndConfirm(name.toUpperCase());
    }

    public MailServerWizard addMailServer() {
        return getResourceManager().addResource(MailServerWizard.class);
    }

    public void selectServer(String name) {
        getResourceManager().selectByName(name.toUpperCase());
    }
}
