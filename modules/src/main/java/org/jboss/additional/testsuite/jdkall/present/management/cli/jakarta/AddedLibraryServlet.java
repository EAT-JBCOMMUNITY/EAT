package org.jboss.additional.testsuite.jdkall.present.management.cli;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Stuart Douglas
 */
@WebServlet(urlPatterns = "/AddedLibraryServlet")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/management/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/management/src/main/java"})
public class AddedLibraryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Added Library Servlet");
    }
}
