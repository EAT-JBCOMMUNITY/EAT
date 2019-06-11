/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.clustering;

import org.jboss.as.test.shared.TimeoutUtil;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 * Test constants statically imported used across the clustering tests.
 *
 * @author Radoslav Husar
 * @version Feb 2012
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/Wildfly/clustering/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/WildflyRelease-10.1.0.Final/clustering/src/main/java","modules/testcases/jdkAll/Eap72x/clustering/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/clustering/src/main/java","modules/testcases/jdkAll/Eap7/clustering/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/clustering/src/main/java","modules/testcases/jdkAll/Eap71x/clustering/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/clustering/src/main/java","modules/testcases/jdkAll/Eap70x/clustering/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/clustering/src/main/java"})
public interface ClusteringTestConstants {

    /**
     * Test configuration.
     */
    String TEST_CACHE_MODE = System.getProperty("cacheMode");

    /**
     * Manual container with unmanaged deployments names.
     */
    String CONTAINER_SINGLE = "container-single";
    String CONTAINER_1 = "container-0";
    String CONTAINER_2 = "container-1";
    String[] CONTAINERS = new String[] { CONTAINER_1, CONTAINER_2 };

    /**
     * Deployment names.
     */
    String DEPLOYMENT_1 = "deployment-0";
    String DEPLOYMENT_2 = "deployment-1";
    String[] DEPLOYMENTS = new String[] { DEPLOYMENT_1, DEPLOYMENT_2 };

    /**
     * Some helper deployment names.
     */
    String DEPLOYMENT_HELPER_1 = "deployment-helper-0";
    String DEPLOYMENT_HELPER_2 = "deployment-helper-1";
    String[] DEPLOYMENT_HELPERS = new String[] { DEPLOYMENT_HELPER_1, DEPLOYMENT_HELPER_2 };

    /**
     * Node names passed in arquillian.xml via -Djboss.node.name property.
     */
    String NODE_1 = "node-0";
    String NODE_2 = "node-1";
    String[] NODES = new String[] { NODE_1, NODE_2 };

    /**
     * Name of cluster for remote client.
     */
    String CLUSTER_NAME = "ejb";

    /**
     * Timeouts.
     */
    int GRACE_TIME_TO_REPLICATE = TimeoutUtil.adjust(3000);

    /**
     * TODO: This will be removed.
     */
    int GRACE_TIME_TO_MEMBERSHIP_CHANGE = TimeoutUtil.adjust(10000);

    int CLUSTER_ESTABLISHMENT_WAIT_MS = TimeoutUtil.adjust(100);
    int CLUSTER_ESTABLISHMENT_LOOP_COUNT = 20;
    int WAIT_FOR_PASSIVATION_MS = TimeoutUtil.adjust(5);
    int HTTP_REQUEST_WAIT_TIME_S = TimeoutUtil.adjust(5);
    String NODE_NAME_PROPERTY = "jboss.node.name";
}
