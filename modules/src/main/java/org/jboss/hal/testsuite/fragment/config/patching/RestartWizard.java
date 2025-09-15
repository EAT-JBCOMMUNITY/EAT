package org.jboss.hal.testsuite.fragment.config.patching;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.util.PropUtils;

import java.util.concurrent.TimeUnit;

/**
 * Created by mvelas on 22.4.2014.
 */
public class RestartWizard extends WizardWindow {
    public void confirm() {
        String label = PropUtils.get("modals.restart.wizard.confirm.label");
        clickButton(label);

        Graphene.waitGui().withTimeout(30, TimeUnit.SECONDS).until().element(root).is().not().present();
        closed = true;
    }
}
