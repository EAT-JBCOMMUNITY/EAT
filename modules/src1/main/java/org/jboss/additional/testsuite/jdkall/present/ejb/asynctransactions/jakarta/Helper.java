package org.jboss.additional.testsuite.jdkall.present.ejb.asynctransactions;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import jakarta.transaction.SystemException;
import jakarta.transaction.Transaction;
import jakarta.transaction.TransactionManager;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#27.0.0.Alpha4"})
public class Helper {

    static Transaction getCurrentTransaction() {
        try {
            InitialContext ctx = new InitialContext();
            TransactionManager transactionManager = (TransactionManager) ctx.lookup("java:/TransactionManager");
            return transactionManager.getTransaction();
        } catch (NamingException | SystemException e) {
            throw new RuntimeException(e);
        }
    }

}
