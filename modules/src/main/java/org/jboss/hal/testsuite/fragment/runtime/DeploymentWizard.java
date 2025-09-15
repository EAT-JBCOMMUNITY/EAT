package org.jboss.hal.testsuite.fragment.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class DeploymentWizard extends WizardWindow {

    private static final Logger log = LoggerFactory.getLogger(DeploymentWizard.class);

    private static final String NAME = "name";
    private static final String ENABLED = "enabled";
    private static final String RUNTIME_NAME = "runtimeName";
    private static final String IS_ARCHIVE = "archive";
    private static final String PATH = "path";
    private static final String UPLOAD_FORM_ELEMENT = "uploadFormElement";
    private static final String MANAGED = "Upload a new deployment";
    private static final String UNMANAGED = "Create an unmanaged deployment";
    private static final String FROMREPOSITORY = "Choose a deployment from the content repository";

    private static final By MANAGED_BUTTON = By.xpath(".//label[text()='" + MANAGED + "']");
    private static final By UNMANAGED_BUTTON = By.xpath(".//label[text()='" + UNMANAGED + "']");
    private static final By FROMREPOSITORY_BUTTON = By.xpath(".//label[text()='" + FROMREPOSITORY + "']");

    public DeploymentWizard switchToRepository() {
        root.findElement(FROMREPOSITORY_BUTTON).click();
        return this;
    }

    public DeploymentWizard switchToManaged() {
        root.findElement(MANAGED_BUTTON).click();
        return this;
    }

    public DeploymentWizard switchToUnmanaged() {
        root.findElement(UNMANAGED_BUTTON).click();
        return this;
    }

    public DeploymentWizard uploadDeployment(File file) {
        getEditor().uploadFile(file, UPLOAD_FORM_ELEMENT);
        return this;
    }

    public DeploymentWizard name(String name) {
        getEditor().text(NAME, name);
        return this;
    }

    public DeploymentWizard runtimeName(String runtimeName) {
        getEditor().text(RUNTIME_NAME, runtimeName);
        return this;
    }

    public DeploymentWizard enable(boolean enable) {
        getEditor().checkbox(ENABLED, enable);
        return this;
    }

    public DeploymentWizard enableAfterDeployment(boolean enable) {
        getEditor().checkbox("enableAfterDeployment", enable);
        return this;
    }

    public DeploymentWizard path(String path) {
        getEditor().text(PATH, path);
        return this;
    }

    public DeploymentWizard isArchive(boolean val) {
        getEditor().checkbox(IS_ARCHIVE, val);
        return this;
    }

    public DeploymentWizard nextFluent() {
        next();
        return this;
    }

    public DeploymentWizard enableDeploymentInGroup(String deploymentName) {
        Graphene.waitAjax().until().element(ByJQuery.selector(".default-cell-table")).is().present();
        ResourceTableFragment table = Graphene.createPageFragment(ResourceTableFragment.class, this.getRoot());
        WebElement checkbox = table.getRowByText(0, deploymentName)
                .getCell(1)
                .findElement(By.tagName("input"));
        checkbox.click();
        return this;
    }

}
