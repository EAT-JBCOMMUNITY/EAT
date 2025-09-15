package org.jboss.hal.testsuite.fragment.rhaccess;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.wait.IsNotElementBuilder;
import org.jboss.hal.testsuite.fragment.BaseFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

/**
 * Created by mvelas on 3.11.14.
 */
public class RHAccessFragment extends BaseFragment {

    protected final By LOGIN_INPUT_SELECTOR = ByJQuery.selector("input#" +
            PropUtils.get("rhaccess.login.id"));
    protected final By PASSWORD_INPUT_SELECTOR = ByJQuery.selector("input#" +
            PropUtils.get("rhaccess.password.id"));
    protected final By LOGIN_SUBMIT_SELECTOR = ByJQuery.selector("button." +
            PropUtils.get("rhaccess.login.submit.class"));

    protected final String RH_ACCESS_LOGIN = PropUtils.get("rhaccess.login");
    protected final String RH_ACCESS_PASSWORD = PropUtils.get("rhaccess.password");

    private static final int LONG_SEARCH_TIMEOUT = 60;    // [s] (long query)

    public boolean isAuthenticationRequested() {
        try {
            root.findElement(LOGIN_INPUT_SELECTOR);
            root.findElement(PASSWORD_INPUT_SELECTOR);
            return true;
        } catch (NoSuchElementException e) {
            return tryToInvokeLogIn();
        }
    }

    protected boolean tryToInvokeLogIn() {
        try {
            WebElement logInButton = findLogInButton();
            logInButton.click();
            waitLongUntilElementIs(LOGIN_INPUT_SELECTOR).present();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected WebElement findLogInButton() {
        return root.findElement(By.partialLinkText("Log In"));
    }

    public void authenticate() throws Exception {
        root.findElement(LOGIN_INPUT_SELECTOR).sendKeys(RH_ACCESS_LOGIN);
        root.findElement(PASSWORD_INPUT_SELECTOR).sendKeys(RH_ACCESS_PASSWORD);
        root.findElement(LOGIN_SUBMIT_SELECTOR).click();
    }

    protected IsNotElementBuilder<Void> waitLongUntilElementIs(final By selector) {
        return waitLongUntilElementIs(selector, null);
    }

    protected IsNotElementBuilder<Void> waitLongUntilElementIs(final By selector, final SearchContext context) {
        if (context != null) {
            return Graphene.waitGui().withTimeout(LONG_SEARCH_TIMEOUT, TimeUnit.SECONDS).until().
                    element(context, selector).is();
        } else {
            return Graphene.waitGui().withTimeout(LONG_SEARCH_TIMEOUT, TimeUnit.SECONDS).until().
                    element(selector).is();
        }
    }

    protected IsNotElementBuilder<Void> waitLongUntilElementIs(final WebElement element) {
        return Graphene.waitGui().withTimeout(LONG_SEARCH_TIMEOUT, TimeUnit.SECONDS).until().
                element(element).is();
    }

    public String getSelectValue(final String selectName) {
        By selector = ByJQuery.selector("select[name=" + selectName + "]");
        waitLongUntilElementIs(selector, root).visible();
        WebElement selectElement = root.findElement(selector);
        Select select = new Select(selectElement);
        return select.getFirstSelectedOption().getText();
    }
}
