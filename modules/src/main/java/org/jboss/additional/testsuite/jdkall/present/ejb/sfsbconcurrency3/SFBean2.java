package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency3;

import javax.ejb.Stateful;
import javax.ejb.DependsOn;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.enterprise.inject.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Wildfly/ejb/src/main/java#10.0.0.Final*27.0.0.Beta2","modules/testcases/jdkAll/ServerBeta/ejb/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/Eap72x/ejb/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/ejb/src/main/java","modules/testcases/jdkAll/Eap73x/ejb/src/main/java","modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java"})
@Stateful
@RequestScoped
public class SFBean2 {

	public void foo() {
            foo.foo();
	}

	@Inject
	private Foo foo;
       
}
