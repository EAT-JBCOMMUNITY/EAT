package org.jboss.hal.testsuite.page.runtime;

import static java.util.stream.Collectors.toSet;

import java.util.List;
import java.util.Set;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.runtime.batch.BatchTableFragment;
import org.jboss.hal.testsuite.fragment.runtime.batch.BatchTableRowFragment;
import org.jboss.hal.testsuite.page.MetricsPage;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Encapsulates GUI interaction with Batch subsystem runtime page
 *
 * @author pjelinek
 */
public class BatchRuntimePage extends MetricsPage implements Navigatable {

    @Override
    public void navigate() {
        navigate2runtimeSubsystem("Batch");
    }

    public BatchRuntimePage navigate2jobs() {
        navigate();
        switchTab("Jobs");
        Console.withBrowser(browser).waitUntilLoaded();
        return this;
    }

    public List<BatchTableRowFragment> getAllRows() {
        return getResourceManager().getResourceTable(BatchTableFragment.class).getAllRows();
    }

    public Set<BatchTableRowFragment> getRowsForJobXmlName(final String jobFileName) {
        return getAllRows()
                .stream()
                .filter(row -> row.getJobXmlNameValue().equals(jobFileName))
                .collect(toSet());
    }

    public BatchTableRowFragment selectRowForJobXmlName(String jobFileName) {
        return getResourceManager().getResourceTable(BatchTableFragment.class)
                .selectRowBy(row -> row.getJobXmlNameValue().equals(jobFileName));
    }

    public BatchTableRowFragment getRowByExecutionId(String executionId) {
        return getResourceManager().getResourceTable(BatchTableFragment.class)
                .getRowBy(row -> row.getExecutionIdValue().equals(executionId));
    }

    public void setFilterText(String text) {
        WebElement filterElement = Console.withBrowser(browser)
                .findElement(ByJQuery.selector("input[type='text']"), getContentPanel());
        filterElement.clear();
        filterElement.sendKeys(text);
    }

    private WebElement getContentPanel() {
        By contentPanelSelector = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");
        return getContentRoot().findElement(contentPanelSelector);
    }

}
