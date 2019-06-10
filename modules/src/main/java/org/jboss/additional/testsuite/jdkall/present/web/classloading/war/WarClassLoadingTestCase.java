/*
 * JBoss, Home of Professional Open Source
 * Copyright 2016, Red Hat Inc., and individual contributors as indicated
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
package org.jboss.additional.testsuite.jdkall.present.web.classloading.war;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@EapAdditionalTestsuite({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/web/src/main/java","modules/testcases/jdkAll/Wildfly/web/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/web/src/main/java","modules/testcases/jdkAll/Eap72x/web/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/web/src/main/java","modules/testcases/jdkAll/Eap7/web/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/web/src/main/java","modules/testcases/jdkAll/Eap71x/web/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/web/src/main/java","modules/testcases/jdkAll/Eap70x/web/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/web/src/main/java","modules/testcases/jdkAll/Eap64x/web/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/web/src/main/java","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/web/src/main/java","modules/testcases/jdkAll/Eap63x/web/src/main/java","modules/testcases/jdkAll/Eap62x/web/src/main/java","modules/testcases/jdkAll/Eap61x/web/src/main/java"})
public class WarClassLoadingTestCase {

    @Deployment
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class);
        war.addClass(WarClassLoadingTestCase.class);
        JavaArchive libJar = ShrinkWrap.create(JavaArchive.class);
        libJar.addClass(WebInfLibClass.class);
        war.addAsLibraries(libJar);
        return war;
    }

    @Test
    public void testWebInfLibAccessible() throws ClassNotFoundException {
        loadClass("org.jboss.additional.testsuite.jdkall.present.web.classloading.war.WebInfLibClass");
    }

    private static Class<?> loadClass(String name) throws ClassNotFoundException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl != null) {
            try {
                return Class.forName(name, false, cl);
            } catch (ClassNotFoundException ex) {
                return Class.forName(name);
            }
        } else
            return Class.forName(name);
    }
}
