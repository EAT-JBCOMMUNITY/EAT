package org.jboss.additional.testsuite.jdkall.present.management.cli;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author Stuart Douglas
 */
@WebServlet(urlPatterns = "/EarServlet")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/management/src/main/java#27.0.0.Alpha4"})
public class EarServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream resource = getClass().getClassLoader().getResourceAsStream("jar-info.txt");
        try {
            byte[] data = new byte[100];
            int res;
            while ((res = resource.read(data)) > 0) {
                resp.getOutputStream().write(data, 0, res);
            }
        } finally {
            resource.close();
        }
    }
}
