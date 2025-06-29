package org.jboss.additional.testsuite.jdkall.present.elytron.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.handlers.ServletRequestContext;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/Eap73x/elytron/src/main/java","modules/testcases/jdkAll/Eap7Plus/elytron/src/main/java#7.4.8"})
public class MyDummyTokenHandler
        implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange)
            throws Exception {
        HttpServletRequest request = (HttpServletRequest) exchange
                .getAttachment(ServletRequestContext.ATTACHMENT_KEY)
                .getServletRequest();
        HttpServletResponse response = (HttpServletResponse) exchange
                .getAttachment(ServletRequestContext.ATTACHMENT_KEY)
                .getServletResponse();

        boolean login = false;
        try {
            login = Boolean.valueOf(request.getParameter("login"));
        } catch (Exception e) {
            //ignore
        }
        final String AUTHENTICATED = "authenticated";
        HttpSession session = request.getSession(false);
        if (login && (session == null || session.getAttribute(AUTHENTICATED) == null)) {
            request.login("testuser", "testpassword");
            session = session == null ? request.getSession() : session;
            session.putValue(AUTHENTICATED, AUTHENTICATED);
        }

    }

}
