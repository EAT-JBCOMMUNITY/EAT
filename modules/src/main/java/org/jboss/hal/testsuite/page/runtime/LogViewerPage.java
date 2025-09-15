package org.jboss.hal.testsuite.page.runtime;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.fragment.shared.modal.LogConfirmDownloadWindow;
import org.jboss.hal.testsuite.page.RuntimePage;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

/**
 * @author jcechace
 */

@Location("#logfiles")
public class LogViewerPage extends RuntimePage {

    public static final String MAIN_TAB = PropUtils.get("logviewer.tab.main.label");
    public static final String CLOSE_TAB = PropUtils.get("content.tab.close.class");
    public static final String REMOVE = PropUtils.get("remove.icon.class");
    public static final String VIEW_BUTTON = PropUtils.get("logviewer.btn.view.id");


    /**
     * Switch to main tab with overview of log files
     */
    public void switchToFileList() {
        switchTab(MAIN_TAB);
    }

    /**
     * Select a log file
     *
     * @param name name of the log file
     */
    public void selectLogFile(String name) {
        getResourceManager().selectByName(name);
    }


    /**
     * Open a log file
     *
     * @param name name of the log file
     */
    public void viewFile(String name) {
        selectLogFile(name);
        clickButton(VIEW_BUTTON);
        waitUntilTabIsPresent(name);
    }

    /**
     * Open a large l
     *
     * @param name name of the log file
     * @return the large file confirmation window
     */
    public LogConfirmDownloadWindow viewLargeFile(String name) {
        selectLogFile(name);
        clickButton(VIEW_BUTTON);
        return Console.withBrowser(browser).openedWindow(LogConfirmDownloadWindow.class);
    }

    /**
     * Returns the content of log file
     *
     * WARNING: DO NOT CALL THIS ON LARGER LOG FILES.
     *
     * @return content of log file
     */
    public String getLogConent() {
        By selector = By.className(PropUtils.get("logviewer.content.text.class"));
        WebElement content = getContentRoot().findElement(selector);

        return content.getText();
    }

    /**
     * Clicks the close icon
     * @param name name of the file displayed on tab
     *
     * @return <code>false</code> if no such file is open, true otherwise
     */
    public boolean closeFile(String name) {
        Map<String, WebElement> map = openedFileTabsMap();

        WebElement tab = map.get(name);


        By selector = ByJQuery.selector("." + REMOVE + "." + CLOSE_TAB + ":visible");
        WebElement close = tab.findElement(selector);
        close.click();

        try {
            waitUntilTabIsNotPresent(name);
            return true;
        } catch (TimeoutException e) {
            return  false;
        }
    }

    /**
     * Returns a set of opened files
     *
     * @return set of opened files
     */
    public Set<String> openedFileNames() {
        Map<String, WebElement> map = openedFileTabsMap();

        return map.keySet();
    }

    private List<WebElement> openedFileTabs() {
        By selector = By.className("hal-TabLayout-tab");
        List<WebElement> elements = getContentRoot().findElements(selector);

        if (elements.size() < 2) {
            return Collections.emptyList();
        }

        return  elements;
    }

    private Map<String, WebElement> openedFileTabsMap() {
        List<WebElement> tabs = openedFileTabs();
        Map<String, WebElement> map = new HashMap<String, WebElement>();

        for (int i = 1; i < tabs.size(); i++) {
            WebElement elm = tabs.get(i);
            map.put(elm.getText().toLowerCase(), elm);
        }

        return map;
    }
}
