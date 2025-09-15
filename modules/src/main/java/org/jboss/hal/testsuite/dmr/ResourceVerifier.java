/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
package org.jboss.hal.testsuite.dmr;

import org.jboss.as.controller.descriptions.ModelDescriptionConstants;
import org.jboss.dmr.ModelNode;
import org.jboss.hal.testsuite.cli.Library;

import java.util.Arrays;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_ATTRIBUTE_OPERATION;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.READ_RESOURCE_OPERATION;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Helper class to verify resource (attributes).
 * @author Harald Pehl
 * @deprecated use creaper
 */
public class ResourceVerifier {

    private final Dispatcher dispatcher;

    public ResourceVerifier(final Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }


    // ------------------------------------------------------ resources

    /**
     * Verifies that the given resource exists.
     */
    public void verifyResource(ResourceAddress address) {
        verifyResource(address, true);
    }

    /**
     * Verifies that the given resource exists using the specified timeout.
     */
    public void verifyResource(ResourceAddress address, int timeout) {
        verifyResource(address, true, timeout);
    }

    /**
     * Verifies the given resource against the {@code expected} parameter.
     */
    public void verifyResource(ResourceAddress address, boolean expected) {
        verifyResource(address, expected, 0);
    }

    /**
     * Verifies the given resource against the {@code expected} parameter using the specified timeout.
     */
    public void verifyResource(ResourceAddress address, boolean expected, int timeout) {
        Operation operation = new Operation.Builder(READ_RESOURCE_OPERATION, address).withTimeout(timeout).build();
        assertEquals(expected, dispatcher.execute(operation).isSuccessful());
    }


    // ------------------------------------------------------ attributes

    /**
     * Verifies the attribute against the {@code expected} parameter.
     */
    public void verifyAttribute(ResourceAddress address, String attribute, boolean expected) {
        verifyAttribute(address, attribute, expected, 0);
    }

    /**
     * Verifies the attribute against the {@code expected} parameter using the specified timeout.
     * Trying to get response with expected value until specified timeout expires
     * RECOMMENDATION : It is not good to set timeout bigger than 5000 miliseconds
     */
    public void verifyAttribute(ResourceAddress address, String attribute, boolean expected, int timeout) {
        DmrResponse response = dispatcher.execute(readAttributeOperation(address, attribute));
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() <= start + timeout && expected != response.payload().asBoolean()) {
            response = getResponseAndWait(address, attribute);
        }

        assertTrue(response.isSuccessful());
        assertEquals(expected, response.payload().asBoolean());
    }

    /**
     * Verifies the attribute against the {@code expected} parameter.
     */
    public void verifyAttribute(ResourceAddress address, String attribute, String[] expected) {
        verifyAttribute(address, attribute, expected, 0);
    }

    /**
     * Verifies the attribute against the {@code expected} parameter using the specified timeout.
     * Trying to get response with expected value until specified timeout expires
     * RECOMMENDATION : It is not good to set timeout bigger than 5000 miliseconds
     */
    public void verifyAttribute(ResourceAddress address, String attribute, String[] expected, int timeout) {
        DmrResponse response = dispatcher.execute(readAttributeOperation(address, attribute));
        long start = System.currentTimeMillis();
        String[] values = response.payload().asList().stream().map(ModelNode::asString).toArray(String[]::new);

        while (System.currentTimeMillis() <= start + timeout && !Arrays.equals(expected, values)) {
            response = getResponseAndWait(address, attribute);
            values = response.payload().asList().stream().map(ModelNode::asString).toArray(String[]::new);
        }

        assertTrue(response.isSuccessful());
        assertArrayEquals(expected, values);
    }

    /**
     * Verifies the attribute against the {@code expected} parameter.
     */
    public void verifyAttribute(ResourceAddress address, String attribute, String expected) {
        verifyAttribute(address, attribute, expected, 0);
    }

    /**
     * Verifies the attribute against the {@code expected} parameter using the specified timeout.
     * Trying to get response with expected value until specified timeout expires
     * RECOMMENDATION : It is not good to set timeout bigger than 5000 miliseconds
     */
    public void verifyAttribute(ResourceAddress address, String attribute, String expected, int timeout) {
        DmrResponse response = dispatcher.execute(readAttributeOperation(address, attribute));
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() <= start + timeout && !expected.equals(response.payload().asString())) {
            response = getResponseAndWait(address, attribute);
        }

        assertTrue(response.isSuccessful());
        assertEquals(expected, response.payload().asString());
    }

    private Operation readAttributeOperation(ResourceAddress address, String attribute) {
            return new Operation.Builder(READ_ATTRIBUTE_OPERATION, address)
                    .param(ModelDescriptionConstants.NAME, attribute).build();
    }

    private DmrResponse getResponseAndWait(ResourceAddress address, String attribute) {
        Library.letsSleep(50);
        DmrResponse response = dispatcher.execute(readAttributeOperation(address, attribute));
        return response;
    }
}
