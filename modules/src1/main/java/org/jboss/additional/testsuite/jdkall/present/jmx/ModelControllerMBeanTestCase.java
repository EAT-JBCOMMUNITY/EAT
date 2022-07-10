/*
* JBoss, Home of Professional Open Source.
* Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.jmx;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ContainerResource;
import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.jmx.model.ModelControllerMBeanHelper;
import org.jboss.as.test.integration.common.DefaultConfiguration;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xnio.IoUtils;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 */
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/jmx/src/main/java","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/jmx/src/main/java","modules/testcases/jdkAll/Wildfly/jmx/src/main/java","modules/testcases/jdkAll/ServerBeta/jmx/src/main/java"})
public class ModelControllerMBeanTestCase {

    private static final String RESOLVED_DOMAIN = "jboss.as";

    static final ObjectName RESOLVED_MODEL_FILTER = createObjectName(RESOLVED_DOMAIN  + ":*");
    static final ObjectName RESOLVED_ROOT_MODEL_NAME = ModelControllerMBeanHelper.createRootObjectName(RESOLVED_DOMAIN);

    static JMXConnector connector;
    static MBeanServerConnection connection;

    @Deployment(testable = false)
    public static Archive<?> deploy() {
        WebArchive war = ShrinkWrap.create(WebArchive.class,"ModelControllerMBeanTestCase.war");
        war.addClasses(ModelControllerMBeanTestCase.class);
        return war;
    }

    @ContainerResource
    private ManagementClient managementClient;


    @Before
    public void initialize() throws Exception {
        connection = setupAndGetConnection();
    }

    @After
    public void closeConnection() throws Exception {
        IoUtils.safeClose(connector);
    }

    /**
     * Test that all the MBean infos can be read properly
     */
    @Test
    public void testAllMBeanInfos() throws Exception {
        Set<ObjectName> names = connection.queryNames(RESOLVED_MODEL_FILTER, null);
        Map<ObjectName, Exception> failedInfos = new HashMap<ObjectName, Exception>();

        for (ObjectName name : names) {
            try {
                Assert.assertNotNull(connection.getMBeanInfo(name));
            } catch (Exception e) {
                failedInfos.put(name, e);
            }
        }

        // https://issues.redhat.com/browse/WFLY-13977:
        // There are some MBeans which only live a short time, such as the active operations.
        // They may be returned from the original queryNames() call, and then don't exist
        // in the check so they get added to failedInfos. Remove from failedInfos the ones
        // which are not there in a fresh queryNames() call
        Set<ObjectName> currentNames = connection.queryNames(RESOLVED_MODEL_FILTER, null);
        for (ObjectName name : new HashMap<>(failedInfos).keySet()) {
            if (!currentNames.contains(name)) {
                failedInfos.remove(name);
            }
        }

        Assert.assertTrue(failedInfos.toString(), failedInfos.isEmpty());
    }

    private static ObjectName createObjectName(String name) {
        try {
            return ObjectName.getInstance(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private MBeanServerConnection setupAndGetConnection() throws Exception {
        // Make sure that we can connect to the MBean server
        String urlString = System
                .getProperty("jmx.service.url", "service:jmx:remote+http://" + managementClient.getMgmtAddress() + ":" + managementClient.getMgmtPort());
        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
        connector = JMXConnectorFactory.connect(serviceURL, DefaultConfiguration.credentials());
        return connector.getMBeanServerConnection();
    }

}
