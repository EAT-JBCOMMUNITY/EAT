package org.jboss.hal.testsuite.page;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.arquillian.graphene.findby.FindByJQuery;
import org.jboss.hal.testsuite.cli.Library;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.AlertFragment;
import org.jboss.hal.testsuite.fragment.ConfigAreaFragment;
import org.jboss.hal.testsuite.fragment.MessageListEntry;
import org.jboss.hal.testsuite.fragment.NavigationFragment;
import org.jboss.hal.testsuite.fragment.NotificationCenterFragment;
import org.jboss.hal.testsuite.fragment.config.navigation.ViewNavigation;
import org.jboss.hal.testsuite.fragment.rhaccess.RHAccessHeaderFragment;
import org.jboss.hal.testsuite.fragment.shared.layout.Footer;
import org.jboss.hal.testsuite.fragment.shared.layout.HeaderTabs;
import org.jboss.hal.testsuite.fragment.shared.table.InfoTable;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.util.Console;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Created by jcechace on 15/02/14.
 */
public abstract class BasePage {

    @Drone
    protected WebDriver browser;

    @FindByJQuery("#header-links-section")
    private HeaderTabs headerNavigation;

    private NavigationFragment navigation;
    private NotificationCenterFragment notification;

    @FindBy(className = "footer-panel")
    private Footer footer;


    // SELECTORS
    private static By INFO_TABLE_SELECTOR = By.className(PropUtils.get("infotable.class"));

    private static By INFO_TABLE_2ND_SELECTOR = ByJQuery.selector("." + PropUtils.get("infotable.class") + ":eq(2)");

    private static By ROOT_SELECTOR = ByJQuery.selector("#" + PropUtils
            .get("page.content.id") + ":visible");
    private static By PARENT_ROOT_SELECTOR = ByJQuery.selector("." + PropUtils
            .get("page.content.parent.class") + ":visible");

    public HeaderTabs getHeaderNavigation() {
        return headerNavigation;
    }

    public NavigationFragment getNavigation() {
        if (navigation != null) {
            return navigation;
        }

        By selector = ByJQuery.selector("#main-content-area div [role='navigation']");
        WebElement navigationRoot = browser.findElement(selector);
        navigation = Graphene.createPageFragment(NavigationFragment.class, navigationRoot);

        return navigation;
    }

    public Footer getFooter() {
        return footer;
    }


    /**
     * Finds button in content area based on identifier
     *
     * @param identifier id or label
     * @return the button element
     */
    public WebElement getButton(String identifier) {
        By selector = ByJQuery.selector("" +
                        "button#" + identifier + ":visible," +
                        "button:contains('" + identifier + "'):visible," +
                        "div.btn:contains('" + identifier + "'):visible"
        );
        WebElement button = getContentRoot().findElement(selector);

        return button;
    }

    /**
     * Click on button with given identifier
     *
     * @param label either an id or label used to find the button
     */
    public void clickButton(String label) {
        WebElement button = getButton(label);

        button.click();
    }

    public void switchTab(String identifier) {
        WebElement tab = getTab(identifier);
        tab.click();
        waitUntilTabActive(identifier);
    }

    public void switchSubTab(String identifier) {
        WebElement subTab = getSubTab(identifier);
        subTab.click();
        Graphene.waitGui().until().element(subTab).attribute("class").contains("link-bar-active");
        Console.withBrowser(browser).waitUntilLoaded();
    }

    public void switchSubTabByExactText(String identifier) {
        List<WebElement> subTabs = getSubTabs(identifier);
        for (WebElement subTab : subTabs) {
            if (subTab.getText().equals(identifier)) {
                subTab.click();
                Graphene.waitGui().until().element(subTab).attribute("class").contains("link-bar-active");
                Console.withBrowser(browser).waitUntilLoaded();
            }
        }
    }

    private WebElement getSubTab(String identifier) {
        By selector = ByJQuery.selector("div.inline-link:contains('" + identifier + "'):visible");
        Graphene.waitGui().until().element(selector).is().visible();
        return browser.findElement(selector);
    }

