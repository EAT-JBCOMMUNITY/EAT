package org.jboss.hal.testsuite.page.config.elytron;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.config.elytron.permissionmapper.PermissionMappingResourceManager;
import org.jboss.hal.testsuite.fragment.config.elytron.permissionmapper.PermissionsTableFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.jboss.hal.testsuite.finder.FinderNames.SETTINGS;
import static org.jboss.hal.testsuite.page.config.elytron.ElytronPageConstants.ELYTRON_SUBSYTEM_LABEL;

public class MapperDecoderPage extends AbstractElytronConfigPage<MapperDecoderPage> {

    private static final String
        MAPPER_DECODER = "Mapper / Decoder",
        ROLE_MAPPER = "Role Mapper",
        PERMISSION_MAPPER = "Permission Mapper",
        DECODER = "Decoder";

    @Override
    public MapperDecoderPage navigateToApplication() {
        return navigateToRoleMapper();
    }

    public MapperDecoderPage navigateToRoleMapper () {
        return navigateToTab(ROLE_MAPPER);
    }

    public MapperDecoderPage navigateToPermissionMapper () {
        return navigateToTab(PERMISSION_MAPPER);
    }

    public MapperDecoderPage navigateToDecoder () {
        return navigateToTab(DECODER);
    }

    private MapperDecoderPage navigateToTab(String tabIdentifier) {
        getSubsystemNavigation(ELYTRON_SUBSYTEM_LABEL).step(SETTINGS, MAPPER_DECODER).openApplication(30);
        switchTab(tabIdentifier);
        return this;
    }

    public PermissionsTableFragment getPermissionsTable() {
        WebElement windowPanel = browser.findElement(By.className("default-window-content"));
        return Graphene.createPageFragment(PermissionsTableFragment.class, windowPanel);
    }

    public PermissionMappingResourceManager getPermissionMappingResourceManager() {
        By configAreaContentSelector = ByJQuery.selector("." + PropUtils.get("configarea.content.class") + ":visible");
        return Graphene.createPageFragment(PermissionMappingResourceManager.class, browser.findElement(configAreaContentSelector));
    }

    public ConfigFragment getWindowFragment() {
        WebElement contentWindow = browser.findElement(By.className("default-window-content"));
        return Graphene.createPageFragment(ConfigFragment.class, contentWindow);
    }

}
