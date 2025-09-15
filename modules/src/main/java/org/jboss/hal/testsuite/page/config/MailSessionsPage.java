package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.Column;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.finder.Row;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.mail.MailServerFragment;
import org.jboss.hal.testsuite.fragment.config.mail.MailSessionWizard;
import org.jboss.hal.testsuite.fragment.config.mail.MailSessionsFragment;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
@Location("#profile")
public class MailSessionsPage extends ConfigurationPage {
    private static final Logger log = LoggerFactory.getLogger(MailSessionsPage.class);

    private static final String MAIL_SESSION_LABEL = "Mail Session";
    private static final String ATTRIBUTES = "Attributes";

    private static final By BACK_ANCHOR = ByJQuery.selector("a:contains('Back')");
    private static final By CONTENT = ByJQuery.selector("." + PropUtils.get("page.content.gwt-layoutpanel") + ":visible");
    private static final By SESSIONCONTENT = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");

    public void navigate() {
        getFinderNavigation().selectColumn();
        Application.waitUntilVisible();
    }

    private FinderNavigation getFinderNavigation() {
        return new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS)
                .step(FinderNames.SUBSYSTEM, "Mail");
    }

    public MailSessionsFragment getMailSessions() {
        backIfAvailable();
        WebElement root = getContentRoot().findElement(CONTENT);
        return Graphene.createPageFragment(MailSessionsFragment.class, root);
    }

    public MailServerFragment getMailServers(String jndiName) {
        backIfAvailable();
        getResourceManager().viewByName(jndiName);
        WebElement fragmentRoot = getContentRoot().findElement(CONTENT);
        return Graphene.createPageFragment(MailServerFragment.class, fragmentRoot);
    }

    public MailServerFragment getSesionsServers() {
        WebElement fragmentRoot = getContentRoot().findElement(SESSIONCONTENT);
        return Graphene.createPageFragment(MailServerFragment.class, fragmentRoot);
    }

    public void viewMailSession(String sessionName) {
        invokeOperationOnMailSession(FinderNames.VIEW, sessionName);
        Application.waitUntilVisible();
    }

    public void removeMailSession(String sessionName) {
        invokeOperationOnMailSession(FinderNames.REMOVE, sessionName);
    }

    public void viewMailSessionAtributesPopUp(String sessionName) {
        invokeOperationOnMailSession("Attributes", sessionName);
    }

    public boolean addMailSession(String sessionName, String mailName) {
        invokeOperationOnMailSessionColumn(FinderNames.ADD);
        MailSessionWizard wizard = Console.withBrowser(browser).openedWizard(MailSessionWizard.class);

        return wizard.jndiName(sessionName)
                .name(mailName)
                .finish();
    }

    private void invokeOperationOnMailSession(String operationName, String sessionName) {
        Row row = getFinderNavigation().step(MAIL_SESSION_LABEL, sessionName)
                .selectRow();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        row.invoke(operationName);
    }

    private void invokeOperationOnMailSessionColumn(String action) {
        Column column = getFinderNavigation().step(MAIL_SESSION_LABEL).selectColumn();
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        column.invoke(action);
    }

    public ConfigAreaFragment getConfig() {
        By selector = ByJQuery.selector("table.default-tabpanel.master_detail-detail");
        WebElement root = browser.findElement(selector);
        return Graphene.createPageFragment(ConfigAreaFragment.class, root);
    }

    public void switchToCredentialReferenceTab() {
        getConfig().clickTabByLabel("Credential Reference");
    }

    public ConfigFragment getWindowFragment() {
        WebElement windowElement = browser.findElement(By.className("default-window-content"));
        return Graphene.createPageFragment(ConfigFragment.class, windowElement);
    }

    private void backIfAvailable() {
        try {
            WebElement back = getContentRoot().findElement(BACK_ANCHOR);
            if (back.isDisplayed()) {
                back.click();
            }
        } catch (NoSuchElementException e) {
            log.debug("No back anchor found");
        }
    }
}
