package org.jboss.hal.testsuite.util.configurationchanges;

import java.util.Date;

class ConfigurationChangeImpl implements ConfigurationChange {

    private Date datetime;
    private String accessMechanism;
    private String remoteAddress;
    private String outcome;
    private String operation;
    private String resourceAddress;

    ConfigurationChangeImpl(Date datetime, String accessMechanism, String remoteAddress, String outcome,
                            String operation, String resourceAddress) {
        this.datetime = datetime;
        this.accessMechanism = accessMechanism;
        this.remoteAddress = remoteAddress;
        this.outcome = outcome;
        this.operation = operation;
        this.resourceAddress = resourceAddress;
    }

    @Override
    public Date getDatetime() {
        return datetime;
    }

    @Override
    public String getAccessMechanism() {
        return accessMechanism;
    }

    @Override
    public String getRemoteAddress() {
        return remoteAddress;
    }

    @Override
    public String getOperation() {
        return operation;
    }

    @Override
    public String getResult() {
        return outcome;
    }

    @Override
    public String getResourceAddress() {
        return resourceAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationChangeImpl that = (ConfigurationChangeImpl) o;

        if (datetime != null ? !datetime.equals(that.datetime) : that.datetime != null) return false;
        if (accessMechanism != null ? !accessMechanism.equals(that.accessMechanism) : that.accessMechanism != null)
            return false;
        if (remoteAddress != null ? !remoteAddress.equals(that.remoteAddress) : that.remoteAddress != null)
            return false;
        if (outcome != null ? !outcome.equals(that.outcome) : that.outcome != null) return false;
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
        return resourceAddress != null ? resourceAddress.equals(that.resourceAddress) : that.resourceAddress == null;
    }

    @Override
    public int hashCode() {
        int result = datetime != null ? datetime.hashCode() : 0;
        result = 31 * result + (accessMechanism != null ? accessMechanism.hashCode() : 0);
        result = 31 * result + (remoteAddress != null ? remoteAddress.hashCode() : 0);
        result = 31 * result + (outcome != null ? outcome.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (resourceAddress != null ? resourceAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ConfigurationChangeImpl{" +
                "datetime=" + datetime +
                ", accessMechanism='" + accessMechanism + '\'' +
                ", remoteAddress='" + remoteAddress + '\'' +
                ", outcome='" + outcome + '\'' +
                ", operation='" + operation + '\'' +
                ", resourceAddress='" + resourceAddress + '\'' +
                '}';
    }
}
