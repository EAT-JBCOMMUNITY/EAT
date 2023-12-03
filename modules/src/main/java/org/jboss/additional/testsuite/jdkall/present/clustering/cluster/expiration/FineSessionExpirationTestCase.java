/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */
package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.expiration;

import org.infinispan.transaction.TransactionMode;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java"})
public class FineSessionExpirationTestCase extends SessionExpirationTestCase {

    private static final String MODULE_NAME = FineSessionExpirationTestCase.class.getSimpleName();

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
        return getBaseDeployment(MODULE_NAME).addAsWebInfResource("jboss-web-fine.xml", "jboss-web.xml");
    }

    public FineSessionExpirationTestCase() {
        super(TransactionMode.TRANSACTIONAL);
    }
}
