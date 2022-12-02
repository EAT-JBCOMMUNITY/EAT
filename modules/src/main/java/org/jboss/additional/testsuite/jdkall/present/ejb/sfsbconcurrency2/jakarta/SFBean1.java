package org.jboss.additional.testsuite.jdkall.present.ejb.sfsbconcurrency2;

import jakarta.ejb.Stateful;
import jakarta.inject.Inject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.enterprise.inject.Produces;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/ejb/src/main/java","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
@Stateful
@RequestScoped
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class SFBean1 {

	public void foo() {
	}

	@Produces 
	public Foo produceString() {
		return new Foo();
	}
}
