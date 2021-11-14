package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency3;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.enterprise.inject.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Wildfly/ejb/src/main/java","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap7/ejb/src/main/java"})
@Stateful
public class SFBean1 {

	public void foo() {
	}

	@Produces 
	public Foo produceString() {
		return new Foo();
	}
}
