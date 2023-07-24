package org.jboss.additional.testsuite.jdkall.present.web.servlet.timeout;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.naming.InitialContext;
import static javax.transaction.Status.STATUS_ACTIVE;
import javax.transaction.UserTransaction;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@WebServlet(name = "TimeoutServlet", urlPatterns = {"/TimeoutServlet"})
@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.13"})
public class TimeoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InitialContext ctx = null;
        UserTransaction userTransaction = null;
        try {
            ctx = new InitialContext();
            final int timeoutOverride = Integer.parseInt(
                    getServletConfig().getInitParameter("jboss.tx.timeout.override")
            );

            userTransaction = (UserTransaction) ctx.lookup("java:jboss/UserTransaction");

            userTransaction.begin();
            Thread.sleep(timeoutOverride * 1000 + 3000);
            if (userTransaction.getStatus() != STATUS_ACTIVE) {
                throw new IllegalStateException("Transaction already aborted, likely due to JBEAP-22656 ...");
            }
            userTransaction.commit();

            userTransaction.setTransactionTimeout(timeoutOverride);

        } catch (Throwable t) {
            throw new ServletException("Unexpected failure", t);
        } finally {
            if (ctx != null) {
                try {
                    ctx.close();
                } catch (Throwable t) {
                    throw new ServletException("Unexpected failure", t);
                }
            }
        }
    }

}
