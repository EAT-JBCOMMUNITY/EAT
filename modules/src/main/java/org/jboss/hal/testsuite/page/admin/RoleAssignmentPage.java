package org.jboss.hal.testsuite.page.admin;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.hal.testsuite.cli.TimeoutException;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.page.BasePage;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * @author jcechace
 */
@Location("#rbac")
public class RoleAssignmentPage extends BasePage {
    private FinderNavigation navigation;

    public void createGroup(String name, String realm, String role) {
        navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .step(FinderNames.BROWSE_BY, "Groups")
                .step("Group");
        navigation.selectColumn().invoke("Add");

        getWindowFragment().getEditor().text("name", name);
        getWindowFragment().getEditor().text("realm", realm);
        getWindowFragment().getEditor().select("role", role);
        getWindowFragment().clickButton("Save");
    }

    public void selectMember(String name, String realm) {
        WebElement member =  browser.findElement(ByJQuery.selector("[title*=\'" + name + " (at) " + realm + "\']:visible"));
        member.click();
        getWindowFragment().clickButton("Save");
    }

    public void selectRole(String role) {
        WebElement member =  browser.findElement(ByJQuery.selector("[title*=\'" + role + "\']:visible"));
        member.click();
        getWindowFragment().clickButton("Save");
    }

    public void addInclude(String name, String realm, String role) {
       /* navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .refreshPage(true)
                .addAddress(FinderNames.BROWSE_BY, "Groups")
                .addAddress("Group",name + "@" + realm)
                .addAddress("Assignment", "Include")
                .addAddress("Role");
        navigation.selectColumn(); //.invoke("Add");
        WebElement sndAdd = browser.findElement(ByJQuery.selector("div.btn,.primary:contains(\'Add\'):eq(1)"));
        sndAdd.click();
        selectRole(role); // try to fix */
        navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .step(FinderNames.BROWSE_BY, "Roles")
                .step("Role", role)
                .step("Membership", "Include")
                .step("Member");

        navigation.selectColumn().invoke("Add");
        selectMember(name, realm);

    }

    public void removeInclude(String name, String realm, String role) {
        navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .step(FinderNames.BROWSE_BY, "Roles")
                .step("Role", role)
                .step("Membership", "Include")
                .step("Member", name + "@" + realm);

        navigation.selectRow().invoke("Remove");
        try {
            Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
        } catch (TimeoutException ignored) {
        }
    }

    public void removeGroup(String name, String realm) {
        navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .step(FinderNames.BROWSE_BY, "Groups")
                .step("Group", name + "@" + realm);
        navigation.selectRow().invoke("Remove");
        try {
            Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
        } catch (TimeoutException ignored) {
        }
    }

    public void addUser(String name, String realm, String role) {
        navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .step(FinderNames.BROWSE_BY, "Users")
                .step("User");
        navigation.selectColumn().invoke("Add");

        getWindowFragment().getEditor().text("name", name);
        getWindowFragment().getEditor().text("realm", realm);
        getWindowFragment().getEditor().select("role", role);
        getWindowFragment().clickButton("Save");

       /* navigation.resetNavigation()
                .addAddress(FinderNames.BROWSE_BY, "Roles")
                .addAddress("Role", role)
                .addAddress("Membership", "Include")
                .addAddress("Member");

        navigation.selectColumn().invoke("Add");
        selectMember(name, realm);*/
    }

    public void removeUser(String name, String realm) {
        navigation = new FinderNavigation(browser, RoleAssignmentPage.class)
                .step(FinderNames.BROWSE_BY, "Users")
                .step("User", name + "@" + realm);
        navigation.selectRow().invoke("Remove");
        try {
            Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirm();
        } catch (TimeoutException ignored) {
        }
    }

    public ConfigFragment getWindowFragment() {
        WebElement editPanel = browser.findElement(By.className("default-window-content"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }
}
