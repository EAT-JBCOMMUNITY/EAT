package org.jboss.hal.testsuite.util.configurationchanges;

import java.util.Date;

/**
 * Interface describing public methods of both UI and model abstraction over configuration changes.
 */
public interface ConfigurationChange {

    /**
     * Get date stamp of change
     * @return timestamp of change
     */
    Date getDatetime() throws Exception;

    /**
     * Access mechanism of change
     * @return access mechanism of change
     */
    String getAccessMechanism();

    /**
     * Remote address of change
     * @return remote address of change
     */
    String getRemoteAddress();

    /**
     * Resource address of change
     * @return resource address of change
     */
    String getResourceAddress();

    /**
     * Operation which was performed within change
     * @return operation name
     */
    String getOperation();

    /**
     * Result of change
     * @return was it successful or not
     */
    String getResult();
}
