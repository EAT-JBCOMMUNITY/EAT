package org.jboss.hal.testsuite.util.configurationchanges;

import java.util.List;

/**
 * Interface describing provider for configuration changes
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 9/14/16.
 */
public interface ConfigurationChangesProvider {

    /**
     * Get all configuration changes available from provider
     * @return list of configuration changes
     * @throws Exception
     */
    List<ConfigurationChange> getAllConfigurationChanges() throws Exception;
}
