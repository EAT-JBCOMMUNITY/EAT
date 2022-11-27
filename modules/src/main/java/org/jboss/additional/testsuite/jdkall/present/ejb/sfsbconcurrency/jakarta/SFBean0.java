package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
@Stateful
@RequestScoped
public class SFBean0 {

        @Produces 
	public Foo produceString() {
		return new Foo();
	}
	
}
