package org.jboss.hal.testsuite.fragment.config.web.servlet;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class VirtualServersWizard extends WizardWindow {
    private static final String NAME = "name";
    private static final String ALIAS = "alias";
    private static final String DEFAULT_MODULE = "defaultWebModule";

    private static final By ALIAS_TEXTAREA = ByJQuery.selector("textarea");

    public VirtualServersWizard name(String value) {
        getEditor().text(NAME, value);
        return this;
    }

    public VirtualServersWizard defaultModule(String value) {
        getEditor().text(DEFAULT_MODULE, value);
        return this;
    }

    public VirtualServersWizard alias(String value) {
        root.findElement(ALIAS_TEXTAREA).sendKeys(value);
        return this;
    }
}
