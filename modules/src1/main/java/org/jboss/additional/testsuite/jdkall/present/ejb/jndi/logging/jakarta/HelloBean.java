package org.jboss.additional.testsuite.jdkall.present.ejb.jndi.logging;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;

@Stateless(name="Hello")
@Remote(Hello.class)
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
public class HelloBean implements Hello {
}
