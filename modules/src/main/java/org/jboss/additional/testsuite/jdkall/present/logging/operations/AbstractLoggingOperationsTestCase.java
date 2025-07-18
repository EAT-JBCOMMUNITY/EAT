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

package org.jboss.additional.testsuite.jdkall.present.logging.operations;

import static org.junit.Assert.*;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jboss.as.arquillian.container.ManagementClient;
import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.client.helpers.Operations;
import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.as.test.integration.management.util.MgmtOperationException;
import org.jboss.dmr.ModelNode;
import org.jboss.eap.additional.testsuite.annotations.EAT;
import org.jboss.logging.Logger;
import org.junit.Assert;

/**
 * @author <a href="mailto:jperkins@redhat.com">James R. Perkins</a>
 */
@EAT({"modules/testcases/jdkAll/WildflyRelease-13.0.0.Final/logging/src/main/java","modules/testcases/jdkAll/Eap72x/logging/src/main/java","modules/testcases/jdkAll/Eap72x-Proposed/logging/src/main/java","modules/testcases/jdkAll/Eap73x/logging/src/main/java","modules/testcases/jdkAll/Eap7Plus/logging/src/main/java","modules/testcases/jdkAll/Eap71x-Proposed/logging/src/main/java","modules/testcases/jdkAll/Eap71x/logging/src/main/java","modules/testcases/jdkAll/Eap7.1.0.Beta/logging/src/main/java","modules/testcases/jdkAll/Eap70x/logging/src/main/java","modules/testcases/jdkAll/Eap70x-Proposed/logging/src/main/java","modules/testcases/jdkAll/WildflyRelease-24.0.0.Final/logging/src/main/java","modules/testcases/jdkAll/Wildfly/logging/src/main/java","modules/testcases/jdkAll/WildflyJakarta/logging/src/main/java#27.0.0.Alpha4","modules/testcases/jdkAll/WildflyRelease-27.0.0.Final/logging/src/main/java","modules/testcases/jdkAll/ServerBeta/logging/src/main/java","modules/testcases/jdkAll/WildflyRelease-17.0.0.Final/logging/src/main/java","modules/testcases/jdkAll/WildflyRelease-20.0.0.Final/logging/src/main/java","modules/testcases/jdkAll/Eap64x/logging/src/main/java","modules/testcases/jdkAll/Eap64x-Proposed/logging/src/main/java","modules/testcases/jdkAll/Eap63x/logging/src/main/java","modules/testcases/jdkAll/Eap62x/logging/src/main/java","modules/testcases/jdkAll/Eap61x/logging/src/main/java","modules/testcases/jdkAll/EapJakarta/logging/src/main/java"})
abstract class AbstractLoggingOperationsTestCase {
    private final Logger log = Logger.getLogger(this.getClass().getName());

    static final PathElement SUBSYSTEM_PATH = PathElement.pathElement(ModelDescriptionConstants.SUBSYSTEM, "logging");

    static PathAddress createAddress(final String resourceKey, final String resourceName) {
        return PathAddress.pathAddress(
                SUBSYSTEM_PATH,
                PathElement.pathElement(resourceKey, resourceName)
        );
    }

    static PathAddress createRootLoggerAddress() {
        return createAddress("root-logger", "ROOT");
    }

    static PathAddress createProfileAddress(final String name) {
        return createAddress("logging-profile", name);
    }

    static PathAddress createCustomHandlerAddress(final String name) {
        return createAddress("custom-handler", name);
    }

    static PathAddress createCustomHandlerAddress(final String profileName, final String name) {
        if (profileName == null) {
            return createAddress("custom-handler", name);
        }
        return createProfileAddress(profileName).append("custom-handler", name);
    }

    static PathAddress createSizeRotatingFileHandlerAddress(final String name) {
        return createAddress("size-rotating-file-handler", name);
    }

    static File getAbsoluteLogFilePath(final ManagementClient client, final String filename) throws IOException, MgmtOperationException {
        final ModelNode address = PathAddress.pathAddress(
                PathElement.pathElement(ModelDescriptionConstants.PATH, "jboss.server.log.dir")
        ).toModelNode();
        final ModelNode op = Operations.createReadAttributeOperation(address, ModelDescriptionConstants.PATH);
        final ModelNode result = client.getControllerClient().execute(op);
        if (Operations.isSuccessfulOutcome(result)) {
            return new File(Operations.readResult(result).asString(), filename);
        }
        throw new MgmtOperationException("Failed to read the path resource", op, result);
    }

