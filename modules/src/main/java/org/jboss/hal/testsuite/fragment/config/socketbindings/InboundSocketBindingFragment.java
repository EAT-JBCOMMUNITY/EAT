package org.jboss.hal.testsuite.fragment.config.socketbindings;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.page.config.SocketBindingsPage;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class InboundSocketBindingFragment extends ConfigFragment {
    public InboundSocketBindingWizard addSocketBinding() {
        return getResourceManager().addResource(InboundSocketBindingWizard.class);
    }

    /**
     * You have to use {@link SocketBindingsPage#getResourceManager()} to obtain resource manager and then add it
     * since socket binding table is 'page table'
     */
    @Deprecated
    public void selectSocketBinding(String name) {
        getResourceManager().selectByName(name);
    }

    /**
     * You have to use {@link SocketBindingsPage#getResourceManager()} to obtain resource manager and then remove it
     * since socket binding table is 'page table'
     */
    @Deprecated
    public void removeSocketBinding(String name) {
        getResourceManager().removeResource(name).confirmAndDismissReloadRequiredMessage();
    }
}
