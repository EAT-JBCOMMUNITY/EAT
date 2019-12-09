package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;
import javax.ejb.ApplicationException;

/**
 * @author bmaxwell
 *
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x/ejb/src/main/java", "modules/testcases/jdkAll/Eap72x-Proposed/ejb/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java"})
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
