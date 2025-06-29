package org.jboss.additional.testsuite.jdkall.present.jpa.jca;

import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/jpa/src/main/java","modules/testcases/jdkAll/Eap7Plus/jpa/src/main/java#7.4.8"})
@SuppressWarnings("serial")
@WebServlet(name = "sleep", urlPatterns = {"/sleep/"}, loadOnStartup = 1)
public class SleepServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger("SleepServlet");

    @Resource(lookup = "java:jboss/datasources/ExampleDS")
    private DataSource dataSource;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;

        try {
            dataSource.getConnection();

            LOGGER.debug("## SLEEP START");
            Thread.sleep(3000L);
            LOGGER.debug("## SLEEP END");
        } catch (Exception e) {
            LOGGER.debug("Exception occured " + e.getMessage());
        } finally {
            try { con.close(); } catch (Exception ee) {}
        }

        response.setContentType("text/plain");
        final PrintWriter writer = response.getWriter();
        writer.write("Servlet result OK");
        writer.close();
    }
}
