/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.additional.testsuite.jdkall.present.jaxrs.jaxb;

import java.util.Date;
import java.io.IOException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.annotation.WebFilter;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@WebFilter("*")
@EAT({"modules/testcases/jdkAll/WildflyJakarta/jaxrs/src/main/java#28.0.0.Beta1"})
public class CacheFilter implements Filter {

    @Override
    public void init(FilterConfig fConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String page = httpRequest.getRequestURI();

        httpResponse.setHeader("Access-Control-Allow-Origin","*");
        httpResponse.setHeader("Access-Control-Allow-Methods","GET,POST");
        httpResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        if (((HttpServletRequest) httpRequest).getMethod().equals("OPTIONS")){
    //   System.out.println("Options");
        }
        if (!page.contains("fontawesome-webfont.eot") && !page.contains("/js/lib/") && !page.contains("/css/")) {
            httpResponse.setHeader("Expires", "-1");
            httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, post-check=0, pre-check=0");
            httpResponse.setHeader("Pragma", "no-cache");
        } else {
            httpResponse.setHeader("Cache-Control", "max-age=" + 604800);
            httpResponse.setDateHeader("Last-Modified", new Date().getTime());
        }
       chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
