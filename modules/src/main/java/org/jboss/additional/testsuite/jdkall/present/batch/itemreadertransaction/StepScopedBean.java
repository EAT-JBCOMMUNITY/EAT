package org.jboss.additional.testsuite.jdkall.present.batch.itemreadertransaction;

import javax.annotation.Resource;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.jberet.cdi.StepScoped;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@StepScoped
@EAT({"modules/testcases/jdkAll/Wildfly/batch/src/main/java#27.0.0", "modules/testcases/jdkAll/Eap73x/batch/src/main/java","modules/testcases/jdkAll/Eap7Plus/batch/src/main/java#7.4.5"})
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
