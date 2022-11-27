/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2013, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.clustering.cluster;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.infinispan.server.test.core.ServerRunMode;
import org.infinispan.server.test.core.TestSystemPropertyNames;
import org.infinispan.server.test.junit4.InfinispanServerRule;
import org.infinispan.server.test.junit4.InfinispanServerRuleBuilder;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.test.api.Deployer;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.as.arquillian.api.WildFlyContainerController;
import org.jboss.additional.testsuite.jdkall.present.clustering.NodeUtil;
import org.jboss.as.test.shared.TimeoutUtil;
import org.jboss.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.rules.TestRule;

/**
 * Base implementation for every clustering test which guarantees a framework contract as follows:
 * <ol>
 * <li>test case is constructed specifying nodes and deployments required</li>
 * <li>before every test method, first all containers that are not required in the test are stopped</li>
 * <li>before every test method, containers are started and deployments are deployed via {@link #beforeTestMethod()}</li>
 * <li>after every method execution the deployments are undeployed via {@link #afterTestMethod()}</li>
 * </ol>
 * Should the test demand different node and deployment handling, the {@link #beforeTestMethod()} and {@link #afterTestMethod()}
 * may be overridden.
 * <p>
 * Furthermore, this base class provides common constants for node/instance-id, deployment/deployment helpers, timeouts and provides
 * convenience methods for managing container and deployment lifecycle ({@link #start(String...)}, {@link #deploy(String...)}, etc).
 *
 * @author Radoslav Husar
 */
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/EapJakarta/clustering/src/main/java"})
public abstract class AbstractClusteringTestCase {

    public static final String CONTAINER_SINGLE = "node-non-ha";

    // Unified node and container names
    public static final String NODE_1 = "node-1";
    public static final String NODE_2 = "node-2";
    public static final String NODE_3 = "node-3";
    public static final String NODE_4 = "node-4";
    @Deprecated public static final String[] TWO_NODES = new String[] { NODE_1, NODE_2 };
    @Deprecated public static final String[] THREE_NODES = new String[] { NODE_1, NODE_2, NODE_3 };
    @Deprecated public static final String[] FOUR_NODES = new String[] { NODE_1, NODE_2, NODE_3, NODE_4 };
    public static final Set<String> NODE_1_2 = Set.of(TWO_NODES);
    public static final Set<String> NODE_1_2_3 = Set.of(THREE_NODES);
    public static final Set<String> NODE_1_2_3_4 = Set.of(FOUR_NODES);
    public static final String NODE_NAME_PROPERTY = "jboss.node.name";

    // Test deployment names
    public static final String DEPLOYMENT_1 = "deployment-1";
    public static final String DEPLOYMENT_2 = "deployment-2";
    public static final String DEPLOYMENT_3 = "deployment-3";
    public static final String DEPLOYMENT_4 = "deployment-4";
    @Deprecated public static final String[] TWO_DEPLOYMENTS = new String[] { DEPLOYMENT_1, DEPLOYMENT_2 };
    @Deprecated public static final String[] THREE_DEPLOYMENTS = new String[] { DEPLOYMENT_1, DEPLOYMENT_2, DEPLOYMENT_3 };
    @Deprecated public static final String[] FOUR_DEPLOYMENTS = new String[] { DEPLOYMENT_1, DEPLOYMENT_2, DEPLOYMENT_3, DEPLOYMENT_4 };
    public static final Set<String> DEPLOYMENT_1_2 = Set.of(TWO_DEPLOYMENTS);
    public static final Set<String> DEPLOYMENT_1_2_3 = Set.of(THREE_DEPLOYMENTS);
    public static final Set<String> DEPLOYMENT_1_2_3_4 = Set.of(FOUR_DEPLOYMENTS);

    // Helper deployment names
    public static final String DEPLOYMENT_HELPER_1 = "deployment-helper-0";
    public static final String DEPLOYMENT_HELPER_2 = "deployment-helper-1";
    public static final String DEPLOYMENT_HELPER_3 = "deployment-helper-2";
    public static final String DEPLOYMENT_HELPER_4 = "deployment-helper-3";
    @Deprecated public static final String[] TWO_DEPLOYMENT_HELPERS = new String[] { DEPLOYMENT_HELPER_1, DEPLOYMENT_HELPER_2 };
    @Deprecated public static final String[] FOUR_DEPLOYMENT_HELPERS = new String[] { DEPLOYMENT_HELPER_1, DEPLOYMENT_HELPER_2, DEPLOYMENT_HELPER_3, DEPLOYMENT_HELPER_4 };
    public static final Set<String> DEPLOYMENT_HELPER_1_2 = Set.of(TWO_DEPLOYMENT_HELPERS);
    public static final Set<String> DEPLOYMENT_HELPER_1_2_3_4 = Set.of(FOUR_DEPLOYMENT_HELPERS);

