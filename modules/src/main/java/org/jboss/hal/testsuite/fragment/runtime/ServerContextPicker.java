package org.jboss.hal.testsuite.fragment.runtime;

import java.util.ArrayList;
import java.util.List;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.PopUpFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Created by mvelas on 11.3.2014.
 *
 * Class implements pop-up fragment for switching host/server context in domain mode
 */
public class ServerContextPicker extends PopUpFragment {
    public static final ByJQuery BUTTON_SELECTOR = ByJQuery.selector(PropUtils.get("runtime.server.switch.button"));

    /**
     * @param newContext new context (server/host) to set
     */
    public void changeContext(String newContext) {
        String evenSelectorString = "div.cellListEvenItem:contains('" + newContext + "')";
        String oddSelectorString = "div.cellListOddItem:contains('" + newContext + "')";
        By selector = ByJQuery.selector(evenSelectorString + ", " + oddSelectorString);
        root.findElement(selector).click();
    }

    private List<String> getCurrentContext() {
        ByJQuery serverSelector = ByJQuery.selector("div.cellListSelectedItem");
        List<String> context = new ArrayList<String>();
        for (WebElement elem : root.findElements(serverSelector)) {
            context.add(elem.getText().trim());
        }
        return context;
    }

    public String getCurrentHostName() {
        return getCurrentContext().get(0);
    }

    public String getCurrentServerName() {
        return getCurrentContext().get(1);
    }
}
