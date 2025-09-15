package org.jboss.hal.testsuite.creaper.command;

import org.wildfly.extras.creaper.core.online.OnlineCommand;
import org.wildfly.extras.creaper.core.online.OnlineCommandContext;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;
import org.wildfly.extras.creaper.core.online.operations.Values;

public class AddRemoteDestinationOutboundSocketBinding implements OnlineCommand  {

    private final String socketBindingGroup;
    private final String remoteDestinationOutboundSocketBindingName;

    private final boolean fixedSourcePort;
    private final String host;
    private final int port;
    private final String sourceInterface;
    private final int sourcePort;

    private AddRemoteDestinationOutboundSocketBinding(Builder builder) {
        this.remoteDestinationOutboundSocketBindingName = builder.remoteDestinationOutboundSocketBindingName;
        this.socketBindingGroup = builder.socketBindingGroup;
        this.fixedSourcePort = builder.fixedSourcePort;
        this.host = builder.host;
        this.port = builder.port;
        this.sourceInterface = builder.sourceInterface;
        this.sourcePort = builder.sourcePort;
    }

    public static final class Builder {
        private String socketBindingGroup;
        private final String remoteDestinationOutboundSocketBindingName;
        private boolean fixedSourcePort;
        private String host;
        private int port;
        private String sourceInterface;
        private int sourcePort;

        public Builder(String remoteDestinationOutboundSocketBindingName) {
            this.remoteDestinationOutboundSocketBindingName = remoteDestinationOutboundSocketBindingName;
        }

        public Builder socketBindingGroup(String socketBindingGroup) {
            this.socketBindingGroup = socketBindingGroup;
            return this;
        }

        public Builder fixedSourcePort(boolean value) {
            this.fixedSourcePort = value;
            return this;
        }

        public Builder host(String value) {
            this.host = value;
            return this;
        }

        public Builder port(int value) {
            this.port = value;
            return this;
        }

        public Builder sourceInterface(String value) {
            this.sourceInterface = value;
            return this;
        }

        public Builder sourcePort(int value) {
            this.sourcePort = value;
            return this;
        }

        public AddRemoteDestinationOutboundSocketBinding build() {
            return new AddRemoteDestinationOutboundSocketBinding(this);
        }
    }

    @Override
    public void apply(OnlineCommandContext onlineCommandContext) throws Exception {
        Operations operations = new Operations(onlineCommandContext.client);
        String contextBindingGroup = socketBindingGroup;
        if (contextBindingGroup == null) {
            contextBindingGroup = onlineCommandContext.options.isDomain ? "full-ha-sockets" : "standard-sockets";
        }
        final Address remoteDestinationOutboundSocketSocketBindingAddress = Address.of("socket-binding-group", contextBindingGroup)
                .and("remote-destination-outbound-socket-binding", remoteDestinationOutboundSocketBindingName);
        Values attributes = Values.of("host", host)
                .and("port", port)
                .andOptional("fixed-source-port", fixedSourcePort)
                .andOptional("source-interface", sourceInterface)
                .andOptional("source-port", sourcePort);
        operations.add(remoteDestinationOutboundSocketSocketBindingAddress, attributes).assertSuccess();
    }
}
