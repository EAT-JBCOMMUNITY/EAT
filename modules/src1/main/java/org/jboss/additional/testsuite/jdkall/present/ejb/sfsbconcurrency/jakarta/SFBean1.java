package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency;

import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
@Stateful
@RequestScoped
public class SFBean1 {

	public void foo() {
            foo.foo();
	}

	@Inject
	private Foo foo;
}
