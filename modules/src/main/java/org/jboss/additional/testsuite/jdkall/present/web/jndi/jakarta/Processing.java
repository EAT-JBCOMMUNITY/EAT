package org.jboss.additional.testsuite.jdkall.present.web.jndi;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.naming.InitialContext;
import jakarta.servlet.http.HttpServletResponse;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#29.0.0"})
public class Processing implements Runnable {

    private final HttpServletResponse response;
    private final String jndi;

    public Processing(final String jndi, final HttpServletResponse response) {
        super();
        this.response = response;
        this.jndi = jndi;
    }

    public void run() {
        try {
            final String result = lookup(jndi);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write(result);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_OK);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            try {
                response.getWriter().write(sw.toString());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private String lookup(final String jndi) throws Exception {
        Properties props = new Properties();
        final String result = ((ConfigInterface) new InitialContext(props).lookup(jndi)).getConfigValue();
        return "lookup [" + jndi + "] : " + result;
    }

}
