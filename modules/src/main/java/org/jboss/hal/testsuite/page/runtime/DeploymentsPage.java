package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.Column;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.finder.Row;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.fragment.runtime.DeploymentContentRepositoryArea;
import org.jboss.hal.testsuite.fragment.runtime.DeploymentServerGroupArea;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.page.config.ConfigurationPage;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstraction over 'Deployments' page for both domain and standalone mode.
 */
public class DeploymentsPage extends ConfigurationPage {

    private static final By BACK_BUTTON = By.xpath(".//a[text()='Back']");
    private static final By CONTENT = By.className(PropUtils.get("page.content.gwt-layoutpanel"));

    private static final String CONTENT_TABLE_ROW = "table:contains('Data') tr.form-attribute-row";

    private enum ServerMode {
        STANDALONE, DOMAIN
    }

    private void checkIfServerIsRunningInExpectedMode(ServerMode expectedServerMode) {
        if (expectedServerMode == ServerMode.DOMAIN && !ConfigUtils.isDomain()) {
            throw new IllegalStateException("This method can be used only in domain mode!");
        } else if (expectedServerMode == ServerMode.STANDALONE && ConfigUtils.isDomain()) {
            throw new IllegalStateException("This method can be used only in standalone mode!");
        }
    }

    /**
     * Selects 'Content Repository' in 'Browse By' column and obtains area containing present deployments.
     * @return Area containing deployments in content repository
     */
    public DeploymentContentRepositoryArea switchToContentRepository() {
        switchTab("Content Repository");
        return getDeploymentContent(DeploymentContentRepositoryArea.class);
    }

    /**
     * Selects 'Server Groups' in 'Browse By' column and switches to given server group.
     * @param serverGroup name of server group
     * @return Area containing deployments assigned to given server group
     */
    public DeploymentServerGroupArea switchToServerGroup(String serverGroup) {
        switchTab("Server Groups");
        WebElement backAnchor = getContentRoot().findElement(BACK_BUTTON);
        if (backAnchor.isDisplayed()) {
            backAnchor.click();
        }
        getResourceManager().viewByName(serverGroup);
        return getDeploymentContent(DeploymentServerGroupArea.class);
    }

    public <T extends BaseFragment> T getDeploymentContent(Class<T> clazz) {
        WebElement content = getContentRoot().findElement(CONTENT);
        return Graphene.createPageFragment(clazz, content);
    }

    /**
     * Get area containing deployments on page
     * @return area containing deployments on page
     */
    public DeploymentContentRepositoryArea  getDeploymentContent() {
        WebElement content = getContentRoot().findElement(CONTENT);
        return Graphene.createPageFragment(DeploymentContentRepositoryArea.class, content);
    }

    /**
     * Checks if 'Assign Content' window opened after click on 'Assign' button in 'Unassigned Content' contains name of
     * given deployment.
     * @param assignName Name of deployment
     * @return True if deployment's name is present
     */
    public boolean checkAssignDeploymentNameInAssignContent(String assignName) {
        WebElement editPanel = browser.findElement(By.className("default-window-content"));
        String contentText = editPanel.findElement(By.className("gwt-Label")).getText();
        editPanel.findElement(ByJQuery.selector("button:contains(Cancel)")).click();
        return contentText.contains(assignName);
    }

    /**
     * navigates to deployment in standalone mode.
     * @param deploymentName Name of deployment
     * @return selected row
     */
    public Row navigateToDeploymentInStandalone(String deploymentName) {
        checkIfServerIsRunningInExpectedMode(ServerMode.STANDALONE);
        return new FinderNavigation(browser, StandaloneDeploymentEntryPoint.class)
                .step(FinderNames.DEPLOYMENT, deploymentName)
                .selectRow();
    }

    /**
     * Navigates to row in server group. Domain only!
     * @param groupName Name of server group
     * @param deploymentName Name of deployment
     * @return selected row
     */
    public Row navigateToRowInServerGroup(String groupName, String deploymentName) {
        checkIfServerIsRunningInExpectedMode(ServerMode.DOMAIN);
        Row row = new FinderNavigation(browser, DomainDeploymentEntryPoint.class)
                .step(FinderNames.BROWSE_BY, FinderNames.SERVER_GROUPS)
                .step(FinderNames.SERVER_GROUP, groupName)
                .step(FinderNames.DEPLOYMENT, deploymentName)
                .selectRow();
        Console.withBrowser(browser).waitUntilLoaded();
        return row;
    }

