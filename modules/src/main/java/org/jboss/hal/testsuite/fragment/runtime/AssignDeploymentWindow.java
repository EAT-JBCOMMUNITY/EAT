package org.jboss.hal.testsuite.fragment.runtime;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.WindowState;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableFragment;
import org.openqa.selenium.WebElement;

/**
 * GUI abstraction for the 'Assign content' window in domain mode to assign deployment to particular server group(s).
 * @author pjelinek
 */
public class AssignDeploymentWindow extends WindowFragment {

    public AssignDeploymentWindow selectServerGroup(String serverGroupName) {
        Graphene.waitGui().until().element(ByJQuery.selector(".default-cell-table")).is().present();
        ResourceTableFragment table = Graphene.createPageFragment(ResourceTableFragment.class, this.getRoot());
        WebElement checkbox = table.getRowByText(1, serverGroupName).getCell(0)
                .findElement(ByJQuery.selector("input[type='checkbox']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        Graphene.waitGui().until().element(checkbox).is().selected();
        return this;
    }

    /**
     * Click 'Assign' button
     */
    public WindowState assign() {
        clickButton("Assign");
        waitUntilClosed();
        closed = true;
        return new WindowState(this);
    }
}
