package org.jboss.hal.testsuite.fragment.config.infinispan;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class CacheContainersFragment extends ConfigFragment {

    public void addCacheContainer(String name) {
        WizardWindow window = getResourceManager().addResource();
        window.getEditor().text("name", name);
        window.finish();
    }
}
