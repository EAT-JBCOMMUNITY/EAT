package org.jboss.hal.testsuite.page.config;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.finder.Application;
import org.jboss.hal.testsuite.finder.FinderNames;
import org.jboss.hal.testsuite.finder.FinderNavigation;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.security.SecurityDomainAddWizard;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.page.Navigatable;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 16.10.15.
 */
public class SecurityPage extends ConfigurationPage implements Navigatable {

    private FinderNavigation navigation;
    private static final String SECURITY_DOMAIN = "Security Domain";

    @Override
    public void navigate() {
        if (ConfigUtils.isDomain()) {
            navigation = new FinderNavigation(browser, DomainConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.PROFILES)
                    .step(FinderNames.PROFILE, ConfigUtils.getDefaultProfile());
        } else {
            navigation = new FinderNavigation(browser, StandaloneConfigEntryPoint.class)
                    .step(FinderNames.CONFIGURATION, FinderNames.SUBSYSTEMS);
        }
        navigation.step(FinderNames.SUBSYSTEM, "Security")
                .step(SECURITY_DOMAIN);
        navigation.selectColumn(true);
        Console.withBrowser(browser).dismissReloadRequiredWindowIfPresent();
        Console.withBrowser(browser).waitUntilLoaded();
    }

    public void viewJBossEJBPolicy() {
        viewSecurityDomain("jboss-ejb-policy");
    }

    public void viewJBossWebPolicy() {
        viewSecurityDomain("jboss-web-policy");
    }

    public void viewOther() {
        viewSecurityDomain("other");
    }

    public SecurityDomainAddWizard addSecurityDomain() {
        navigation.resetNavigation().step(SECURITY_DOMAIN).selectColumn().invoke(FinderNames.ADD);
        return Console.withBrowser(browser).openedWindow(SecurityDomainAddWizard.class);
    }

    public void viewSecurityDomain(String name) {
        navigation.resetNavigation().step(SECURITY_DOMAIN, name).selectRow().invoke(FinderNames.VIEW);
        Application.waitUntilVisible();
    }

    public void switchToAuthentication() {
        switchSubTab("Authentication");
    }

    public void switchToAuthorization() {
        switchSubTab("Authorization");
    }

    public void switchToAudit() {
        switchSubTab("Audit");
    }

    public void switchToACL() {
        switchSubTab("ACL");
    }

    public void switchToMapping() {
        switchSubTab("Mapping");
    }

    public void switchToIdentityTrust() {
        switchSubTab("Identity Trust");
    }

    public void addAuthenticationModule(String name, String code) {
        WizardWindow wizard = getResourceManager().addResource();
        Editor editor = wizard.getEditor();
        editor.text("name", name);
        editor.text("code", code);
        editor.select("flag", "optional");
        wizard.finish();
    }

    public void addACLModule(String name, String code) {
        WizardWindow wizard = getResourceManager().addResource();
        Editor editor = wizard.getEditor();
        editor.text("name", name);
        editor.text("code", code);
        editor.select("flag", "optional");
        wizard.finish();
    }

    public void addAuditModule(String name, String code) {
        WizardWindow wizard = getResourceManager().addResource();
        Editor editor = wizard.getEditor();
        editor.text("name", name);
        editor.text("code", code);
        wizard.finish();
    }

    public void addTrustModule(String name, String code) {
        WizardWindow wizard = getResourceManager().addResource();
        Editor editor = wizard.getEditor();
        editor.text("name", name);
        editor.text("code", code);
        editor.select("flag", "optional");
        wizard.finish();
    }

    public boolean editTextAndSave(String identifier, String value) {
        ConfigFragment configFragment = getConfigFragment();
        Editor editor = configFragment.edit();
        editor.text(identifier, value);
        return configFragment.save();
    }

    public boolean selectOptionAndSave(String identifier, String value) {
        ConfigFragment configFragment = getConfigFragment();
        configFragment.edit().select(identifier, value);
        return configFragment.save();
    }

    public Boolean editCheckboxAndSave(String identifier, Boolean value) {
        ConfigFragment configFragment = getConfigFragment();
        configFragment.edit().checkbox(identifier, value);
        return configFragment.save();
    }

    public Boolean isErrorShownInForm() {
        By selector = ByJQuery.selector("div.form-item-error-desc:visible");
        return isElementVisible(selector);
    }

    public void removeSecurityDomain(String name) {
        try {
            navigation.resetNavigation().step(SECURITY_DOMAIN, name).selectRow().invoke("Remove");
        } catch (TimeoutException ignored) {
        }
        Console.withBrowser(browser).openedWindow(ConfirmationWindow.class).confirmAndDismissReloadRequiredMessage();
    }

    public boolean isDomainPresent(String name) {
        return navigation.resetNavigation().step(SECURITY_DOMAIN, name).selectColumn().rowIsPresent(name, 60L, navigation);
    }

    public void selectModule(String name) {
        getResourceManager().selectByName(name);
    }

    public void addMappingModule(String name, String code, String type) {
        WizardWindow wizard = getResourceManager().addResource();
        Editor editor = wizard.getEditor();
        editor.text("name", name);
        editor.text("type", type);
        editor.text("code", code);
        wizard.finish();
    }
}