    // Infinispan Server
    public static final String INFINISPAN_SERVER_HOME = System.getProperty("infinispan.server.home");
    public static final String INFINISPAN_SERVER_PROFILE = System.getProperty("infinispan.server.profile");
    public static final String INFINISPAN_SERVER_PROFILE_DEFAULT = "infinispan-13_0.xml";
    public static final String INFINISPAN_SERVER_ADDRESS = "127.0.0.1";
    public static final int INFINISPAN_SERVER_PORT = 11222;
    public static final String INFINISPAN_APPLICATION_USER = "testsuite-application-user";
    public static final String INFINISPAN_APPLICATION_PASSWORD = "testsuite-application-password";
    public static final InfinispanServerRule INFINISPAN_SERVER_RULE = null;

    static {
        String profile = (INFINISPAN_SERVER_PROFILE == null || INFINISPAN_SERVER_PROFILE.isEmpty()) ? INFINISPAN_SERVER_PROFILE_DEFAULT : INFINISPAN_SERVER_PROFILE;
        // Workaround for "ISPN-13107 ServerRunMode.FORKED yields InvalidPathException with relative server config paths on Windows platform" by using absolute file path which won't get mangled.
        String absoluteConfigurationFile = null;
        try {
            absoluteConfigurationFile = Paths.get(Objects.requireNonNull(AbstractClusteringTestCase.class.getClassLoader().getResource(profile)).toURI()).toFile().toString();
        } catch (URISyntaxException ignore) {
        }
    }

    public static TestRule infinispanServerTestRule() {
        return INFINISPAN_SERVER_RULE;
    }

    // Undertow-based WildFly load-balancer
    public static final String LOAD_BALANCER_1 = "load-balancer-1";

    // H2 database
    public static final String DB_PORT = System.getProperty("dbport", "9092");

    // Timeouts
    public static final int GRACE_TIME_TO_REPLICATE = TimeoutUtil.adjust(3000);
    public static final int GRACE_TIME_TOPOLOGY_CHANGE = TimeoutUtil.adjust(3000);
    public static final int GRACEFUL_SHUTDOWN_TIMEOUT = TimeoutUtil.adjust(15);
    public static final int GRACE_TIME_TO_MEMBERSHIP_CHANGE = TimeoutUtil.adjust(10000);
    public static final int WAIT_FOR_PASSIVATION_MS = TimeoutUtil.adjust(5);
    public static final int HTTP_REQUEST_WAIT_TIME_S = TimeoutUtil.adjust(5);

    // System Properties
    public static final String TESTSUITE_NODE0 = System.getProperty("node0", "127.0.0.1");
    public static final String TESTSUITE_NODE1 = System.getProperty("node1", "127.0.0.1");
    public static final String TESTSUITE_NODE2 = System.getProperty("node2", "127.0.0.1");
    public static final String TESTSUITE_NODE3 = System.getProperty("node3", "127.0.0.1");
    public static final String TESTSUITE_MCAST = System.getProperty("mcast", "230.0.0.4");
    public static final String TESTSUITE_MCAST1 = System.getProperty("mcast1", "230.0.0.5");
    public static final String TESTSUITE_MCAST2 = System.getProperty("mcast2", "230.0.0.6");
    public static final String TESTSUITE_MCAST3 = System.getProperty("mcast3", "230.0.0.7");

    protected static final Logger log = Logger.getLogger(AbstractClusteringTestCase.class);
    private static final Map<String, String> CONTAINER_TO_DEPLOYMENT = Map.of(NODE_1, DEPLOYMENT_1, NODE_2, DEPLOYMENT_2, NODE_3, DEPLOYMENT_3, NODE_4, DEPLOYMENT_4);

    protected static Map.Entry<String, String> parseSessionRoute(HttpResponse response) {
        Header setCookieHeader = response.getFirstHeader("Set-Cookie");
        if (setCookieHeader == null) return null;
        String setCookieValue = setCookieHeader.getValue();
        final String id = setCookieValue.substring(setCookieValue.indexOf('=') + 1, setCookieValue.indexOf(';'));
        final int index = id.indexOf('.');
        return (index < 0) ? new AbstractMap.SimpleImmutableEntry<>(id, null) : new AbstractMap.SimpleImmutableEntry<>(id.substring(0, index), id.substring(index + 1));
    }

    @ArquillianResource
    protected ContainerRegistry containerRegistry;
    @ArquillianResource
    protected WildFlyContainerController controller;
    @ArquillianResource
    protected Deployer deployer;

    private final Set<String> containers;
    private final Set<String> deployments;

    // Framework contract methods
    public AbstractClusteringTestCase() {
        this(NODE_1_2);
    }

    @Deprecated
    public AbstractClusteringTestCase(String[] nodes) {
        this(Set.of(nodes));
    }

    public AbstractClusteringTestCase(Set<String> containers) {
        this(containers, toDeployments(containers));
    }

    @Deprecated
    public AbstractClusteringTestCase(String[] nodes, String[] deployments) {
        this(Set.of(nodes), Set.of(deployments));
    }

