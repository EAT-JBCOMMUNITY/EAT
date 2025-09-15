package org.jboss.hal.testsuite.util;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.wildfly.extras.creaper.core.online.ModelNodeResult;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Operations;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static org.jboss.as.cli.Util.ADDRESS;
import static org.jboss.as.cli.Util.RESULT;

/**
 * Class providing suggestions for given resources identified by {@link Address} templates. The last element in last
 * pair of template address has to be a star symbol '{@code *}'.
 * E.g. '{@code  /socket-binding-group=standard-sockets/socket-binding=*}'.
 *
 * @author Jan Kasik <jkasik@redhat.com>
 *         Created on 8/22/16.
 */
public class SuggestionResource {

    private final OnlineManagementClient client;
    private final List<Address> templates;

    private SuggestionResource(Builder builder) {
        this.client = builder.client;
        this.templates = builder.templates;
    }

    /**
     * Reads items from model which should be suggested for given templates
     * @return list of items which should be suggested when no filter is applied
     * @throws IOException
     */
    public List<String> readSuggestions() throws IOException {
        final List<String> suggestions = new LinkedList<>();
        for (Address template : templates) {
            readResource(template).get(RESULT).asList().stream()
                .filter(step -> step.get(ADDRESS).isDefined())
                .map(step -> formatAddressName(step.get(ADDRESS)))
                .forEachOrdered(suggestions::add);
        }
        return suggestions;
    }

    private ModelNodeResult readResource(Address template) throws IOException {
        return new Operations(client).readResource(template);
    }

    private String formatAddressName(ModelNode address) {
        final String EQUALS = " = ";
        final String SLASH = " / ";

        final StringBuilder stringBuilder = new StringBuilder();

        final List<ModelNode> addressParts = address.asList();

        for (int i = 0; i < addressParts.size(); i++) {
            ModelNode addressPart = addressParts.get(i);
            Property property = addressPart.asProperty();
            stringBuilder.append(property.getName())
                    .append(EQUALS)
                    .append(property.getValue().asString());
            if (i + 1 < addressParts.size()) {
                stringBuilder.append(SLASH);
            }
        }
        return stringBuilder.toString();
    }

    public static class Builder {

        private OnlineManagementClient client;
        private List<Address> templates;

        public Builder() {
        }

        /**
         * Set client used for obtaining options from model
         * @param client non null {@link OnlineManagementClient}
         */
        public Builder withClient(OnlineManagementClient client) {
            this.client = client;
            return this;
        }

        /**
         * Adds an {@link Address} template from which the suggestion strings will be loaded. The last element in last
         * pair of address has to be a star symbol '*'. E.g.: '/socket-binding-group=standard-sockets/socket-binding=*'.
         * @param template an address template.
         */
        public Builder template(Address template) {
            if (this.templates == null) {
                this.templates = new LinkedList<>();
            }
            this.templates.add(template);
            return this;
        }

        private void validate() {
            if (client == null) {
                throw new IllegalArgumentException("Client is not defined!");
            }

            if (templates == null) {
                throw new IllegalArgumentException("No template is defined!");
            }
        }

        public SuggestionResource build() {
            validate();
            return new SuggestionResource(this);
        }
    }
}
