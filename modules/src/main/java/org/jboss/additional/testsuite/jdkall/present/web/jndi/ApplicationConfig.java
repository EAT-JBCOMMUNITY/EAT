package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.3"})
@Startup
@Singleton
public class ApplicationConfig implements ConfigInterface {

    @Override
    public String getConfigValue() {
        return "getConfigValue...";
    }
}
