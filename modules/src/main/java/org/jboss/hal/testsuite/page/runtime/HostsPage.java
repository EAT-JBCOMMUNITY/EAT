package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.finder.Row;
import org.jboss.hal.testsuite.fragment.runtime.HostPropertiesWizard;
import org.jboss.hal.testsuite.page.config.ConfigurationPage;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#hosts")
public class HostsPage extends ConfigurationPage {

    private FinderNavigation navigation;
    private Row defaultHostRow;

    @Override
    public void navigate() {
        navigation = new FinderNavigation(browser, HostsPage.class)
                .step("Browse Domain By", FinderNames.HOSTS)
                .step(FinderNames.HOST, ConfigUtils.getDefaultHost());
        defaultHostRow = navigation.selectRow();
    }

    public void viewServerConfiguration(String serverName) {
        navigation.resetNavigation().step(FinderNames.SERVER, serverName);
        navigation.selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }

    public void viewDefaultHostProperties() {
        defaultHostRow.invoke("Properties");
    }

    public HostPropertiesWizard addProperty() {
        return getResourceManager().addResource(HostPropertiesWizard.class);
    }

    public boolean isRowPresent(String propertyName) {
        return getResourceManager().getResourceTable().getRowByText(0, propertyName) != null;
    }

    public boolean isErrorShown() {
        By selector = ByJQuery.selector("div.form-item-error-desc:visible");
        return isElementVisible(selector);
    }

}
