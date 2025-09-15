package org.jboss.hal.testsuite.fragment.shared.modal.suggestbox;

import org.jboss.arquillian.graphene.findby.ByJQuery;
import org.jboss.hal.testsuite.fragment.PopUpFragment;
import org.jboss.hal.testsuite.util.PropUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 8/22/16.
 *
 *  This class represent a suggest box pop up which appears when typing some text into input with css class
 *  gwt-SuggestBox.
 */
public class SuggestBoxPopUp extends PopUpFragment {

    private static final String TABLE_HTML_NAME = "table";

    /**
     * Returns list of suggested items wrapped inside {@link SuggestBoxPopUpValue}
     * @return list of suggested items
     */
    public List<SuggestBoxPopUpValue> getValues() {
        By selector = By.cssSelector("tr td");
        List<WebElement> elements = getTableWithSuggestedItems().findElements(selector);
        return elements.stream()
                .map(SuggestBoxPopUpValue::new)
                .collect(Collectors.toList());
    }

    /**
     * Return list of suggested labels from opened pop up
     * @return list of strings of suggested items
     */
    public List<String> getLabelValues() {
        return getValues().stream()
                .map(SuggestBoxPopUpValue::toString)
                .collect(Collectors.toList());
    }

    /**
     * Selects one suggestion based on given comparator.
     * @param comparator Function which accepts string as an argument and returns boolean. This function will be used to
     *                   filter suggested values.
     */
    public void selectSuggestionByLabel(Function<String, Boolean> comparator) {
        Optional<SuggestBoxPopUpValue> valueOptional = getValues()
                .stream()
                .filter(value -> comparator.apply(value.toString()))
                .findFirst();
        if (valueOptional.isPresent()) {
            valueOptional.get().select();
        } else {
            throw new SuggestedItemNotFoundException("Suggestion not found by given comparator!");
        }
    }

    /**
     * Selects first suggestion which equals to label
     * @param label
     */
    public void selectSuggestionByLabel(String label) {
        try {
            selectSuggestionByLabel(label::equals);
        } catch (SuggestedItemNotFoundException e) {
            throw new SuggestedItemNotFoundException("Suggestion identified by label '" + label + "' was not found!");
        }
    }

    private WebElement getSuggestBoxContent() {
        String className = PropUtils.get("components.suggestboxpopup.content.class");
        By selector = ByJQuery.selector("." + className);
        return root.findElement(selector);
    }

    private WebElement getTableWithSuggestedItems() {
        By selector = ByJQuery.selector(TABLE_HTML_NAME);
        return getSuggestBoxContent().findElement(selector);
    }

}
