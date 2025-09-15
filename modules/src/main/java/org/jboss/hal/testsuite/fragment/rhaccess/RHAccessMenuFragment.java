package org.jboss.hal.testsuite.fragment.rhaccess;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.PopUpFragment;
import org.jboss.hal.testsuite.util.Library;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by mvelas on 3.11.14.
 */
public class RHAccessMenuFragment extends PopUpFragment {

    protected enum Entry {
        SEARCH_PORTAL(0),
        OPEN_CASE(1),
        MODIFY_CASE(2);

        private final int index;
        Entry(int index) {
            this.index = index;
        }
    }

    protected static final String ENTRY_ITEM_CLASS = PropUtils.get("rhaccess.entry.class");
    protected static final long RH_ACCESS_TIMEOUT = 5; // [s]

    protected WebElement openEntry(final Entry entry) {
        List<WebElement> menuEntries = root.findElements(ByJQuery.selector("div." + ENTRY_ITEM_CLASS));
        menuEntries.get(entry.index).click();

        // TODO - this wait must be implemented in more fancy way
        Library.letsSleep(RH_ACCESS_TIMEOUT * 1000);

        WebElement iFrame = browser.findElement(ByJQuery.selector("iframe.gwt-Frame"));
        WebElement content = browser.switchTo().frame(iFrame).findElement(By.tagName("body"));
        return content;
    }

    public RHAccessSearchFragment openSearchPortal() {
        return Graphene.createPageFragment(
                RHAccessSearchFragment.class, openEntry(Entry.SEARCH_PORTAL));
    }

    public RHAccessOpenTicketFragment openOpenCase() {
        return Graphene.createPageFragment(
                RHAccessOpenTicketFragment.class, openEntry(Entry.OPEN_CASE));
    }

    public RHAccessModifyTicketFragment openModifyCase() {
        return Graphene.createPageFragment(
                RHAccessModifyTicketFragment.class, openEntry(Entry.MODIFY_CASE));
    }
}