    /**
     * Navigates to row containing deployment's name in unassigned content. Domain Only!
     * @param deploymentName Name of deployment
     * @return selected row
     */
    public Row navigateToRowInUnassignedContent(String deploymentName) {
        checkIfServerIsRunningInExpectedMode(ServerMode.DOMAIN);
        Row row = new FinderNavigation(browser, DomainDeploymentEntryPoint.class)
                .step(FinderNames.BROWSE_BY, "Unassigned Content")
                .step("Unassigned", deploymentName)
                .selectRow();
        Console.withBrowser(browser).waitUntilLoaded();
        return row;
    }

    /**
     * Navigates to column in deployment repository. Domain only!
     * @return selected column
     */
    public Column navigateToColumnInContentRepository() {
        checkIfServerIsRunningInExpectedMode(ServerMode.DOMAIN);
        Column column = new FinderNavigation(browser, DomainDeploymentEntryPoint.class)
                .step(FinderNames.BROWSE_BY, "Content Repository").step("All Content")
                .selectColumn();
        Console.withBrowser(browser).waitUntilLoaded();
        return column;
    }

    /**
     * Navigates do deployment column in server group. Domain only!
     * @param groupName Name of server group
     * @return selected column
     */
    public Column navigateToDeploymentColumnInServerGroup(String groupName) {
        checkIfServerIsRunningInExpectedMode(ServerMode.DOMAIN);
        Column column = new FinderNavigation(browser, DomainDeploymentEntryPoint.class)
                .step(FinderNames.BROWSE_BY, FinderNames.SERVER_GROUPS)
                .step(FinderNames.SERVER_GROUP, groupName)
                .step(FinderNames.DEPLOYMENT)
                .selectColumn();
        Console.withBrowser(browser).waitUntilLoaded();
        return column;
    }


    public List<String> getDeploymentBrowsedContentItems() {
        By selector = ByJQuery.selector("table.browse-content tr");
        List<String> items = new ArrayList<>();

        List<WebElement> webElements = getContentRoot().findElements(selector);
        items.addAll(webElements.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList()));

        return items;
    }

    /**
     * Navigates to deployment and invokes 'View' action. For use in both standalone and domain. In domain,
     * 'main-server-group' is used. If you prefer to use other group, please refer to
     * {@link DeploymentsPage#navigateToRowInServerGroup(String, String)}.
     * @param deploymentName name of deployment
     */
    public void navigateToDeploymentAndInvokeView(String deploymentName) {
        if (ConfigUtils.isDomain()) {
            navigateToRowInServerGroup("main-server-group", deploymentName).invoke(FinderNames.VIEW);
        } else {
            navigateToDeploymentInStandalone(deploymentName).invoke(FinderNames.VIEW);
        }
        Application.waitUntilVisible();
    }

    /**
     * Disables enabled deployment. Standalone only!
     * @param deploymentName name of deployment
     */
    public void disableEnabledDeployment(String deploymentName) {
        checkIfServerIsRunningInExpectedMode(ServerMode.STANDALONE);
        FinderNavigation navigation = new FinderNavigation(browser, StandaloneDeploymentEntryPoint.class);
        Row row = navigation.step(FinderNames.DEPLOYMENT, deploymentName).selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        row.invoke(FinderNames.DISABLE);
        Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
    }

    /**
     * Enables disabled deployment. Standalone only!
     * @param deploymentName name of deployment
     */
    public void enableDisabledDeployment(String deploymentName) {
        checkIfServerIsRunningInExpectedMode(ServerMode.STANDALONE);
        FinderNavigation navigation = new FinderNavigation(browser, StandaloneDeploymentEntryPoint.class);
        Row row = navigation.step(FinderNames.DEPLOYMENT, deploymentName).selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        row.invoke(FinderNames.ENABLE);
        Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
    }

}
