package org.jboss.additional.testsuite.jdkall.present.web.security.manualh2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.util.JdbcUtils;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.11"})
@WebServlet(urlPatterns = {H2Servlet.URL_PATTERN})
public class H2Servlet extends HttpServlet {

    public static final String URL_PATTERN = "/h2";

    private static final Logger log = LoggerFactory.getLogger(H2Servlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html");

            PrintWriter out = response.getWriter();

            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");

        /*    // start a TCP server to be used with h2 console. Start h2 console manually.
            Server server = Server.createTcpServer().start();

            System.out.println("Server started and connection is open.");
            System.out.println("URL: jdbc:h2:" + server.getURL() + "/mem:test");
            Thread.sleep(500000);
        */
            try {
                conn = JdbcUtils.getConnection("javax.naming.InitialContext", "ldap:jboss/datasources/ExampleDS", "", "");
                throw new ServletException();
            } catch (SQLException e) {
                throw new ServletException(e);
                // Exception is expected. Lookup that does not contain java: should fail.
            }
        } catch (Throwable ex) {
            throw new ServletException(ex);
        }
    }
}
