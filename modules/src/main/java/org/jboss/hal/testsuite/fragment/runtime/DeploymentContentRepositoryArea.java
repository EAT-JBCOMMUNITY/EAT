package org.jboss.hal.testsuite.fragment.runtime;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class DeploymentContentRepositoryArea extends ConfigFragment {
    public DeploymentWizard add() {
        return getResourceManager().addResource(DeploymentWizard.class);
    }

    public void removeAndConfirm(String name) {
        getResourceManager().removeResourceAndConfirm(name);
    }

    public void assignDeployment(String deploymentName, String groupName) {
        By groupSelector = By.xpath(".//div[text()='" + groupName + "']");
        getResourceManager().selectByName(deploymentName);
        clickButton(PropUtils.get("config.deployments.assign.label"));
        WizardWindow window = Console.withBrowser(browser).openedWizard();
        window.getRoot().findElement(groupSelector).click();
        window.finish();
    }
}
