package org.jboss.hal.testsuite.util;

import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Class which pulic methods return only {@link SuggestState} objects on which the verification ca be done
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 8/25/16.
 */
public class SuggestionChecker {

    private WebDriver browser;
    private ConfigFragment configFragment;
    private String suggestBoxInputLabel;
    private SuggestionResource suggestionResource;

    private SuggestionChecker(Builder builder) {
        this.browser = builder.browser;
        this.configFragment = builder.configFragment;
        this.suggestBoxInputLabel = builder.suggestBoxInputLabel;
        this.suggestionResource = builder.suggestionResource;
    }

    private void clearFilter() {
        WebElement textInput = getEditor().getText(suggestBoxInputLabel);
        textInput.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        textInput.sendKeys(Keys.DELETE);
    }

    private List<String> getSuggestedLabelsStrings() {
        try {
            return Console.withBrowser(browser).openedSuggestionBoxPopUp().getLabelValues();
        } catch (TimeoutException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Clears input field and sets its value to filterString
     * @param filterString value which will be set to input field
     * @return state of suggestion box
     */
    public SuggestState filterSuggestions(String filterString) {
        clearFilter();
        getEditor().text(suggestBoxInputLabel, filterString);
        return new SuggestState(filterString, getSuggestedLabelsStrings());
    }

    /**
     * Appends symbol to value of input box
     * @param symbol value which will be appended to input box
     * @return state of suggestion box
     */
    public SuggestState appendSymbolToInputField(char symbol) {
        WebElement inputField = getEditor().getText(suggestBoxInputLabel);
        inputField.click();
        inputField.sendKeys(String.valueOf(symbol));
        return new SuggestState(inputField.getAttribute("value"), getSuggestedLabelsStrings());
    }

    /**
     * Removes symbol to value of input box
     * @return state of suggestion box
     */
    public SuggestState removeSymbolFromInputField() {
        WebElement inputField = getEditor().getText(suggestBoxInputLabel);
        inputField.click();
        inputField.sendKeys(Keys.BACK_SPACE);
        return new SuggestState(inputField.getAttribute("value"), getSuggestedLabelsStrings());
    }

    private Editor getEditor() {
        Editor editor = configFragment.getEditor();
        if (!configFragment.isInEditMode()) {
            editor = configFragment.edit();
        }
        return editor;
    }

    public class SuggestState {

        private final String inputFieldValue;
        private final List<String> valuesLabels;

        private SuggestState(String inputFieldValue, List<String> valuesLabels) {
            this.inputFieldValue = inputFieldValue;
            if (valuesLabels == null) {
                throw new IllegalArgumentException("List of suggested items cannot be null!");
            }
            this.valuesLabels = valuesLabels;
        }

        /**
         * Verifies that only relevant items were suggested
         * @throws IOException when there was an error during reading real values from model
         */
        public void verifyOnlyRelevantSuggestionWereSuggested() throws IOException {
            verifyOnlyRelevantSuggestionWereSuggested(null);
        }

        /**
         * Verifies that only relevant items were suggested
         * @param message Error message to append
         * @throws IOException when there was an error during reading real values from model
         */
        public void verifyOnlyRelevantSuggestionWereSuggested(String message) throws IOException {
            List<String> filteredSuggestionFromResource = suggestionResource.readSuggestions().stream()
                    .filter(suggestion -> suggestion.matches(".*(" + Pattern.quote(inputFieldValue) + ").*"))
                    .collect(Collectors.toList());

            Collections.sort(filteredSuggestionFromResource);
            Collections.sort(valuesLabels);

            Assert.assertEquals("Non relevant suggestions appeared or some of the are missing, input value: '" + inputFieldValue + "'. " +
                            "Size expected: '" + filteredSuggestionFromResource.size() + "' vs. size actual: '" + valuesLabels.size() + "'. " +
                            (message == null ? "" : message),
                    filteredSuggestionFromResource,
                    valuesLabels);
        }

        /**
         * Verifies that none suggestions were suggested
         */
        public void verifyNoneSuggestionWereSuggested() {
            Assert.assertTrue("There were some suggested items! Input value: '" + inputFieldValue + "'.", valuesLabels.isEmpty());
        }
    }

    public static class Builder {
        private WebDriver browser;
        private ConfigFragment configFragment;
        private String suggestBoxInputLabel;
        private SuggestionResource suggestionResource;

        /**
         * Sets config fragment
         */
        public Builder configFragment(ConfigFragment configFragment) {
            this.configFragment = configFragment;
            return this;
        }

        /**
         * Sets label of suggestion box
         */
        public Builder suggestBoxInputLabel(String suggestBoxInputLabel) {
            this.suggestBoxInputLabel = suggestBoxInputLabel;
            return this;
        }

        /**
         * Sets {@link SuggestionResource} from which the suggestions for comparation will be read
         */
        public Builder suggestionResource(SuggestionResource suggestionResource) {
            this.suggestionResource = suggestionResource;
            return this;
        }

        /**
         * Sets {@link WebDriver} used for obtaining opened {@link org.jboss.hal.testsuite.fragment.shared.modal.suggestbox.SuggestBoxPopUp}
         */
        public Builder browser(WebDriver browser) {
            this.browser = browser;
            return this;
        }

        public SuggestionChecker build() {
            return new SuggestionChecker(this);
        }
    }

}
