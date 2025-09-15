package org.jboss.hal.testsuite.fragment.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class StandaloneDeploymentsArea extends BaseFragment {


    public DeploymentWizard add() {
        clickButton(PropUtils.get("config.shared.add.label"));
        return Console.withBrowser(browser).openedWizard(DeploymentWizard.class);
    }

    public WebElement selectDeployment(String name) {
        By deploymentSelector = By.xpath(".//div[text()='" + name + "']");
        WebElement deployment = root.findElement(deploymentSelector);
        deployment.click();
        return deployment;
    }

    public void removeAndConfirm(String name) {
        WebElement deployment = selectDeployment(name);
        clickButton(PropUtils.get("config.shared.remove.label"));
        Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
        Graphene.waitGui().until().element(deployment).is().not().present();
    }

    public void changeState(String name) {
        selectDeployment(name);
        clickButton(PropUtils.get("config.deployments.state.change.label"));
        ConfirmationWindow window = Console.withBrowser(browser).openedWindow(ConfirmationWindow.class);
        window.confirm();
        Graphene.waitGui().until().element(window.getRoot()).is().not().present();
    }
}
