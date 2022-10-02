package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ejb.ApplicationException;

/**
 * @author bmaxwell
 *
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java"})
@ApplicationException(rollback = false)
public class MyApplicationExceptionNoRollbackRuntimeException extends RuntimeException {
    /**
     *
     */
    public MyApplicationExceptionNoRollbackRuntimeException() {
    }

    /**
     * @param message
     */
    public MyApplicationExceptionNoRollbackRuntimeException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public MyApplicationExceptionNoRollbackRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public MyApplicationExceptionNoRollbackRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
