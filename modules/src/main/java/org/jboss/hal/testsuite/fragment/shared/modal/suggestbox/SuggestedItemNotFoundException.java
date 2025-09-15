package org.jboss.hal.testsuite.fragment.shared.modal.suggestbox;

import org.openqa.selenium.NotFoundException;

/**
 * This exception will be throws when desired item was not found among suggest item in UI
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 8/22/16.
 */
public class SuggestedItemNotFoundException extends NotFoundException {

    public SuggestedItemNotFoundException(String message) {
        super(message);
    }
}
