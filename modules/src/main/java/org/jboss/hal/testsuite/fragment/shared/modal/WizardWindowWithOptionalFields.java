package org.jboss.hal.testsuite.fragment.shared.modal;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * {@link WizardWindow} with optional fields tab. E.g. tab labeled 'Optional Fields' when adding new resource.
 */
public class WizardWindowWithOptionalFields extends WizardWindow {

    private boolean isOptionalFieldsTabOpened;

    /**
     * Opens tab in wizard window. E.g. tab labeled 'Optional Fields' when adding new resource.
     */
    public void openOptionalFieldsTab() {
        final By disclosurePanelSelector = ByJQuery.selector(".form-edit-panel .gwt-DisclosurePanel tbody > tr > td > a");
        final WebElement disclosurePanel = root.findElement(disclosurePanelSelector);
        Graphene.waitGui().until().element(disclosurePanelSelector).is().visible();
        disclosurePanel.click();

        Graphene.waitGui(browser).until()
                .element(ByJQuery.selector(".form-edit-panel .gwt-DisclosurePanel tr:nth-child(2)"))
                .is()
                .visible();

        isOptionalFieldsTabOpened = true;
    }

    public void openOptionalFieldsTabIfNotAlreadyOpened() {
        if (!isOptionalFieldsTabOpened) {
            openOptionalFieldsTab();
        }
    }
}
