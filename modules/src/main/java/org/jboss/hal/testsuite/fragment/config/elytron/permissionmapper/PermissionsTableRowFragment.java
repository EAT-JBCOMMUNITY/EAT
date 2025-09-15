package org.jboss.hal.testsuite.fragment.config.elytron.permissionmapper;

import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.openqa.selenium.WebElement;

public class PermissionsTableRowFragment extends ResourceTableRowFragment {

    public String getClassNameValue() {
        return getCellValue(0);
    }

    public WebElement getClassName() {
        return getCell(0);
    }

    public String getModuleValue() {
        return getCellValue(1);
    }

    public WebElement getModule() {
        return getCell(1);
    }

    public String getTargetNameValue() {
        return getCellValue(2);
    }

    public WebElement getTargetName() {
        return getCell(2);
    }

    public String getActionValue() {
        return getCellValue(3);
    }

    public WebElement getAction() {
        return getCell(3);
    }

}
