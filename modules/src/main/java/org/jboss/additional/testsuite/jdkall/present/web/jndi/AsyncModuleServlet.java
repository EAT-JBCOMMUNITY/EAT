package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import java.rmi.ServerException;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.3"})
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
