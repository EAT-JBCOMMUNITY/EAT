package org.jboss.additional.testsuite.jdkall.present.ejb.transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import static org.junit.Assert.fail;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/ejb/src/main/java#7.4.8"})
@WebServlet(urlPatterns = {TransactionalServlet.URL_PATTERN})
public class TransactionalServlet extends HttpServlet {

    public static final String URL_PATTERN = "/transactional";

    private static final Logger log = LoggerFactory.getLogger(TransactionalServlet.class);

    @EJB
    TransactionalEjb transactionalEjb;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.write("Calling notSupportedTransactional and requiredTransactional ejbs : ");

        out.write("\n");

        try {
            transactionalEjb.notSupportedTransactional();
            fail("notSupportedTransactional ejb call shoule have failed...");
        } catch (Exception ex) {

        }

        try {
            transactionalEjb.requiredTransactional();
            fail("requiredTransactional ejb call shoule have failed...");
        } catch (Exception ex) {
        }
    }
}
