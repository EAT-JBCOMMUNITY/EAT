package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.socketbindings.InboundSocketBindingFragment;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#socket-bindings;name=standard-sockets")
public class StandardSocketBindingsPage extends ConfigPage implements Navigatable {

    private static final By CONTENT_ROOT = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");

    public InboundSocketBindingFragment switchToInbound() {
        return switchTo(InboundSocketBindingFragment.class, "Inbound");
    }

    public ConfigFragment switchToOutboundRemote() {
        return switchTo("Outbound Remote");
    }

    public ConfigFragment switchToOutboundLocal() {
        return switchTo("Outbound Local");
    }

    private ConfigFragment switchTo(String label) {
        return switchTo(ConfigFragment.class, label);
    }
    private <T extends ConfigFragment> T switchTo(Class<T> clazz, String label) {
        switchSubTab(label);
        WebElement root = getContentRoot().findElement(CONTENT_ROOT);
        return Graphene.createPageFragment(clazz, root);
    }

    @Override
    public void navigate() {
        new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, "Socket Binding")
                .selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        getResourceManager().viewByName("standard-sockets");
        Console.withBrowser(browser).waitUntilLoaded().waitForContent();
    }
}
