package org.jboss.hal.testsuite.creaper.command;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.Property;
import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.extras.creaper.core.CommandFailedException;
import org.wildfly.extras.creaper.core.online.OnlineCommand;
import org.wildfly.extras.creaper.core.online.OnlineCommandContext;
import org.wildfly.extras.creaper.core.online.operations.Address;
import org.wildfly.extras.creaper.core.online.operations.Batch;
import org.wildfly.extras.creaper.core.online.operations.Operations;
import org.wildfly.extras.creaper.core.online.operations.ReadResourceOption;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>A command for making backups and restores of attributes at given address.</p>
 * <p>Backup does not descend to children, it backups attributes only at given address!</p>
 */
public final class BackupAndRestoreAttributes {

    private final Address address;
    private final DirectedAcyclicGraph<String, String> dependencies;
    private final Set<String> excluded;

    private final Logger logger = LoggerFactory.getLogger(BackupAndRestoreAttributes.class);

    private ModelNode backup;

    private BackupAndRestoreAttributes(Builder builder) {
        this.address = builder.address;
        this.dependencies = builder.dependencies;
        this.excluded = builder.excluded;
    }

    private final OnlineCommand backupPart = new OnlineCommand() {

        @Override
        public void apply(OnlineCommandContext ctx) throws Exception {
            if (BackupAndRestoreAttributes.this.backup != null) {
                throw new CommandFailedException("Backup has been already made!");
            }
            Operations ops = new Operations(ctx.client);

            BackupAndRestoreAttributes.this.backup = ops.readResource(address, ReadResourceOption.INCLUDE_DEFAULTS, ReadResourceOption.ATTRIBUTES_ONLY).value();
        }
    };

    private final OnlineCommand restorePart = new OnlineCommand() {

        private final Batch batch = new Batch();

        private Map<String, ModelNode> propertyListToMap(List<Property> propertyList) {
            Map<String, ModelNode> attributeMap = new HashMap<>();
            for (Property property : propertyList) {
                logger.info("Adding '" + property.getName() + "' to map.");
                attributeMap.put(property.getName(), property.getValue());
            }
            return attributeMap;
        }


        @Override
        public void apply(OnlineCommandContext ctx) throws Exception {
            if (BackupAndRestoreAttributes.this.backup == null) {
                throw new CommandFailedException("There is no backup to be restored!");
            }

            Map<String, ModelNode> attributeValueMap = propertyListToMap(BackupAndRestoreAttributes.this.backup.asPropertyList());

            if (dependencies != null) { //process dependencies
                Iterator<String> dependencyIterator = dependencies.iterator();
                while (dependencyIterator.hasNext()) {
                    String attributeName = dependencyIterator.next();
                    if (!attributeValueMap.containsKey(attributeName))
                        throw new CommandFailedException("Attribute '" + attributeName + "' is not present or it was previously added and removed!");

                    logger.info("Adding dependency '" + attributeName + "' to batch.");
                    batch.writeAttribute(address,
                            attributeName,
                            attributeValueMap.get(attributeName));
                    attributeValueMap.remove(attributeName);
                }
            }

            for (Map.Entry<String, ModelNode> attributeEntry : attributeValueMap.entrySet()) {
                if (excluded == null || !excluded.contains(attributeEntry.getKey())) { //attribute is not excluded
                    logger.info("Adding attribute '" + attributeEntry + "' to batch.");
                    batch.writeAttribute(address, attributeEntry.getKey(), attributeEntry.getValue());
                }
            }

            Operations ops = new Operations(ctx.client);
            ops.batch(batch);

            BackupAndRestoreAttributes.this.backup = null; //can be reused after restoring attributes
        }

    };

    public static final class Builder {

        private Address address;
        private DirectedAcyclicGraph<String, String> dependencies;
        private Set<String> excluded;

        public Builder(Address address) {
            this.address = address;
        }

        /**
         * Add dependency. Any dependency and dependent attribute will be restored even if it is present in excluded list.
         */
        public Builder dependency(String attribute, String dependsOn) {
            if (dependencies == null && dependsOn != null) {
                dependencies = new DirectedAcyclicGraph<>(String.class);
            }
            if (dependencies != null) {
                dependencies.addVertex(attribute);
                dependencies.addVertex(dependsOn);
                dependencies.addEdge(dependsOn, attribute); //throws an unchecked exception if graph becomes cyclic after adding
            }
            return this;
        }

        /**
         * Add attribute which will be excluded from restoring
         */
        public Builder excluded(String attribute) {
            if (excluded == null && attribute != null) {
                excluded = new HashSet<>();
            }
            if (attribute != null) {
                excluded.add(attribute);
            }
            return this;
        }

        public BackupAndRestoreAttributes build() {
            return new BackupAndRestoreAttributes(this);
        }

    }

    public OnlineCommand backup() {
        return backupPart;
    }

    public OnlineCommand restore() {
        return restorePart;
    }

}