    static void safeClose(final Closeable closeable) {
        if (closeable != null) try {
            closeable.close();
        } catch (Exception ignore) {
            // ignore
        }
    }

    static void deleteLogFiles(File file) {
        // avoid NPE
        if (file != null && file.exists()) {
            for (File f : file.getParentFile().listFiles()) {
                if (f.getName().contains(file.getName())) {
                    f.delete();
                }
            }
        }
    }

    ModelNode executeOperation(final ModelNode op) throws IOException {
        ModelNode result = getManagementClient().getControllerClient().execute(op);
        if (!Operations.isSuccessfulOutcome(result)) {
            Assert.assertTrue(Operations.getFailureDescription(result).toString(), false);
        }
        return result;
    }

    protected abstract ManagementClient getManagementClient();

    protected void testWrite(final ModelNode address, final String attribute, final String value) throws IOException {
        final ModelNode writeOp = Operations.createWriteAttributeOperation(address, attribute, value);
        executeOperation(writeOp);
        // Create the read operation
        final ModelNode readOp = Operations.createReadAttributeOperation(address, attribute);
        final ModelNode result = executeOperation(readOp);
        assertEquals(value, Operations.readResult(result).asString());
    }

    protected void testWrite(final ModelNode address, final String attribute, final boolean value) throws IOException {
        final ModelNode writeOp = Operations.createWriteAttributeOperation(address, attribute, value);
        executeOperation(writeOp);
        // Create the read operation
        final ModelNode readOp = Operations.createReadAttributeOperation(address, attribute);
        final ModelNode result = executeOperation(readOp);
        assertEquals(value, Operations.readResult(result).asBoolean());
    }

    protected void testWrite(final ModelNode address, final String attribute, final int value) throws IOException {
        final ModelNode writeOp = Operations.createWriteAttributeOperation(address, attribute, value);
        executeOperation(writeOp);
        // Create the read operation
        final ModelNode readOp = Operations.createReadAttributeOperation(address, attribute);
        final ModelNode result = executeOperation(readOp);
        assertEquals(value, Operations.readResult(result).asInt());
    }

    protected void testWrite(final ModelNode address, final String attribute, final ModelNode value) throws IOException {
        final ModelNode writeOp = Operations.createWriteAttributeOperation(address, attribute, value);
        executeOperation(writeOp);
        // Create the read operation
        final ModelNode readOp = Operations.createReadAttributeOperation(address, attribute);
        final ModelNode result = executeOperation(readOp);
        assertEquals(value, Operations.readResult(result));
    }

    protected void testUndefine(final ModelNode address, final String attribute) throws IOException {
        final ModelNode undefineOp = Operations.createUndefineAttributeOperation(address, attribute);
        executeOperation(undefineOp);
        // Create the read operation
        final ModelNode readOp = Operations.createReadAttributeOperation(address, attribute);
        readOp.get("include-defaults").set(false);
        final ModelNode result = executeOperation(readOp);
        assertFalse("Attribute '" + attribute + "' was not undefined.", Operations.readResult(result)
                .isDefined());
    }

    protected void verifyRemoved(final ModelNode address) throws IOException {
        final ModelNode op = Operations.createReadResourceOperation(address);
        final ModelNode result = getManagementClient().getControllerClient().execute(op);
        assertFalse("Resource not removed: " + address, Operations.isSuccessfulOutcome(result));
    }

    protected void cleanUpRemove(final ModelNode address) {
        final ModelNode op = Operations.createRemoveOperation(address);
        log.info(op.asString());
        try {
            ModelNode result = getManagementClient().getControllerClient().execute(op);
            log.info(result.asString());
        } catch (IOException ioe) {
            log.warn(ioe.getMessage());
        }
    }

    int getResponse(URL url) throws IOException {
        return ((HttpURLConnection) url.openConnection()).getResponseCode();
    }
}
