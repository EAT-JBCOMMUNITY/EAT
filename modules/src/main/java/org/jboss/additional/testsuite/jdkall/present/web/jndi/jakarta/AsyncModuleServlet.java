package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import java.rmi.ServerException;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
@WebServlet(urlPatterns = "/async-module", asyncSupported = true)
public class AsyncModuleServlet extends HttpServlet {

    public static final String URL_PATTERN = "/async-module";
    private static final long serialVersionUID = 1L;

    @Override
    public final void doGet(final HttpServletRequest request, HttpServletResponse response)
            throws ServerException, IOException {
        final AsyncContext asyncContext = request.startAsync();
        asyncContext.start(new Processing("java:module/ApplicationConfig!org.jboss.additional.testsuite.jdkall.present.web.jndi.ConfigInterface",
                (HttpServletResponse) asyncContext.getResponse()));

    }
}
