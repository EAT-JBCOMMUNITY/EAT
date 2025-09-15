package org.jboss.hal.testsuite.dmr;

import org.jboss.dmr.ModelNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Class wrapping common helper utils for model nodes
 */
public class ModelNodeUtils {

    private static final Logger log = LoggerFactory.getLogger(ModelNodeUtils.class);

    /**
     * Checks whether model node list contains given value
     * @param list List to be checked
     * @param value Value to find
     * @return true if value was found, false otherwise
     */
    public static boolean isValuePresentInModelNodeList(ModelNode list, ModelNode value) throws IOException {
        return list.asList().stream()
                .peek(modelNode -> log.debug("Comparing '{}' with list member '{}'.", value.toString(), modelNode.toString()))
                .anyMatch(modelNode -> modelNode.equals(value));
    }
}
