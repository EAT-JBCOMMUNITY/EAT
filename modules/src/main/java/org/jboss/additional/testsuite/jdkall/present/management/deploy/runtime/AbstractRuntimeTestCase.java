/*
 * JBoss, Home of Professional Open Source.
 * Copyright (c) 2013, Red Hat, Inc., and individual contributors
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
package org.jboss.additional.testsuite.jdkall.present.management.deploy.runtime;

import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.controller.operations.common.Util;
import org.jboss.dmr.ModelNode;
import org.jboss.eap.additional.testsuite.annotations.EAT;

/**
 * @author <a href="mailto:cdewolf@redhat.com">Carlo de Wolf</a>
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Eap72x/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap73x/management/src/main/java","modules/testcases/jdkAll/Eap7Plus/management/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap71x/management/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/management/src/main/java","modules/testcases/jdkAll/Eap70x/management/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Wildfly/management/src/main/java","modules/testcases/jdkAll/ServerBeta/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/management/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/management/src/main/java","modules/testcases/jdkAll/Eap64x/management/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/management/src/main/java","modules/testcases/jdkAll/Eap63x/management/src/main/java","modules/testcases/jdkAll/Eap62x/management/src/main/java","modules/testcases/jdkAll/Eap61x/management/src/main/java"})
public class AbstractRuntimeTestCase {
    protected static ModelNode composite(final ModelNode... operations) {
        final ModelNode compositeOperation = Util.getEmptyOperation(ModelDescriptionConstants.COMPOSITE, new ModelNode());
        final ModelNode steps = compositeOperation.get(ModelDescriptionConstants.STEPS);
        for (ModelNode operation : operations) {
            steps.add(operation);
        }
        return compositeOperation;
    }

    protected static ModelNode remove(final String name) {
        return Util.getEmptyOperation(ModelDescriptionConstants.REMOVE, new ModelNode().add(ModelDescriptionConstants.DEPLOYMENT, name));
    }

    protected static ModelNode undeploy(final String name) {
        return Util.getEmptyOperation(ModelDescriptionConstants.UNDEPLOY, new ModelNode().add(ModelDescriptionConstants.DEPLOYMENT, name));
    }
}
