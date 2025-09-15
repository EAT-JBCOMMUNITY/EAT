package org.jboss.hal.testsuite.util;

import org.jboss.dmr.ModelNode;

/**
 * Interface representing an object capable to be represented as {@link ModelNode}
 */
public interface ModelNodeSerializable {

    /**
     * Transforms associated object to {@link ModelNode}
     * @return {@link ModelNode} representation of associated object
     */
    ModelNode toModelNode();
}
