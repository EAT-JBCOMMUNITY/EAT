/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.web.remote;

import org.infinispan.transaction.TransactionMode;
import org.jboss.additional.testsuite.jdkall.present.clustering.InfinispanServerUtil;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.expiration.SessionExpirationTestCase;
import org.junit.ClassRule;
import org.junit.rules.TestRule;

/**
 * @author Paul Ferraro
 */
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java"})
public abstract class AbstractHotRodSessionExpirationTestCase extends SessionExpirationTestCase {

    @ClassRule
    public static final TestRule INFINISPAN_SERVER_RULE = InfinispanServerUtil.infinispanServerTestRule();

    public AbstractHotRodSessionExpirationTestCase() {
        super(TransactionMode.NON_TRANSACTIONAL);
    }
}
