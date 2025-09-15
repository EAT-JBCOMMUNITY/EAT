/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2015, Red Hat, Inc., and individual contributors
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

package org.jboss.hal.testsuite.util;

import org.jboss.dmr.ModelNode;
import org.jboss.hal.testsuite.cli.CliClient;
import org.jboss.hal.testsuite.cli.CliUtils;
import org.junit.Assert;

import java.util.Map;

/**
 * Created by pjelinek on Apr 7, 2015
 * @deprecated Should be replaced with {@link org.jboss.hal.testsuite.dmr.ResourceVerifier}
 */
@Deprecated
public class ResourceVerifier {

    private String dmrPath;
    private CliClient cliClient;

    public ResourceVerifier(String dmrPath, CliClient cliClient) {
        if (cliClient == null) {
            throw new IllegalArgumentException("Management client not set.");
        }
        this.cliClient = cliClient;
        setDmrPath(dmrPath);
    }

    /**
     * Verifies that resource on given path exists in model
     *
     * @param dmrPath dmr address of the resource
     * @param expected <code>true</code> if resource is expected to exists, false otherwise
     */
    public void verifyResource(String dmrPath, boolean expected) {
        verifyResource(dmrPath, expected, 0);
    }

    public void verifyResource(String dmrPath, boolean expected, int timeout) {
        boolean exists = cliClient.executeForSuccess(dmrPath + ":read-resource()", timeout);
        if (expected) {
            Assert.assertTrue("Resource " + dmrPath + " should exist", exists);
        } else {
            Assert.assertFalse("Resource " + dmrPath + " should not exist", exists);
        }
    }

    /**
     * Verifies that resource on set path path exists in model
     *
     * @param expected <code>true</code> if resource is expected to exists, false otherwise
     */
    public void verifyResource(boolean expected) {
        verifyResource(expected, 0);
    }

    public void verifyResource(boolean expected, int timeout) {
        if (dmrPath == null) {
            throw new IllegalStateException("DMR path not set");
        }
        verifyResource(dmrPath, expected, timeout);
    }

    /**
     * Verifies the value of attribute in model.
     *
     * @param name name of the attribute. If the name is camelCase it will be converted to camel-case.
     * @param expectedValue expected value
     */
    public void verifyAttribute(String name, String expectedValue) {
        verifyAttribute(name, expectedValue, 0);
    }

    public void verifyAttribute(String name, String expectedValue, int timeout) {
        if (dmrPath == null) {
            throw new IllegalStateException("DMR path not set");
        }

        String dmrName = camelToDash(name);
        String actualValue = cliClient.readAttribute(dmrPath, dmrName, timeout);

        Assert.assertEquals("Attribute value is different in model.", expectedValue, actualValue);
    }

    /**
     * Verifies the value of array attribute in model.
     * @param name name of the attribute. If the name is camelCase it will be converted to camel-case.
     * @param expectedValues expected values
     */
    public void verifyAttribute(String name, String[] expectedValues) {
        if (dmrPath == null) {
            throw new IllegalStateException("DMR path not set");
        }

        String dmrName = camelToDash(name);
        String[] actualValues = cliClient
                .executeForResponse(CliUtils.buildCommand(dmrPath, ":read-attribute", new String[]{"name=" + dmrName}))
                .get("result").asList().stream().map(ModelNode::asString).toArray(String[]::new);

        Assert.assertArrayEquals(expectedValues, actualValues);
    }

    /**
     * Verify the value of attributes against model
     *
     * @param pairs Key-Value map of attribute names and values. If name is camelCase it will be coverted to camel-case
     */
    public void verifyAttributes(Map<String, String> pairs) {
        for (Map.Entry<String, String> p : pairs.entrySet()) {
            verifyAttribute(p.getKey(), p.getValue());
        }
    }

    public void setDmrPath(String dmrPath) {
        this.dmrPath = dmrPath;
    }

    /**
     * Converts a camelCaseString to camel-case-string
     *
     * @param input
     * @return
     */
    private static String camelToDash(String input) {
        return input.replaceAll("\\B([A-Z])", "-$1" ).toLowerCase();
    }
}
