package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import jakarta.ejb.Local;

/**
 * @author bmaxwell
 *
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
@Local
public interface Hello {
    public String hello();
}
