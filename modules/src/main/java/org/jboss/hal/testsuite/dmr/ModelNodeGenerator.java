/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2016, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.hal.testsuite.dmr;

import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.hal.testsuite.creaper.ResourceVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Intended to help with creating {@link ModelNode} objects to be used e.g. in {@link ResourceVerifier}
 * Created by pjelinek on May 27, 2016
 */
public class ModelNodeGenerator {

    /**
     * @return {@link ModelNode} of type {@link ModelType} {@code OBJECT} including map of just one property from the parameter
     */
    public ModelNode createObjectNodeWithPropertyChild(String firstChildPropertyKey, String firstChildPropertyValue) {
        Map<String, ModelNode> propertyMap = new HashMap<>(1);
        propertyMap.put(firstChildPropertyKey, new ModelNode(firstChildPropertyValue));
        return createObjectNodeWithPropertyChildren(propertyMap);
    }

    /**
     * @return {@link ModelNode} of type {@link ModelType} {@code OBJECT} including map of properties from the parameter
     */
    public ModelNode createObjectNodeWithPropertyChildren(Map<String, ModelNode> childPropertiesMap) {
        ModelNode parent = new ModelNode();
        childPropertiesMap.forEach((propertyKey, propertyValue) -> parent.get(propertyKey).set(propertyValue));
        return parent;
    }

    /**
     * Builder for creating {@link ModelNode} of type {@link ModelType#OBJECT} including map of properties
     */
    public static final class ModelNodePropertiesBuilder {

        private Map<String, ModelNode> propertyMap = new LinkedHashMap<>();

        public ModelNodePropertiesBuilder() { }

        public ModelNodePropertiesBuilder addProperty(String key, ModelNode value) {
            this.propertyMap.put(key, value);
            return this;
        }

        public ModelNodePropertiesBuilder addProperty(String key, String value) {
            return addProperty(key, new ModelNode(value));
        }

        public ModelNodePropertiesBuilder addUndefinedProperty(String key) {
            return addProperty(key, new ModelNode());
        }

        public ModelNode build() {
            if (propertyMap.isEmpty()) {
                throw new IllegalStateException("You have to add any property first!");
            }
            return new ModelNodeGenerator().createObjectNodeWithPropertyChildren(propertyMap);
        }

    }

    /**
     * Builder for creating {@link ModelNode} of type {@link ModelType#LIST}
     */
    public static final class ModelNodeListBuilder {

        private List<ModelNode> nodeList = new ArrayList<>();

        private boolean isEmpty = false;

        public ModelNodeListBuilder() { }

        public ModelNodeListBuilder(ModelNode node) {
            this.nodeList.add(node);
        }

        public ModelNodeListBuilder empty() {
            this.isEmpty = true;
            return this;
        }

        public ModelNodeListBuilder addNode(ModelNode node) {
            this.nodeList.add(node);
            return this;
        }

        public ModelNodeListBuilder addAll(String... values) {
            for (String value : values) {
                this.nodeList.add(new ModelNode(value));
            }
            return this;
        }

        public ModelNode build() {
            ModelNode parent = new ModelNode();
            if (isEmpty) {
                parent.add();
                parent.remove(0);
                return parent; // empty list
            }
            if (nodeList.isEmpty()) {
                throw new IllegalStateException("No child node yet set! You have to either set list as empty or add any"
                        + " ModelNode first!");
            }
            nodeList.forEach(item -> parent.add(item));
            return parent;
        }
    }
}
