package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class JPAPage extends ConfigPage {
    @Override
    public ConfigAreaFragment getConfig() {
        By selector = ByJQuery.selector("table.fill-layout-width:visible:has(table.form-view-panel)");
        WebElement root = getContentRoot().findElement(selector);
        return Graphene.createPageFragment(ConfigAreaFragment.class, root);
    }

    public ConfigFragment getConfigFragment() {
        return Graphene.createPageFragment(ConfigFragment.class, getContentRoot());
    }

}
