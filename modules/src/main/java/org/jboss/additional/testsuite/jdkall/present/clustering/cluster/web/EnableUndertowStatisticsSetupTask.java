/*
 * Copyright The WildFly Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.web;

import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.AbstractClusteringTestCase;
import org.jboss.as.test.shared.CLIServerSetupTask;

/**
 * Task that enable Undertow statistics.
 * @author Paul Ferraro
 */
import org.jboss.eap.additional.testsuite.annotations.EAT;

@EAT({"modules/testcases/jdkAll/WildflyJakarta/clustering/src/main/java"})
public class EnableUndertowStatisticsSetupTask extends CLIServerSetupTask {

    public EnableUndertowStatisticsSetupTask() {
        this.builder.node(AbstractClusteringTestCase.FOUR_NODES)
                .setup("/subsystem=undertow:write-attribute(name=statistics-enabled, value=true)")
                .teardown("/subsystem=undertow:undefine-attribute(name=statistics-enabled)")
                ;
    }
}
