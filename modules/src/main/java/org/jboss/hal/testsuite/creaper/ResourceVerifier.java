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

package org.jboss.hal.testsuite.creaper;

import org.jboss.dmr.ModelNode;
import org.jboss.hal.testsuite.cli.Library;
import org.jboss.hal.testsuite.dmr.ModelNodeGenerator;
import org.jboss.hal.testsuite.dmr.ModelNodeUtils;
import org.jboss.hal.testsuite.util.ConfigUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.extras.creaper.core.online.Constants;
import org.wildfly.extras.creaper.core.online.ModelNodeResult;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.OperationException;
import org.wildfly.extras.creaper.core.online.operations.Operations;

import java.io.IOException;

/**
 * Helper class to verify resource existence and attribute values in model.
 * Created by pjelinek on Nov 16, 2015
 */
public class ResourceVerifier {

    private static final Logger log = LoggerFactory.getLogger(ResourceVerifier.class);
    private static final int DEFAULT_TIMEOUT = Integer.parseInt(ConfigUtils.get("propagate.to.model.timeout", "500"));
    public static final int LONG_TIMEOUT = Integer.parseInt(ConfigUtils.get("propagate.to.model.timeout.long", "6000"));

    private Address resourceAddress;
    private Operations ops;
    private int timeout;

    public ResourceVerifier(Address resourceAddress, OnlineManagementClient client) {
        this(resourceAddress, client, DEFAULT_TIMEOUT);
    }

    /**
     * @param timeout - how long to wait for GUI change to be propagated to model in milis
     */
    public ResourceVerifier(Address resourceAddress, OnlineManagementClient client, int timeout) {
        this.resourceAddress = resourceAddress;
        this.ops = new Operations(client);
        this.timeout = timeout;
    }

    /**
     * Verifies resource exists in model.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyExists(String errorMessageSuffix) throws Exception {
        waitFor(() -> ops.exists(resourceAddress));

        Assert.assertTrue("Resource '" + resourceAddress + "' should exist!" + errorMessageSuffix,
                ops.exists(resourceAddress));
        return this;
    }

    /**
     * Verifies resource exists in model.
     */
    public ResourceVerifier verifyExists() throws Exception {
        return verifyExists("");
    }

    /**
     * Verifies resource doesn't exist in model.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyDoesNotExist(String errorMessageSuffix) throws Exception {
        waitFor(() -> !ops.exists(resourceAddress));

        Assert.assertFalse("Resource '" + resourceAddress + "' should NOT exist! " + errorMessageSuffix,
                ops.exists(resourceAddress));
        return this;
    }

    /**
     * Verifies resource doesn't exist in model.
     */
    public ResourceVerifier verifyDoesNotExist() throws Exception {
        return verifyDoesNotExist("");
    }

    /**
     * Verifies the value of attribute in model.
     * @param expectedValue - to create this parameter value you can use {@link ModelNodeGenerator}
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     * @throws OperationException
     */
    public ResourceVerifier verifyAttribute(String attributeName, final ModelNode expectedValue,
            String errorMessageSuffix) throws Exception {
        waitFor(() -> {
            ModelNodeResult actualResult = ops.readAttribute(resourceAddress, attributeName);
            return actualResult.isSuccess() && actualResult.hasDefinedValue()
                    && expectedValue.equals(actualResult.value());
        });

        ModelNodeResult actualResult = ops.readAttribute(resourceAddress, attributeName);
        actualResult.assertDefinedValue(errorMessageSuffix);
        Assert.assertEquals("Attribute value is different in model! " + errorMessageSuffix,
                expectedValue, actualResult.value());
        return this;
    }

    /**
     * Verifies the value of attribute in model.
     * @param expectedValue - to create this parameter value you can use {@link ModelNodeGenerator}
     */
    public ResourceVerifier verifyAttribute(String attributeName, final ModelNode expectedValue) throws Exception {
        return verifyAttribute(attributeName, expectedValue, "");
    }

