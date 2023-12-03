/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.additional.testsuite.jdkall.present.clustering;

import static org.jboss.additional.testsuite.jdkall.present.clustering.cluster.AbstractClusteringTestCase.INFINISPAN_SERVER_HOME;
import static org.jboss.additional.testsuite.jdkall.present.clustering.cluster.AbstractClusteringTestCase.INFINISPAN_SERVER_PROFILE;
import static org.jboss.additional.testsuite.jdkall.present.clustering.cluster.AbstractClusteringTestCase.INFINISPAN_SERVER_PROFILE_DEFAULT;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;

import org.infinispan.server.test.core.ServerRunMode;
import org.infinispan.server.test.core.TestSystemPropertyNames;
import org.infinispan.server.test.junit4.InfinispanServerRule;
import org.infinispan.server.test.junit4.InfinispanServerRuleBuilder;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.AbstractClusteringTestCase;
import org.junit.rules.TestRule;

/**
 * @author Radoslav Husar
 */
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java"})
public class InfinispanServerUtil {

    public static final InfinispanServerRule INFINISPAN_SERVER_RULE;

    static {
        String profile = (INFINISPAN_SERVER_PROFILE == null || INFINISPAN_SERVER_PROFILE.isEmpty()) ? INFINISPAN_SERVER_PROFILE_DEFAULT : INFINISPAN_SERVER_PROFILE;
        // Workaround for "ISPN-13107 ServerRunMode.FORKED yields InvalidPathException with relative server config paths on Windows platform" by using absolute file path which won't get mangled.
        String absoluteConfigurationFile = null;
        try {
            absoluteConfigurationFile = Paths.get(Objects.requireNonNull(AbstractClusteringTestCase.class.getClassLoader().getResource(profile)).toURI()).toFile().toString();
        } catch (URISyntaxException ignore) {
        }

        INFINISPAN_SERVER_RULE = InfinispanServerRuleBuilder
                .config(absoluteConfigurationFile)
                .property(TestSystemPropertyNames.INFINISPAN_TEST_SERVER_DIR, INFINISPAN_SERVER_HOME)
                .property("infinispan.client.rest.auth_username", "testsuite-driver-user")
                .property("infinispan.client.rest.auth_password", "testsuite-driver-password")
                .numServers(1)
                .runMode(ServerRunMode.FORKED)
                .build();
    }

    public static TestRule infinispanServerTestRule() {
        return INFINISPAN_SERVER_RULE;
    }

}
