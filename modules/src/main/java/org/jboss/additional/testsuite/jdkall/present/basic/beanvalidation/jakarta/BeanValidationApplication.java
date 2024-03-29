package org.jboss.additional.testsuite.jdkall.present.basic.beanvalidation;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/basic/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/EapJakarta/basic/src/main/java","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/basic/src/main/java"})
@ApplicationPath("/bean-validation")
public class BeanValidationApplication extends Application {

}
