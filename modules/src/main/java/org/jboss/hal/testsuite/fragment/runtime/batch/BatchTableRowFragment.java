package org.jboss.hal.testsuite.fragment.runtime.batch;

import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.openqa.selenium.WebElement;

public class BatchTableRowFragment extends ResourceTableRowFragment {

    public WebElement getDeployment() {
        return getCell(0);
    }

    public String getDeploymentValue() {
        return getCellValue(0);
    }

    public WebElement getJobXmlName() {
        return getCell(1);
    }

    public String getJobXmlNameValue() {
        return getCellValue(1);
    }

    public WebElement getExecutionId() {
        return getCell(2);
    }

    public String getExecutionIdValue() {
        return getCellValue(2);
    }

    public WebElement getInstanceId() {
        return getCell(3);
    }

    public String getInstanceIdValue() {
        return getCellValue(3);
    }

    public WebElement getBatchStatus() {
        return getCell(4);
    }

    public String  getBatchStatusValue() {
        return getCellValue(4);
    }

    public WebElement getStartTime() {
        return getCell(5);
    }

    public String getStartTimeValue() {
        return getCellValue(5);
    }

}
