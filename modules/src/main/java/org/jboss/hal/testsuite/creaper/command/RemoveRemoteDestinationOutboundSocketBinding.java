package org.jboss.hal.testsuite.creaper.command;

import org.wildfly.extras.creaper.core.CommandFailedException;
import org.wildfly.extras.creaper.core.online.OnlineCommand;
import org.wildfly.extras.creaper.core.online.OnlineCommandContext;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;

import java.io.IOException;

public class RemoveRemoteDestinationOutboundSocketBinding implements OnlineCommand {
    private final String remoteDestinationOutboundSocketBinding;
    private final String socketBindingGroup;

    public RemoveRemoteDestinationOutboundSocketBinding(String socketGroup, String remoteDestinationOutboundSocketBinding) {
        this.socketBindingGroup = socketGroup;
        this.remoteDestinationOutboundSocketBinding = remoteDestinationOutboundSocketBinding;
    }

    public RemoveRemoteDestinationOutboundSocketBinding(String remoteDestinationOutboundSocketBinding) {
        this(null, remoteDestinationOutboundSocketBinding);
    }

    @Override
    public void apply(OnlineCommandContext onlineCommandContext) throws Exception {
        Operations operations = new Operations(onlineCommandContext.client);
        String contextSocketBindingGroup = socketBindingGroup;
        if (contextSocketBindingGroup == null) {
            contextSocketBindingGroup = onlineCommandContext.options.isDomain ? "full-sockets" : "standard-sockets";
        }
        final Address remoteDestinationOutboundSocketBindingAddress = Address.of("socket-binding-group", contextSocketBindingGroup)
                .and("remote-destination-outbound-socket0-binding", remoteDestinationOutboundSocketBinding);
        try {
            operations.removeIfExists(remoteDestinationOutboundSocketBindingAddress);
        } catch (IOException e) {
            throw new CommandFailedException(e);
        }
    }
}
