package org.jboss.additional.testsuite.jdkall.present.messaging.jms.mdb;

import javax.inject.Qualifier;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/messaging/src/main/java#7.4.8","modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Final"})
@Qualifier
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Booking {
}