    /**
     * Verifies the value of attribute in model.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyAttribute(String attributeName, String expectedValue, String errorMessageSuffix)
            throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue), errorMessageSuffix);
    }

    /**
     * Verifies the value of attribute in model.
     */
    public ResourceVerifier verifyAttribute(String attributeName, String expectedValue) throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue));
    }

    /**
     * Verifies the value of attribute in model.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyAttribute(String attributeName, boolean expectedValue, String errorMessageSuffix)
            throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue), errorMessageSuffix);
    }

    /**
     * Verifies the value of attribute in model.
     */
    public ResourceVerifier verifyAttribute(String attributeName, boolean expectedValue) throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue));
    }

    /**
     * Verifies the value of attribute in model.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyAttribute(String attributeName, int expectedValue, String errorMessageSuffix)
            throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue), errorMessageSuffix);
    }

    /**
     * Verifies the value of attribute in model.
     */
    public ResourceVerifier verifyAttribute(String attributeName, int expectedValue) throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue));
    }

    /**
     * Verifies the value of attribute in model.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyAttribute(String attributeName, long expectedValue, String errorMessageSuffix)
            throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue), errorMessageSuffix);
    }

    /**
     * Verifies the value of attribute in model.
     */
    public ResourceVerifier verifyAttribute(String attributeName, long expectedValue) throws Exception {
        return verifyAttribute(attributeName, new ModelNode(expectedValue));
    }

    /**
     * @throws Exception
     * @throws AssertionError if resource exists and has attribute with attributeName
     * with value equal to notExpectedValue.
     */
    public ResourceVerifier verifyAttributeNotEqual(String attributeName, ModelNode notExpectedValue)
            throws Exception {
        waitFor(() -> !attributeEquals(attributeName, notExpectedValue));

        Assert.assertFalse(attributeName + " should not have value " + notExpectedValue,
                attributeEquals(attributeName, notExpectedValue));
        return this;
    }

    private boolean attributeEquals(String attributeName, ModelNode expectedValue) throws IOException {
        ModelNodeResult actualResult = ops.readAttribute(resourceAddress, attributeName);
        return actualResult.isSuccess() && actualResult.hasDefinedValue() && actualResult.value().equals(expectedValue);
    }

    /**
     * Verifies the value of attribute in model is undefined.
     * @param errorMessagePrefix is intended to be used for e.g. passing related tracked issue.
     */
    public ResourceVerifier verifyAttributeIsUndefined(String attributeName, String errorMessagePrefix) throws Exception {
        waitFor(() -> {
            ModelNodeResult actualResult = ops.readAttribute(resourceAddress, attributeName);
            return actualResult.isSuccess() && !actualResult.hasDefined(Constants.RESULT);
        });

        ops.readAttribute(resourceAddress, attributeName).assertNotDefinedValue(errorMessagePrefix);
        return this;
    }

    /**
     * Verifies the value of attribute in model is undefined.
     */
    public ResourceVerifier verifyAttributeIsUndefined(String attributeName) throws Exception {
        return verifyAttributeIsUndefined(attributeName, null);
    }

    private boolean isModelNodePresentInListAttributeValue(String attributeName, ModelNode value) throws IOException {
        final ModelNodeResult modelNodeResult = ops.readAttribute(resourceAddress, attributeName);
        return ModelNodeUtils.isValuePresentInModelNodeList(modelNodeResult.value(), value);
    }

    /**
     * Verifies that list type attribute contains give value.
     * @param value Value which should be present in the list.
     */
    public ResourceVerifier verifyListAttributeContainsValue(String attributeName, ModelNode value, String errorMessageSuffix)
            throws Exception {
        waitFor(() -> {
            ModelNodeResult actualResult = ops.readAttribute(resourceAddress, attributeName);
            return actualResult.isSuccess() &&
                    actualResult.hasDefined(Constants.RESULT) &&
                    isModelNodePresentInListAttributeValue(attributeName, value);
        });

        final ModelNodeResult modelNodeResult = ops.readAttribute(resourceAddress, attributeName);
        modelNodeResult.assertSuccess();

        Assert.assertTrue("Given value '" + value.toString() + "' is not present in list attribute '" + attributeName
                        + "' with value '" + modelNodeResult.value() + "'!" +
                        (errorMessageSuffix == null || errorMessageSuffix.isEmpty() ? "" : " " + errorMessageSuffix),
                isModelNodePresentInListAttributeValue(attributeName, value));

        return this;
    }



    /**
     * Verifies that list type attribute contains give value.
     * @param value Value which should be present in the list.
     */
    public ResourceVerifier verifyListAttributeContainsValue(String attributeName, ModelNode value) throws Exception {
        return verifyListAttributeContainsValue(attributeName, value, null);
    }

    /**
     * Verifies that list type attribute contains give value.
     * @param value Value which should be present in the list.
     */
    public ResourceVerifier verifyListAttributeContainsValue(String attributeName, String value) throws Exception {
        return verifyListAttributeContainsValue(attributeName, new ModelNode(value));
    }

    /**
     * Verifies that list type attribute contains give value.
     * @param value Value which should be present in the list.
     */
    public ResourceVerifier verifyListAttributeDoesNotContainValue(String attributeName, ModelNode value, String errorMessageSuffix)
            throws Exception {
        waitFor(() -> {
            ModelNodeResult actualResult = ops.readAttribute(resourceAddress, attributeName);
            return actualResult.isSuccess() &&
                    actualResult.hasDefined(Constants.RESULT) &&
                    !isModelNodePresentInListAttributeValue(attributeName, value);
        });

        final ModelNodeResult modelNodeResult = ops.readAttribute(resourceAddress, attributeName);
        modelNodeResult.assertSuccess();

        Assert.assertFalse("Given value '" + value.toString() + "' should not be present in list attribute '" + attributeName + "'!" +
                        (errorMessageSuffix == null || errorMessageSuffix.isEmpty() ? "" : " " + errorMessageSuffix),
                isModelNodePresentInListAttributeValue(attributeName, value));

        return this;
    }

    /**
     * Verifies that list type attribute contains give value.
     * @param value Value which should be present in the list.
     */
    public ResourceVerifier verifyListAttributeDoesNotContainValue(String attributeName, ModelNode value)
            throws Exception {
        return verifyListAttributeDoesNotContainValue(attributeName, value, null);
    }

    private void waitFor(PropagationChecker checker) throws Exception {
        long start = System.currentTimeMillis();
        while (! checker.finallyPropagated() && System.currentTimeMillis() <= start + timeout) {
            log.debug("Not yet propagated therefore waiting.");
            Library.letsSleep(100);
        }
    }

    @FunctionalInterface
    private interface PropagationChecker {
        boolean finallyPropagated() throws Exception;
    }

}
