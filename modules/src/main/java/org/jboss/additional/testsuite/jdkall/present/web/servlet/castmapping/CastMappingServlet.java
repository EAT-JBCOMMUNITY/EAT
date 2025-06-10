
package org.jboss.additional.testsuite.jdkall.present.web.servlet.castmapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.MappingMatch;
import java.io.PrintWriter;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@WebServlet(name = "CastMappingServlet", urlPatterns = {"/CastMappingServlet",
            "/AForwardToB",
            "/AIncludesB"})
@EAT({"modules/testcases/jdkAll/Eap73x/web/src/main/java","modules/testcases/jdkAll/Eap7Plus/web/src/main/java#7.4.14"})
public class CastMappingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	printCurrentMappingDetails(request, out);
        HttpServletMapping mapping = request.getHttpServletMapping();
        if (mapping.getPattern().equals("/CastMappingServlet")) {
            RequestDispatcher rd = request.getRequestDispatcher("/AIncludesB");
            rd.include(request, response);
        }
    }
    
    public static void printCurrentMappingDetails(HttpServletRequest request,
            PrintWriter out) throws IOException {
            HttpServletMapping forwardMapping = (HttpServletMapping) request.getAttribute(RequestDispatcher.FORWARD_MAPPING);
            HttpServletMapping includeMapping = (HttpServletMapping) request.getAttribute(RequestDispatcher.INCLUDE_MAPPING);

            out.print("<p> " + request.getHttpServletMapping() + "</p>");
            out.print("<p> FORWARD_MAPPING: " + forwardMapping + "</p>");
            out.print("<p> INCLUDE_MAPPING: " + includeMapping + "</p>");
            out.print("<hr />");


    }
   
}
