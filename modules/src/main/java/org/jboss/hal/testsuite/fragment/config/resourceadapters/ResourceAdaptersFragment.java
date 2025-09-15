package org.jboss.hal.testsuite.fragment.config.resourceadapters;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class ResourceAdaptersFragment extends ConfigFragment {

    //private static final By CONTENT = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");
    private static final By CONTENT = ByJQuery.selector("." + PropUtils.get("page.content.gwt-layoutpanel") + ":visible");

    public ResourceAdapterWizard addResourceAdapter() {
        return getResourceManager().addResource(ResourceAdapterWizard.class);
    }

    public void removeResourceAdapter(String resourceAdapterName) {
        getResourceManager().removeResourceAndConfirm(resourceAdapterName);
    }

    public void selectResourceAdapter(String nameNoTransaction) {
        getResourceManager().selectByName(nameNoTransaction);
    }
}
