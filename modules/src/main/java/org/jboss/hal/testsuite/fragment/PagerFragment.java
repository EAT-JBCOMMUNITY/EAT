package org.jboss.hal.testsuite.fragment;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * @author jbliznak@redhat.com
 */
public class PagerFragment extends BaseFragment {

    public static final String CLASS_NAME_PAGER = PropUtils.get("resourcepager.pager.class");
    public static final int IDX_FIRST_PAGE = 0;
    public static final int IDX_PREVIOUS_PAGE = 1;
    public static final int IDX_PAGER_INFO = 2;
    public static final int IDX_NEXT_PAGE = 3;
    public static final int IDX_LAST_PAGE = 4;
    public static final String PAGER_INFO_PATTERN = "(\\d+)-(\\d+) of (\\d+)";
    public static final int IDX_RECORDS_FROM = 1;
    public static final int IDX_RECORDS_TO = 2;
    public static final int IDX_RECORDS_TOTAL = 3;

    /**
     * @return total count of records or -1 if it can't be parsed from HTML
     */
    public int getTotalRecordsCount() {
        return getNthNumberFromInfo(IDX_RECORDS_TOTAL);
    }

    /**
     * @return current displayed 'from' number of records or -1 if it can't be parsed from HTML
     */
    public int getCurrentFromNumber() {
        return getNthNumberFromInfo(IDX_RECORDS_FROM);
    }

    /**
     * @return current displayed 'to' number of records or -1 if it can't be parsed from HTML
     */
    public int getCurrentToNumber() {
        return getNthNumberFromInfo(IDX_RECORDS_TO);
    }

    /**
     * Navigate to next page of records
     * @return whether it was possible to go to next page
     */
    public boolean goToNextPage() {
        return !isLastPage() && goToPageByClickingNthCell(IDX_NEXT_PAGE);
    }

    /**
     * Navigate to previous page of records
     * @return whether it was possible to go to previous page
     */
    public boolean goToPreviousPage() {
        return !isFirstPage() && goToPageByClickingNthCell(IDX_PREVIOUS_PAGE);
    }

    /**
     * Navigate to the first page of records
     * @return whether it was possible to go to the first page
     */
    public boolean goToFirstPage() {
        return !isFirstPage() && goToPageByClickingNthCell(IDX_FIRST_PAGE);
    }

    /**
     * Navigate to the last page of records
     * @return whether it was possible to go to the last page
     */
    public boolean goToLastPage() {
        return !isLastPage() && goToPageByClickingNthCell(IDX_LAST_PAGE);
    }

    /**
     * @return whether displayed page is the first (no previous page exists)
     */
    public boolean isFirstPage() {
        int currentFromNumber = getCurrentFromNumber();
        return currentFromNumber == 1 || currentFromNumber == 0; //0 for no records
    }

    /**
     * @return whether displayed page is the last (no next page exists)
     */
    public boolean isLastPage() {
        return getCurrentToNumber() == getTotalRecordsCount();
    }

    /**
     * @param idx zero-based index
     * @return idx-th TD element of root table or null if no such exists
     */
    private WebElement getNthCell(int idx) {
        ByJQuery selector = ByJQuery.selector("tr td");
        List<WebElement> tds = root.findElements(selector);
        if (tds.size() - 1 < idx) {
            return null;
        } else {
            return tds.get(idx);
        }
    }

    /**
     * @param idx zero-based index
     * @return whether navigate to the page by clicking on idx-th TD IMG element of root table
     */
    private boolean goToPageByClickingNthCell(int idx) {
        WebElement td = getNthCell(idx);
        WebElement img = td.findElement(By.tagName("img"));
        final int previousFromNumber = getCurrentFromNumber();
        img.click();

        return getCurrentFromNumber() != previousFromNumber;
    }

    /**
     * @param idx 1-based index
     * @return idx-th number in pager info TD element or -1 if can't be parsed
     */
    private int getNthNumberFromInfo(int idx) {
        WebElement pageInfoTd = getNthCell(IDX_PAGER_INFO);
        //workaround - #getText() doesn't seem to be reliable in this case
        String text = pageInfoTd.findElement(By.tagName("div")).getAttribute("innerHTML");

        if (text.matches(PAGER_INFO_PATTERN)) {
            return Integer.parseInt(text.replaceFirst(PAGER_INFO_PATTERN, "$" + idx));
        } else {
            return -1;
        }
    }
}
