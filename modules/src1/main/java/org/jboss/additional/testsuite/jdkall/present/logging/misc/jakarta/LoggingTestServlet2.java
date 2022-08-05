/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2016, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.logging.misc;

import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.jboss.additional.testsuite.jdkall.present.logging.misc.log.LoggingUtil;

@WebServlet(name = "SimpleServlet", urlPatterns = {"/simple2"})
@EAT({"modules/testcases/jdkAll/WildflyJakarta/logging/src/main/java#27.0.0.Alpha4"})
public class LoggingTestServlet2 extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(LoggingTestServlet2.class.getPackage().getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Log from this resource
            LOGGER.infof("Log message from the LogResource: %s", LoggingUtil.getClassLoader());

            // Log from the library with other log facades
            LoggingUtil.infoJBossLogging("org.jboss.reproducer.log.jboss.logging", "Test message from JBoss Logging");
            LoggingUtil.infoJul("org.jboss.reproducer.log.jul", "Test message from JUL");
            LoggingUtil.infoCommonsLogging("org.jboss.reproducer.log.commons.logging", "Test message from commons-logging");
            LoggingUtil.infoSlf4j("org.jboss.reproducer.log.slf4j", "Test message from slf4j");
        } catch (Throwable e) {
            throw new ServletException(e);
        }
    }

}
