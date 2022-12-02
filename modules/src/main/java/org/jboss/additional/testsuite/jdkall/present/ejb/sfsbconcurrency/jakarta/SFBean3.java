package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency;

import jakarta.ejb.Stateful;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
@Stateful
@RequestScoped
public class SFBean3 {

        @Inject
	private SFBean1 bean1;

	@Inject
	private SFBean2 bean2;
		
	public void test() {
		bean1.foo();
		bean2.foo();
	}

}
