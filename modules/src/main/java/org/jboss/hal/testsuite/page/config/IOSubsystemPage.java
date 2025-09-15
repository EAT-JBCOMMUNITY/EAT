package org.jboss.hal.testsuite.page.config;

import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.fragment.config.io.BufferPoolWizard;
import org.jboss.hal.testsuite.fragment.config.io.WorkerWizard;
import org.jboss.hal.testsuite.util.Console;

/**
 * Abstraction over UI page for IO subsystem
 */
public class IOSubsystemPage extends ConfigurationPage {

    public BufferPoolWizard addBufferPool() {
        getResourceManager().addResource();
        return Console.withBrowser(browser).openedWizard(BufferPoolWizard.class);
    }

    public WorkerWizard addWorker() {
        getResourceManager().addResource();
        return Console.withBrowser(browser).openedWizard(WorkerWizard.class);
    }

    public void navigateToBufferPools() {
        getSubsystemNavigation("IO").selectRow(true).invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        switchTab("Buffer Pool");
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }

    public void navigateToWorkers() {
        getSubsystemNavigation("IO").selectRow(true).invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
        switchTab("Worker");
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
    }

}
