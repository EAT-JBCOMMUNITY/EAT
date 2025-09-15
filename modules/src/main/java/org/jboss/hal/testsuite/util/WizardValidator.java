package org.jboss.hal.testsuite.util;

import com.google.common.collect.Sets;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.jboss.hal.testsuite.util.configurationchanges.WizardInput;
import org.junit.Assert;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/***
 * Class representing a validation abstraction over wizard windows
 * @param <T> Wizard window type
 */
public abstract class WizardValidator<T extends WizardWindow> {

    protected Set<WizardInput<T>> mandatoryInputs;
    protected ConfigFragment windowFragment;

    public WizardValidator(Collection<WizardInput<T>> mandatoryInputs, ConfigFragment fragment) {
        this.mandatoryInputs = new HashSet<>(mandatoryInputs);
        this.windowFragment = fragment;
    }

    /**
     * Tests all possible invalid combinations for required fields in wizard
     * @param wizard to be tested
     */
    public abstract void testInvalidCombinationsAndAssert(T wizard);

    /**
     * Fills provided fields in wizard and clears rest of them
     * @param presentInputs to be filled
     * @param wizard containing inputs
     */
    protected void fillFieldsAndAssert(Collection<WizardInput<T>> presentInputs, T wizard) {
        StringBuilder message = new StringBuilder("Page should be showing validation error for following fields: ");
        presentInputs.forEach(wizardInput -> wizardInput.fill(wizard));
        Set<WizardInput<T>> remainingInputs = Sets.difference(mandatoryInputs, new HashSet<>(presentInputs));
        remainingInputs.forEach(wizardInput -> wizardInput.clear(wizard));
        String missingFields = remainingInputs.stream().map(WizardInput::getName).collect(Collectors.joining(","));
        message.append(missingFields);
        wizard.saveAndDismissReloadRequiredWindowWithState().assertWindowOpen();
        Assert.assertTrue(message.toString(), windowFragment.isErrorShownInForm());
    }
}
