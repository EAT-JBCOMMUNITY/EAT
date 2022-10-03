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

package org.jboss.additional.testsuite.jdkall.present.web.classloading.ear;

import org.jboss.eap.additional.testsuite.annotations.EAT;

import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import java.net.MalformedURLException;

@WebService
@Stateless
@EAT({"modules/testcases/jdkAll/WildflyJakarta/web/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/web/src/main/java"})
public class GreeterEJBImpl implements GreeterEJB {

    public static final String CLASS_NAME = GreeterEJBImpl.class.getSimpleName();
    public static final String SERVICE_NAME = CLASS_NAME + "Service";
    public static final String NAMESPACE = "http://ear.classloading.web.present.jdkall.testsuite.additional.jboss.org/";

    private static ClassLoader cttl;
    private static boolean cttlAlreadySet = false;

    GreeterServiceClient greeterServiceClient = new GreeterServiceClient();

    public GreeterEJBImpl() throws MalformedURLException {
        if (!cttlAlreadySet) {
            cttl = Thread.currentThread().getContextClassLoader();
            cttlAlreadySet = true;
        }
    }

    @WebMethod
    public String sayHello() {
        return greeterServiceClient.sayHello();
    }

    @WebMethod
    public boolean wasTCCLNull() {
        return cttl == null;
    }
}