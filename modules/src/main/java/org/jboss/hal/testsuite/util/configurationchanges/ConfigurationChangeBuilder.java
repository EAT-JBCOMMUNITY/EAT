package org.jboss.hal.testsuite.util.configurationchanges;

import java.util.Date;

public class ConfigurationChangeBuilder {
    private Date datetime;
    private String accessMechanism;
    private String remoteAddress;
    private String result;
    private String operation;
    private String resourceAddress;

    public ConfigurationChangeBuilder setDatetime(Date datetime) {
        this.datetime = new Date(datetime.getTime());
        return this;
    }

    public ConfigurationChangeBuilder setAccessMechanism(String accessMechanism) {
        this.accessMechanism = accessMechanism;
        return this;
    }

    public ConfigurationChangeBuilder setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
        return this;
    }

    public ConfigurationChangeBuilder setResult(String result) {
        this.result = result;
        return this;
    }

    public ConfigurationChangeBuilder setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public ConfigurationChangeBuilder setResourceAddress(String resourceAddress) {
        this.resourceAddress = resourceAddress;
        return this;
    }

    public ConfigurationChange build() {
        return new ConfigurationChangeImpl(datetime, accessMechanism, remoteAddress, result, operation, resourceAddress);
    }
}