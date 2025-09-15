package org.jboss.hal.testsuite.fragment.config.container;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class EJB3BeanPoolsFragment extends ConfigFragment {

    public EJB3BeanPoolWizard addBeanPool() {
        return getResourceManager().addResource(EJB3BeanPoolWizard.class);
    }

    public void removeBeanPool(String name) {
        ConfirmationWindow window = getResourceManager().removeResource(name);
        window.confirmAndDismissReloadRequiredMessage();
    }
}
