package org.jboss.hal.testsuite.fragment.config.elytron.permissionmapper;

import com.google.common.base.Predicate;
import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.WindowFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.ConfirmationWindow;
import org.jboss.hal.testsuite.fragment.shared.table.ResourceTableRowFragment;
import org.jboss.hal.testsuite.fragment.shared.util.ResourceManager;
import org.jboss.hal.testsuite.util.Console;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class PermissionMappingResourceManager extends ResourceManager {

    private static final String PRINCIPALS = "Principals";

    public void viewByPrincipals(String principals) {
        selectByPrincipals(principals).view();
    }

    public ResourceTableRowFragment selectByPrincipals(String principals) {
        return getResourceTable().selectRowByText(1, principals);
    }

    public <T extends WindowFragment> T removeResourceByPrincipals(String principals, Class<T> clazz) {
        selectByPrincipals(principals);
        invokeRemove();
        return Console.withBrowser(browser).openedWindow(clazz);
    }

    public boolean isResourcePresentByPrincipals(String principals) {
        try {
            Graphene.waitModel(browser)
                    .pollingEvery(100, TimeUnit.MILLISECONDS)
                    .withTimeout(2000, TimeUnit.MILLISECONDS)
                    .until((WebDriver input) -> selectByPrincipals(principals));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public ConfirmationWindow removeResourceByPrincipals(String principals) {
        return removeResourceByPrincipals(principals, ConfirmationWindow.class);
    }
}
