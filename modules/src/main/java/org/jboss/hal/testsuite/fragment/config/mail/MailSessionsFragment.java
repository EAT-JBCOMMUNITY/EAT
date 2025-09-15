package org.jboss.hal.testsuite.fragment.config.mail;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;

/**
 * @author mkrajcov <mkrajcov@redhat.com>
 */
public class MailSessionsFragment extends ConfigFragment {

    private static final By CONTENT = ByJQuery.selector("." + PropUtils.get("page.content.rhs.class") + ":visible");
    private static final String DEBUG = "debug";
    private static final String FROM = "from";

    public MailSessionWizard addMailSession() {
        return getResourceManager().addResource(MailSessionWizard.class);
    }

    public void removeAndConfirm(String jndiName) {
        getResourceManager().removeResourceAndConfirm(jndiName);
    }

    public void editDebugAndSave(String jndiName, boolean value) {
        getResourceManager().selectByName(jndiName);
        Editor editor = edit();
        editor.checkbox(DEBUG, value);
        save();
    }

    public void editFromAndSave(String jndiName, String value) {
        getResourceManager().selectByName(jndiName);
        Editor editor = edit();
        editor.text(FROM, value);
        save();
    }
}
