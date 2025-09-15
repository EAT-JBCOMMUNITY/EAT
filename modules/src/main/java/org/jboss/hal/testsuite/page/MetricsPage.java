package org.jboss.hal.testsuite.page;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.cli.Library;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.MetricsAreaFragment;
import org.jboss.hal.testsuite.page.runtime.DomainRuntimeEntryPoint;
import org.jboss.hal.testsuite.page.runtime.StandaloneRuntimeEntryPoint;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public abstract class MetricsPage extends BasePage {

    public static final String MONITORED_SERVER = "server-one";

    public MetricsAreaFragment getMetricsArea(String title) {
        //////add property
        By selector = By.xpath(".//table[contains(@class, '" + PropUtils.get("metrics.container.class") + "')][.//h3[text()='" + title + "']]");
        WebElement element = null;
        try {
            element = getContentRoot().findElements(selector).stream().filter(el -> el.isDisplayed()).findFirst().get();
        } catch (NoSuchElementException exc) {
            return null;
        }
        MetricsAreaFragment area = Graphene.createPageFragment(MetricsAreaFragment.class, element);

        Map<String, String> metricGrid = element.findElement(By.className(PropUtils.get("metrics.grid.class"))).
                findElements(By.tagName("tr")).stream().
                collect(Collectors.toMap(
                        e -> e.findElement(By.className(PropUtils.get("metrics.grid.nominal.class"))).getText(),
                        e -> e.findElement(By.className(PropUtils.get("metrics.grid.numerical.class"))).getText()));

        area.setMetricGrid(metricGrid);

        return area;
    }

    public void refreshStats() {
        WebElement viewPanel = browser.findElements(Application.APPLICATION_PANEL)
                .stream().filter(p -> p.isDisplayed()).findFirst().get();

        WebElement refreshLink = viewPanel.findElement(By.className("html-link"));
        refreshLink.click();
        Library.letsSleep(500);
    }

    /**
     * Navigate in Runtime menu to the subsystem identified by provided label. For domain navigateToServer to the subsystem
     * in {@link #MONITORED_SERVER}.
     * @param subsystemLabel
     */
    protected void navigate2runtimeSubsystem(String subsystemLabel) {
        navigate2runtimeSubsystem(subsystemLabel, MONITORED_SERVER);
    }

    /**
     * Navigate in Runtime menu to the subsystem identified by provided label. For domain mode, navigate to specified server
     * @param subsystemLabel subsystem to be navigated
     * @param serverName server to be navigated
     */
    protected void navigate2runtimeSubsystem(String subsystemLabel, String serverName) {
        FinderNavigation navigation;
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainRuntimeEntryPoint.class)
                    .step(FinderNames.BROWSE_DOMAIN_BY, FinderNames.HOSTS)
                    .step(FinderNames.HOST, ConfigUtils.getDefaultHost())
                    .step(FinderNames.SERVER, serverName);
        } else {
            navigation = new FinderNavigation(browser, StandaloneRuntimeEntryPoint.class)
                    .step(FinderNames.SERVER, FinderNames.STANDALONE_SERVER);
        }
        navigation = navigation.step(FinderNames.MONITOR, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, subsystemLabel);

        navigation.selectRow().invoke("View");
        Application.waitUntilVisible();
    }
}
