package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.shared.FormItemTableFragment;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.treefinder.TreeNavigation;

/**
 * Page which contains tree navigation in left column and form table with values in right column
 */
public class TreeNavigationPage extends ConfigPage {

    public TreeNavigation treeNavigation() {
        return Graphene.createPageFragment(TreeNavigation.class, getContentRoot());
    }

    public FormItemTableFragment formItemTable() {
        return Graphene.createPageFragment(FormItemTableFragment.class,
                getContentRoot().findElement(ByJQuery.selector("table.form-item-table")));
    }

    /**
     * Clicks refresh button above the navigation tree
     */
    public void refreshTreeNavigation() {
        getContentRoot().findElement(ByJQuery.selector("div.split-west button[title=\"Refresh Model\"]")).click();
    }

}
