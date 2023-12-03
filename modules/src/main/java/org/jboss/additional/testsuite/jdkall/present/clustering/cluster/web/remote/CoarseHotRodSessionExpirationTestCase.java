/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.web.remote;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * @author Paul Ferraro
 */
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java"})
@ServerSetup({ InfinispanServerSetupTask.class, LocalRoutingServerSetup.class })
public class CoarseHotRodSessionExpirationTestCase extends AbstractHotRodSessionExpirationTestCase {

    private static final String MODULE_NAME = CoarseHotRodSessionExpirationTestCase.class.getSimpleName();

    @Deployment(name = DEPLOYMENT_1, managed = false, testable = false)
    @TargetsContainer(NODE_1)
    public static Archive<?> deployment0() {
        return getDeployment();
    }

    @Deployment(name = DEPLOYMENT_2, managed = false, testable = false)
    @TargetsContainer(NODE_2)
    public static Archive<?> deployment1() {
        return getDeployment();
    }

    static WebArchive getDeployment() {
        return getBaseDeployment(MODULE_NAME)
                .addAsWebInfResource("jboss-all_coarse_transactional.xml", "jboss-all.xml")
                .addAsWebInfResource("jboss-web.xml", "jboss-web.xml")
                ;
    }
}
