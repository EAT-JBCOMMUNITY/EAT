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

package org.jboss.additional.testsuite.jdkall.present.messaging.jms.deployment;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
/**
 *
 * @author Peter Mackay
 */
@EAT({"modules/testcases/jdkAll/WildflyJakarta/messaging/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/messaging/src/main/java","modules/testcases/jdkAll/EapJakarta/messaging/src/main/java"})
@WebServlet(name = "EagerMBeanQueryServlet", urlPatterns = { "/EagerMBeanQueryServlet" }, loadOnStartup = 1)
public class EagerMBeanQueryServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        System.out.println("HI-servlet");
        try {
            mBeanServer.queryNames(new ObjectName("*.*:*"), null);
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println("Hello World!");
        writer.close();
    }
}
