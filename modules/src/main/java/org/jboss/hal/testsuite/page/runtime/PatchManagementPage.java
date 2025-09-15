package org.jboss.hal.testsuite.page.runtime;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.fragment.config.patching.PatchingWizard;
import org.jboss.hal.testsuite.fragment.config.patching.RestartWizard;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.jboss.hal.testsuite.page.ConfigPage;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author rhatlapa (rhatlapa@redhat.com)
 */
@Location("#patching")
public class PatchManagementPage extends ConfigPage {

    public PatchingWizard applyNewPatch() {
        return addResource(PatchingWizard.class, PropUtils.get("runtime.patching.apply.label"));
    }

    public RestartWizard restartServer() {
        return addResource(RestartWizard.class, PropUtils.get("runtime.patching.restart.label"));
    }

    public PatchingWizard rollbackPatch(String patchId) {
        getResourceTable().selectRowByText(0, patchId);
        return addResource(PatchingWizard.class, PropUtils.get("runtime.patching.rollback.label"));
    }

    public ResourceTableRowFragment getLastPatchRow() {
        return getResourceManager().getResourceTable().getVisibleRow(0);
    }

    public boolean latestAppliedPatchLabelIsDisplayed() {
        By lastPatchNameSelector = ByJQuery.selector("h3:contains(Latest Applied Patch)");
        return getContentRoot().findElement(lastPatchNameSelector).isDisplayed();
    }

    public boolean latestAppliedPatchIsPendingRestart() {
        By lastPatchCellSelector = ByJQuery.selector("td>div:contains(Latest Applied Patch)");
        return getContentRoot().findElement(lastPatchCellSelector).getText().contains("Pending restart");
    }

    public String getLastPatchId() {
        return getLastPatchRow().getCellValue(0);
    }

    public String getLastPatchDate() {
        return getLastPatchRow().getCellValue(2);
    }

    public String getLastPatchType() {
        return getLastPatchRow().getCellValue(3);
    }

}