    public AbstractClusteringTestCase(Set<String> containers, Set<String> deployments) {
        this.containers = containers;
        this.deployments = deployments;
    }

    /**
     * Guarantees that prior to test method execution
     * (1) all containers that are not used in the test are stopped and,
     * (2) all requested containers are running and,
     * (3) all requested deployments are deployed thus allowing all necessary test resources injection.
     */
    @Before
    public void beforeTestMethod() throws Exception {
        this.containerRegistry.getContainers().forEach(container -> {
            if (container.getState() == Container.State.STARTED && !this.containers.contains(container.getName())) {
                // Even though we should be able to just stop the container object this currently fails with:
                // WFARQ-47 Calling "container.stop();" always ends exceptionally "Caught exception closing ManagementClient: java.lang.NullPointerException"
                this.stop(container.getName());
                log.debugf("Stopped container '%s' which was started but not requested for this test.", container.getName());
            }
        });

        this.start();
        this.deploy();
    }

    /**
     * Guarantees that all deployments are undeployed after the test method has been executed.
     */
    @After
    public void afterTestMethod() throws Exception {
        this.start();
        this.undeploy();
    }

    // Node and deployment lifecycle management convenience methods
    @Deprecated
    protected void start(String... containers) {
        start(Set.of(containers));
    }

    protected void start() {
        this.start(this.containers);
    }

    protected void start(String container) {
        NodeUtil.start(this.controller, container);
    }

    protected void start(Set<String> containers) {
        NodeUtil.start(this.controller, containers);
    }

    @Deprecated
    protected void stop(String... containers) {
        stop(Set.of(containers));
    }

    protected void stop() {
        this.stop(this.containers);
    }

    protected void stop(String container) {
        NodeUtil.stop(this.controller, container);
    }

    protected void stop(Set<String> containers) {
        NodeUtil.stop(this.controller, containers);
    }

    protected boolean isStarted(String container) {
        return NodeUtil.isStarted(this.controller, container);
    }

    @Deprecated
    protected void stop(int timeout, String... containers) {
        stop(Set.of(containers), timeout);
    }

    protected void stop(String container, int timeout) {
        NodeUtil.stop(this.controller, container, timeout);
    }

    protected void stop(Set<String> containers, int timeout) {
        NodeUtil.stop(this.controller, containers, timeout);
    }

    @Deprecated
    protected void deploy(String... deployments) {
        deploy(Set.of(deployments));
    }

    protected void deploy() {
        this.deploy(this.deployments);
    }

    protected void deploy(String deployment) {
        NodeUtil.deploy(this.deployer, deployment);
    }

    protected void deploy(Set<String> deployments) {
        NodeUtil.deploy(this.deployer, deployments);
    }

    @Deprecated
    protected void undeploy(String... deployments) {
        undeploy(Set.of(deployments));
    }

    protected void undeploy() {
        this.undeploy(this.deployments);
    }

    protected void undeploy(String deployment) {
        NodeUtil.undeploy(this.deployer, deployment);
    }

    protected void undeploy(Set<String> deployments) {
        NodeUtil.undeploy(this.deployer, deployments);
    }

    protected static String findDeployment(String container) {
        String deployment = CONTAINER_TO_DEPLOYMENT.get(container);
        if (deployment == null) {
            throw new IllegalArgumentException(container);
        }
        return deployment;
    }

    public static int getPortOffsetForNode(String node) {
        switch (node) {
            case NODE_1:
                return 0;
            case NODE_2:
                return 100;
            case NODE_3:
                return 200;
            case NODE_4:
                return 300;
            default:
                throw new IllegalArgumentException();
        }
    }

    static Set<String> toDeployments(Set<String> containers) {
        return containers.stream().map(AbstractClusteringTestCase::findDeployment).collect(Collectors.toSet());
    }

    public interface Lifecycle {
        default void start(String container) {
            this.start(Set.of(container));
        }
        default void stop(String container) {
            this.stop(Set.of(container));
        }
        void start(Set<String> containers);
        void stop(Set<String> containers);
    }

    public class RestartLifecycle implements Lifecycle {
        @Override
        public void start(Set<String> containers) {
            AbstractClusteringTestCase.this.start(containers);
        }

        @Override
        public void stop(Set<String> containers) {
            AbstractClusteringTestCase.this.stop(containers);
        }
    }

    public class GracefulRestartLifecycle extends RestartLifecycle {
        @Override
        public void stop(Set<String> containers) {
            AbstractClusteringTestCase.this.stop(containers, GRACEFUL_SHUTDOWN_TIMEOUT);
        }
    }

    public class RedeployLifecycle implements Lifecycle {
        @Override
        public void start(Set<String> containers) {
            AbstractClusteringTestCase.this.deploy(toDeployments(containers));
        }

        @Override
        public void stop(Set<String> containers) {
            AbstractClusteringTestCase.this.undeploy(toDeployments(containers));
        }
    }
}
