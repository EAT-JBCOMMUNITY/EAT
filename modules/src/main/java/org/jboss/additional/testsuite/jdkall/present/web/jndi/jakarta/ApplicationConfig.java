package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
@Startup
@Singleton
public class ApplicationConfig implements ConfigInterface {

    @Override
    public String getConfigValue() {
        return "getConfigValue...";
    }
}
