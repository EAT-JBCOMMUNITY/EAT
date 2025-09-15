/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.hal.testsuite.finder;

import static org.jboss.hal.testsuite.util.Console.DEFAULT_PAGE_LOAD_TIMEOUT;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.page.BasePage;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.Find;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to select a column or row in a finder of the specified page.
 *
 * @author Harald Pehl
 */
public class FinderNavigation {

    private static class AddressTuple {

        final String column;
        final String row;

        AddressTuple(final String column, final String row) {
            this.column = column;
            this.row = row;
        }
    }

    public interface Hook {
        void performAfterRowClick();
    }

    private static final Logger log = LoggerFactory.getLogger(FinderNavigation.class);
    private static final String WILDCARD = "*";

    private final WebDriver browser;
    private final Class<? extends BasePage> page;
    private final List<AddressTuple> address;
    private final Hook hook;
    private boolean refresh;
    private int naviRetriesNo = 0;
    private long pageLoadTimeout = DEFAULT_PAGE_LOAD_TIMEOUT;

    /**
     * This constructor should not be used regularly! <br />
     * It's for special cases when you need to perform some action after every row click.<br />
     * E.g. sleeps to workaround HAL-803
     * @param hook action after every row click
     */
    public FinderNavigation(final WebDriver browser, final Class<? extends BasePage> page, Hook hook) {
        this.browser = browser;
        this.page = page;
        this.hook = hook;
        this.address = new ArrayList<>();
        this.refresh = true;
    }

