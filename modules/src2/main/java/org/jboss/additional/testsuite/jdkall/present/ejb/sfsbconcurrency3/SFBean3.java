package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency3;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java"})
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
