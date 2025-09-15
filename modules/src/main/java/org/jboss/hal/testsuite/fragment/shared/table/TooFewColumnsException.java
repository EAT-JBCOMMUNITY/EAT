package org.jboss.hal.testsuite.fragment.shared.table;

public class TooFewColumnsException extends RuntimeException {

    public TooFewColumnsException(String message, Throwable cause) {
        super(message, cause);
    }
}
