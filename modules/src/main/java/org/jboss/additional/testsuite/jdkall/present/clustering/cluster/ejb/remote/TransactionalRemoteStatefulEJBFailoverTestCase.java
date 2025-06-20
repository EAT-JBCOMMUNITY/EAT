/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2017, Red Hat, Inc., and individual contributors
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

package org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.remote;

import java.util.PropertyPermission;

import javax.ejb.NoSuchEJBException;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.container.test.api.TargetsContainer;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ClusterAbstractTestCase;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.remote.bean.Incrementor;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.remote.bean.IncrementorBean;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.remote.bean.Result;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.remote.bean.StatefulIncrementorBean;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.EJBDirectory;
import org.jboss.additional.testsuite.jdkall.present.clustering.cluster.ejb.RemoteEJBDirectory;
import org.jboss.as.test.shared.integration.ejb.security.PermissionUtils;
import org.jboss.ejb.client.EJBClient;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * Validates inhibition of failover behavior of a remotely accessed @Stateful EJB within the context of a transaction.
 * @author Paul Ferraro
 */
@RunWith(Arquillian.class)
@RunAsClient
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/clustering/src/main/java","modules/testcases/jdkAll/Wildfly/clustering/src/main/java#11.0.0.Final*12.0.0.Final","modules/testcases/jdkAll/Eap72x/clustering/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/clustering/src/main/java","modules/testcases/jdkAll/Eap73x/clustering/src/main/java","modules/testcases/jdkAll/Eap7Plus/clustering/src/main/java#7.0.0*7.4.0","modules/testcases/jdkAll/Eap71x-Proposed/clustering/src/main/java","modules/testcases/jdkAll/Eap71x/clustering/src/main/java"})
public class TransactionalRemoteStatefulEJBFailoverTestCase extends ClusterAbstractTestCase {
    private static final String MODULE_NAME = "transactional-remote-stateful-ejb-failover-test";

    @Deployment(name = DEPLOYMENT_1, managed = false, testable = false)
    @TargetsContainer(CONTAINER_1)
    public static Archive<?> createDeploymentForContainer1() {
        return createDeployment();
    }

    @Deployment(name = DEPLOYMENT_2, managed = false, testable = false)
    @TargetsContainer(CONTAINER_2)
    public static Archive<?> createDeploymentForContainer2() {
        return createDeployment();
    }

    private static Archive<?> createDeployment() {
        return ShrinkWrap.create(JavaArchive.class, MODULE_NAME + ".jar")
                .addPackage(EJBDirectory.class.getPackage())
                .addClasses(Result.class, Incrementor.class, IncrementorBean.class, StatefulIncrementorBean.class)
                .addAsManifestResource(PermissionUtils.createPermissionsXmlAsset(new PropertyPermission(NODE_NAME_PROPERTY, "read")), "permissions.xml")
                ;
    }

    @Test
    public void test() throws Exception {
        try (EJBDirectory directory = new RemoteEJBDirectory(MODULE_NAME)) {
            Incrementor bean = directory.lookupStateful(StatefulIncrementorBean.class, Incrementor.class);

            Result<Integer> result = bean.increment();
            // Bean should retain weak affinity for this node
            String target = result.getNode();
            int count = 1;

            Assert.assertEquals(count++, result.getValue().intValue());

            // Validate that multi-invocations function correctly within a tx
            UserTransaction tx = EJBClient.getUserTransaction(target);
            // TODO Currently requires environment to be configured with provider URLs.
            // UserTransaction tx = directory.lookupUserTransaction();
            tx.begin();

            result = bean.increment();
            Assert.assertEquals(count++, result.getValue().intValue());
            Assert.assertEquals(target, result.getNode());

            result = bean.increment();
            Assert.assertEquals(count++, result.getValue().intValue());
            Assert.assertEquals(target, result.getNode());

            tx.commit();

            // Validate that second invocation fails if target node is undeployed in middle of tx
            tx.begin();

            result = bean.increment();
            Assert.assertEquals(count++, result.getValue().intValue());
            Assert.assertEquals(target, result.getNode());

            undeploy(this.findDeployment(target));

            try {
                result = bean.increment();

                Assert.fail("Expected a NoSuchEJBException");
            } catch (NoSuchEJBException e) {
                // Expected
            } finally {
                tx.rollback();
            }
        }
    }
}
