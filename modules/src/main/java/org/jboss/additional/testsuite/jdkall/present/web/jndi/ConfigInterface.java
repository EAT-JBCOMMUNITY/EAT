package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.3", "modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
public interface ConfigInterface {

    String getConfigValue();
}
