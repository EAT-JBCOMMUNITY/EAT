package org.jboss.additional.testsuite.jdkall.present.ejb.transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.ejb.EJB;
import static org.junit.Assert.fail;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/ejb/src/main/java#29.0.0","modules/testcases/jdkAll/EapJakarta/ejb/src/main/java"})
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
