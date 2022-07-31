package org.jboss.additional.testsuite.jdkall.present.ejb.exceptions;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.annotation.Resource;

import jakarta.ejb.EJB;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

import jakarta.transaction.UserTransaction;

import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;

import jakarta.transaction.Synchronization;
import jakarta.transaction.TransactionSynchronizationRegistry;

/**
 * @author bmaxwell
 *
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
@Stateless
public class HelloBean implements Hello {

  @Resource(mappedName = "java:comp/TransactionSynchronizationRegistry")
  private TransactionSynchronizationRegistry txSyncReg;

  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public String hello() {

		txSyncReg.registerInterposedSynchronization(
			new Synchronization() {
				public void afterCompletion(int status) { 
				}
				public void beforeCompletion() { 
					throw new RuntimeException("Fail the tx"); 
				}
			});

		throw new MyApplicationExceptionNoRollbackRuntimeException("Client should not see this, instead should see an EJBException");
  }
}
