/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2018, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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

package org.jboss.additional.testsuite.jdkall.present.security.web;

import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@EapAdditionalTestsuite({"modules/testcases/jdkAll/Wildfly/security/src/main/java#13.0.0","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/security/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/security/src/main/java#7.2.0.CD14","modules/testcases/jdkAll/Eap72x/security/src/main/java#7.2.0.CD14","modules/testcases/jdkAll/Eap71x-Proposed/security/src/main/java#7.1.5","modules/testcases/jdkAll/Eap71x/security/src/main/java#7.1.5","modules/testcases/jdkAll/Eap7/security/src/main/java"})
@WebServlet(name = "UnsecuredServlet", urlPatterns = {UnsecuredServlet.URL_PATTERN})
public class UnsecuredServlet extends HttpServlet {

    public static final String URL_PATTERN = "/unsecured";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("Hello world!");
        writer.close();
    }
}