    /**
     * This constructor should not be used regularly! <br />
     * It's for special cases when you need to delay after every row click.<br />
     * This is workaround for HAL-803 snd should be used mainly when you are interested about preview content.
     * @param clickDelay milis to wait after every row click
     */
    public FinderNavigation(final WebDriver browser, final Class<? extends BasePage> page, final long clickDelay) {
        this(browser, page, () -> {
            try {
                Thread.sleep(clickDelay);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        });
    }

    public FinderNavigation(final WebDriver browser, final Class<? extends BasePage> page) {
        this(browser, page, () -> {
        });
    }

    /**
     * Sets the timeout in seconds for the application to be loaded in the beginning of the navigation after refresh.
     * Use carefully in exceptional situations where default timeout of 30 seconds is not sufficient,
     * e.g. for large domain performance tests.
     * @param pageLoadTimeout - timeout in seconds
     */
    public FinderNavigation withPageLoadTimeout(long pageLoadTimeout) {
        this.pageLoadTimeout = pageLoadTimeout;
        return this;
    }

    /**
     * Adds navigation step of column selection.
     */
    public FinderNavigation step(String column) {
        address.add(new AddressTuple(column, WILDCARD));
        return this;
    }

    /**
     * Adds navigation step of selection of row inside a column.
     */
    public FinderNavigation step(String column, String row) {
        address.add(new AddressTuple(column, row));
        return this;
    }

    public Column selectColumn() {
        WebElement column = navigate()[0];
        if (column == null) {
            throw new IllegalStateException("No address for selecting a column given");
        }
        return Graphene.createPageFragment(Column.class, column);
    }

    /**
     * @param exactRowText decides whether the row will be selected by its exact label (equals) or by part of its label (contains)
     */
    public Column selectColumn(Boolean exactRowText) {
        WebElement column = navigate(exactRowText)[0];
        if (column == null) {
            throw new IllegalStateException("No address for selecting a column given");
        }
        return Graphene.createPageFragment(Column.class, column);
    }

    public Row selectRow() {
        WebElement row = navigate()[1];
        if (row == null) {
            throw new IllegalStateException("No address for selecting a row given.");
        }
        return Graphene.createPageFragment(Row.class, row);
    }

    /**
     * @param exactRowText decides whether the row will be selected by its exact label (equals) or by part of its label (contains)
     */
    public Row selectRow(Boolean exactRowText) {
        WebElement row = navigate(exactRowText)[1];
        if (row == null) {
            throw new IllegalStateException("No address for selecting a row given.");
        }
        return Graphene.createPageFragment(Row.class, row);
    }

    public PreviewFragment getPreview() {
        By previewSelector = By.className("preview-content");
        Graphene.waitGui().until().element(previewSelector).is().visible();
        WebElement preview = browser.findElement(previewSelector);
        return Graphene.createPageFragment(PreviewFragment.class, preview);
    }

    private WebElement[] navigate() {
        return navigate(false);
    }

    private WebElement[] navigate(Boolean exactRowText) {
        WebElement[] columnRow = new WebElement[2];
        if (refresh) {
           // Console.withBrowser(browser).refreshAndNavigate(page);
            Console.withBrowser(browser).withPageLoadTimeout(pageLoadTimeout).waitForFirstNavigationPanel(page);
        }
        for (int i = 0; i < address.size(); i++) {
            AddressTuple tuple = address.get(i);

            columnRow[0] = browser.findElement(columnSelector(tuple.column));
            if (!WILDCARD.equals(tuple.row)) {
                By rowSelector = exactRowText ? rowSelectorEquals(tuple.row) : rowSelector(tuple.row);
                columnRow[1] = new Find().elementWithGuiTimeout(columnRow[0], rowSelector);
                if (!columnRow[1].isDisplayed()) {
                    ((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", columnRow[1]);
                }
                columnRow[1].click();
                hook.performAfterRowClick();
                Graphene.waitModel().until().element(columnRow[0], rowSelector).attribute("class")
                        .contains("cellTableSelectedRowCell");

                // wait for next column to be visible
                if (i < address.size() - 1) {
                    AddressTuple nextTuple = address.get(i + 1);
                    try {
                        Graphene.waitModel().until().element(columnSelector(nextTuple.column)).is().visible();

                    // TODO remove catch as soon as JBEAP-2168 is fixed!
                    } catch (TimeoutException e) {
                        if (ConfigUtils.get("jbeap2168workaround") != null && naviRetriesNo++ < 3) {
                            log.warn("Navigation frozen! JBEAP-2168 needs to be fixed!");
                            return navigate(exactRowText);
                        } else {
                            naviRetriesNo = 0;
                            throw new TimeoutException(
                                    "Navigation frozen! Probably due to JBEAP-2168 or changed navigation tree.", e);
                        }
                    }
                }
            }
        }
        naviRetriesNo = 0;
        return columnRow;
    }

    /**
     * This method should be called after normal finder navigation was called and made with selectRow or selectColumn
     * so user is navigated to some page.
     * It is good when you want to re-navigate at some page you are currently on.
     * You can only move in submenus on current page.
     * @return empty navigation without refresh and navigation to HomePage
     */
    public FinderNavigation resetNavigation() {
        address.clear();
        this.refresh = false;
        return this;
    }


    /**
     * Method delete all inserted addresses.
     * After method navigation has only page where to navigate, but no addresses in submenus.
     * Navigation has still active refresh.
     */
    public void clearNavigation() {
        if (address.size() > 0 ) address.clear();
    }

    /**
     * Select current row, click View and wait for configured model timeout for application detail to open.
     */
    public void openApplication() {
        this.selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }

    /**
     * Select current row, click View and wait for <i>timeout</i> seconds for application detail to open.
     */
    public void openApplication(int timeout) {
        this.selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible(timeout);
    }

    private By columnSelector(String name) {
        return By.cssSelector("[data-column=\"" + name + "\"]");
    }

    By rowSelectorEquals(String label) {
        return getRowSelector(" and text()='" + label + "']]");
    }

    By rowSelector(String label) {
        return getRowSelector(" and contains(.,'" + label + "')]]");
    }

    private By getRowSelector(String xpathSuffix) {
        String cellClass = PropUtils.get("table.cell.class");
        return By.ByXPath.xpath(".//td[contains(@class,'" + cellClass + "') and descendant::div[@class='navigation-column-item'" + xpathSuffix);
    }
}
