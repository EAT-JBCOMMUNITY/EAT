package org.jboss.additional.testsuite.jdkall.present.batch.itemreadertransaction;

import jakarta.annotation.Resource;
import jakarta.transaction.TransactionSynchronizationRegistry;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional.TxType;

import org.jberet.cdi.StepScoped;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@StepScoped
@EAT({"modules/testcases/jdkAll/WildflyJakarta/batch/src/main/java#27.0.0.Alpha4"})
@Transactional(TxType.MANDATORY)
public class StepScopedBean
{
  @Resource
  private TransactionSynchronizationRegistry reg;

  public void close()
  {
    System.out.println("StepScopedBean.close() called; Transaction ID: " + reg.getTransactionKey());
  }
}
