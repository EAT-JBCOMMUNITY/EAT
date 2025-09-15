package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.config.interfaces.NetworkInterfaceContentFragment;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#profile/interfaces")
public class NetworkInterfacesPage extends ConfigPage implements Navigatable {

    private static final By CONTENT = By.id(PropUtils.get("page.content.area.id"));

    public NetworkInterfaceContentFragment getContent() {
        return Graphene.createPageFragment(NetworkInterfaceContentFragment.class, getContentRoot().findElement(CONTENT));
    }

    @Override
    public void navigate() {
        new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, "Interfaces")
                .selectRow().invoke(FinderNames.VIEW);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }
}
