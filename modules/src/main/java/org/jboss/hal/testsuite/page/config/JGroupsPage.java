package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.jgroups.ExecutorTabFragment;
import org.jboss.hal.testsuite.fragment.config.jgroups.JGroupsConfigArea;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@Location("#jgroups")
public class JGroupsPage extends ConfigurationPage {

    public void selectStackByName(String name) {
        getResourceManager().getResourceTable().getRowByText(0, name).view();
    }

    public void openExecutors() {
        By parent = ByJQuery.selector("table.gwt-DisclosurePanel.default-disclosure");
        WebElement root = getContentRoot().findElement(parent);
        ExecutorTabFragment executorTabFragment = Graphene.createPageFragment(ExecutorTabFragment.class, root);
        executorTabFragment.openExecutors();
    }

    public void switchToTransport() {
        switchTo("Transport");
    }

    public void switchToProtocols() {
        switchTo("Protocols");
    }

    public void switchToProtocol(String protocol) {
        switchToProtocols();
        getResourceManager().getResourceTable().getRowByText(0, protocol).click();
    }

    public void switchTo(String name) {
        switchView(name);
        Console.withBrowser(browser).waitUntilLoaded();
    }

    public JGroupsConfigArea getConfig() {
        return getConfig(JGroupsConfigArea.class);
    }

    public void editTextAndSave(String identifier, String value) {
        getConfigFragment().editTextAndSave(identifier, value);
    }

    public ConfigFragment getConfigFragment() {
        ConfigAreaFragment area = getConfig();
        return  Graphene.createPageFragment(ConfigFragment.class, area.getRoot());
    }

}