    private List<WebElement> getSubTabs(String identifier) {
        By selector = ByJQuery.selector("div.inline-link:contains('" + identifier + "'):visible");
        return browser.findElements(selector);
    }


    // TODO: tab management refactoring is needed

    protected WebElement getTab(String identifier) {
        String idSelector = ".gwt-TabLayoutPanelTab[id$='" + identifier + "']";
        String labelSelector = ".gwt-TabLayoutPanelTab:contains('" + identifier + "')";
        By selector = ByJQuery.selector(idSelector + ", " + labelSelector);

        WebElement tab = browser.findElement(selector);
        return tab;
    }

    protected void waitUntilTabIsPresent(String identifier) {
        String idSelector = ".gwt-TabLayoutPanelTab[id$='" + identifier + "']";
        String labelSelector = ".gwt-TabLayoutPanelTab:contains('" + identifier + "')";
        By selector = ByJQuery.selector(idSelector + ", " + labelSelector);

        Graphene.waitGui().until().element(selector).is().present();
    }

    protected void waitUntilTabIsNotPresent(String identifier) {
        String idSelector = ".gwt-TabLayoutPanelTab[id$='" + identifier + "']";
        String labelSelector = ".gwt-TabLayoutPanelTab:contains('" + identifier + "')";
        By selector = ByJQuery.selector(idSelector + ", " + labelSelector);

        Graphene.waitGui().until().element(selector).is().not().present();
    }


    protected void waitUntilTabActive(String identifier) {
        String idSelectorSelected = ".gwt-TabLayoutPanelTab-selected[id$='" + identifier + "']";
        String labelSelectorSelected = ".gwt-TabLayoutPanelTab-selected:contains('" + identifier + "')";

        By selectorCurrent = ByJQuery.selector(idSelectorSelected + ", " + labelSelectorSelected);

        Graphene.waitAjax().until().element(selectorCurrent).is().present();
    }

    public ViewNavigation getViewNavigation() {
        By selector = By.className(PropUtils.get("page.content.viewnav.class"));
        WebElement root = getContentRoot().findElement(selector);

        ViewNavigation viewNavigation = Graphene.createPageFragment(ViewNavigation.class, root);

        return viewNavigation;
    }

    /**
     * Switch the view tab on top of conent area (see for example messaging configuration pages)
     *
     * @param identifier
     */
    public void switchView(String identifier) {
        ViewNavigation viewNavigation = getViewNavigation();
        viewNavigation.switchView(identifier);
    }

    /**
     * Find the content root of current page
     *
     * @returnthe content root
     */
    public WebElement getContentRoot() {
        WebElement contentRoot;
        try {
            contentRoot = browser.findElement(ROOT_SELECTOR);
        } catch (NoSuchElementException e) {
            contentRoot = browser.findElement(PARENT_ROOT_SELECTOR);
        }

        return contentRoot;
    }

    /**
     * @return list of simple messages
     */
    public List<MessageListEntry> getMessages() {
        return this.getNotificationArea().openMessagesList().getMessagesAsData();
    }

    /**
     * @return whether there is any message in message list
     */
    public boolean hasMessages() {
        return this.getNotificationArea().openMessagesList().hasMessages();
    }

    /**
     * Clear messages in notification area.
     */
    public void clearMessages() {
        this.getNotificationArea().openMessagesList().clear();
    }

    /**
     * @return fragment for notification area - ususally element containg "messages: X" displayed on top/right
     */
    public NotificationCenterFragment getNotificationArea() {
        if (notification == null) {
            WebElement e = browser.findElement(By.className(NotificationCenterFragment.CLASS_ROOT));
            notification = Graphene.createPageFragment(NotificationCenterFragment.class, e);
        }
        return notification;
    }

    /**
     * @return returns area display message as popup element in center of page, Area is closed after few seconds automatically usually.
     */
    public AlertFragment getAlertArea() {
        By alertSelector = By.cssSelector(AlertFragment.ROOT_SELECTOR);

        Graphene.waitGui().until().element(alertSelector).is().visible();
        WebElement element = browser.findElement(alertSelector);

        AlertFragment alert = Graphene.createPageFragment(AlertFragment.class, element);

        return alert;
    }


