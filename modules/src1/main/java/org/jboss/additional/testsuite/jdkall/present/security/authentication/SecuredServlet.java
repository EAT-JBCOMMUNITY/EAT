 
package org.jboss.additional.testsuite.jdkall.present.security.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;
 
@SuppressWarnings("serial")
@WebServlet("/secure")
@ServletSecurity(@HttpConstraint(rolesAllowed = { "Admin" }))
@EAT({"modules/testcases/jdkAll/Wildfly/security/src/main/java#27.0.0","modules/testcases/jdkAll/Eap7Plus/security/src/main/java#7.4.4"})
public class SecuredServlet extends HttpServlet {

 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Principal principal = null;
        String authType = null;
        String remoteUser = null;


        principal = req.getUserPrincipal();

        remoteUser = req.getRemoteUser();

        authType = req.getAuthType();

        writer.println("<html><head><title>servlet-security</title></head><body>");
        writer.println("<h1>" + "Successfully called Secured Servlet " + "</h1>");
        writer.println("<p>" + "Principal  : " + principal.getName() + "</p>");
        writer.println("<p>" + "Remote User : " + remoteUser + "</p>");
        writer.println("<p>" + "Authentication Type : " + authType + "</p>");
        writer.println("</body></html>");
        writer.close();
    }

}