    public RHAccessHeaderFragment getRedHatAccessArea() {
        List<WebElement> candidates = browser.findElements(By.className(RHAccessHeaderFragment.CLASS_ROOT));
        for (WebElement candidate : candidates) {
            if (candidate.getText().contains("Red Hat Access")) {
                return Graphene.createPageFragment(RHAccessHeaderFragment.class, candidate);
            }
        }
        return null;
    }

    /**
     * @return fragment for information table
     */
    public InfoTable getInfoTable() {
        WebElement root = getContentRoot().findElement(INFO_TABLE_SELECTOR);
        InfoTable table = Graphene.createPageFragment(InfoTable.class, root);

        return table;
    }

    /**
     * @return fragment for 2nd information table on page
     */
    public InfoTable get2ndInfoTable() {
        WebElement root = getContentRoot().findElement(INFO_TABLE_2ND_SELECTOR);
        InfoTable table = Graphene.createPageFragment(InfoTable.class, root);

        return table;
    }

    public ResourceManager getResourceManager() {
        return Graphene.createPageFragment(ResourceManager.class, getContentRoot());
    }

    /**
     * Navigates to page url
     */
    public void navigate() {
        browser.navigate().refresh();
        Graphene.goTo(this.getClass());
        Console.withBrowser(browser).waitUntilLoaded();
    }

    /**
     * select item in left menu navigation
     * @param label
     * @return
     */
    public BasePage selectMenu(String label) {
        String cellSelectedClass = PropUtils.get("table.cell.selected.class");
        By selector = getMenuEqualsSelector(label);
        getContentRoot().findElement(selector).click();
        Graphene.waitModel().until().element(selector).attribute("class").contains(cellSelectedClass);
        Library.letsSleep(1000);
        return this;
    }

    public ConfigFragment getConfigFragment() {
        WebElement editPanel = browser.findElement(By.className("default-tabpanel"));
        return  Graphene.createPageFragment(ConfigFragment.class, editPanel);
    }

    protected By getMenuEqualsSelector(String label) {
        return getMenuSelector(" and text()='" + label + "']]");
    }

    protected By getMenuContainsSelector(String label) {
        return getMenuSelector(" and contains(.,'" + label + "')]]");
    }

    private By getMenuSelector(String xpathSuffix) {
        String cellClass = PropUtils.get("table.cell.class");
        return By.ByXPath.xpath("//td[contains(@class,'" + cellClass + "') and descendant::div[@class='navigation-column-item'" + xpathSuffix);
    }

    /**
     * Clicks on "Back" link in the top link bar.
     */
    public void clickOnBackLink() {
        WebElement back = browser.findElement(By.className(PropUtils.get("icon.backarrows.class")));
        back.click();
        Console.withBrowser(browser).waitUntilLoaded();
    }

    /**
     * Returns the ConfigArea portion of page as given implementation.
     * Not reliable - you might need to override this method.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends ConfigAreaFragment> T getConfig(Class<T> clazz) {

        WebElement configRoot = null;
        try {
            String cssClass = PropUtils.get("configarea.class");
            By selector = ByJQuery.selector("." + cssClass + ":visible");
            configRoot = getContentRoot().findElement(selector);
        } catch (NoSuchElementException e) { // TODO: this part should be removed once ensured the ID is everywhere
            List<WebElement> elements = getContentRoot().findElements(getConfigSelector());

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    configRoot = element;
                }
            }
        }

        T config = Graphene.createPageFragment(clazz, configRoot);
        return config;
    }

    private By getConfigSelector() {
        String selectionLabel =
                ".//div[contains(@class, 'content-group-label') and (contains(text(), 'Selection') or contains(text(), 'Details'))]";
        By selector = By.xpath(selectionLabel + "/following::*[contains(@class, 'rhs-content-panel')]");

        return selector;
    }

    /**
     * Returns the default implementation of ConfigArea portion of page.
     * Not reliable - you might need to override this method.
     *
     * @return
     */
    public ConfigAreaFragment getConfig() {
        return getConfig(ConfigAreaFragment.class);
    }

}
