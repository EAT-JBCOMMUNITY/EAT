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

package org.jboss.hal.testsuite.util;

import org.jboss.hal.testsuite.creaper.ResourceVerifier;
import org.jboss.hal.testsuite.fragment.ConfigFragment;
import org.jboss.hal.testsuite.fragment.formeditor.Editor;
import org.jboss.hal.testsuite.fragment.shared.modal.WizardWindow;
import org.junit.Assert;
import org.wildfly.extras.creaper.core.online.OnlineManagementClient;
import org.wildfly.extras.creaper.core.online.operations.Address;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Abstraction for editing of {@link ConfigFragment} fields and verification of successful save.
 */
public final class ConfigChecker {

    private final OnlineManagementClient client;
    private final Address resourceAddress;
    private boolean saved;

    /**
     * Verifies that form switch back to read-only mode.
     * @return {@link ResourceVerifier} to facilitate the subsequent verification compared to model
     */
    public ResourceVerifier verifyFormSaved() throws Exception {
        return verifyFormSaved("");
    }

    /**
     * Verifies that form switch back to read-only mode.
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     * @return {@link ResourceVerifier} to facilitate the subsequent verification compared to model
     */
    public ResourceVerifier verifyFormSaved(String errorMessageSuffix) {
        Assert.assertTrue("Configuration should switch into read-only mode! " + errorMessageSuffix, saved);
        return new ResourceVerifier(resourceAddress, client);

    }

    /**
     * Verifies that form doesn't switch back to read-only mode (probably due to validation error).
     * @return {@link ResourceVerifier} to facilitate the subsequent verification compared to model
     */
    public ResourceVerifier verifyFormNotSaved() throws Exception {
        return verifyFormNotSaved("");
    }

    /**
     * Verifies that form doesn't switch back to read-only mode (probably due to validation error).
     * @param errorMessageSuffix is intended to be used for e.g. passing related tracked issue.
     * @return {@link ResourceVerifier} to facilitate the subsequent verification compared to model
     */
    public ResourceVerifier verifyFormNotSaved(String errorMessageSuffix) throws Exception {
        Assert.assertFalse("Configuration should NOT switch into read-only mode. " + errorMessageSuffix, saved);
        return new ResourceVerifier(resourceAddress, client);
    }

    private ConfigChecker(Builder builder)
            throws IOException, InterruptedException, TimeoutException {
        this.client = builder.client;
        this.resourceAddress = builder.resourceAddress;
        WizardWindow wizardWindow = builder.wizardWindow;
        Editor editor = builder.config.edit();
        for (Input input : builder.inputList) {
            enter(editor, input);
        }
        if (wizardWindow != null) {
            this.saved = wizardWindow.saveAndDismissReloadRequiredWindow();
        } else {
            this.saved = builder.config.save();
        }
        if (!this.saved && wizardWindow == null) {
            builder.config.cancel(); // cleanup
        }
    }

    private String getStringAttrValue(Input input) {
        switch (input.inputType) {
            case TEXT: case SELECT:
                if (input.attrValue instanceof String) {
                    return (String) input.attrValue;
                } else if (input.attrValue instanceof Long) {
                    return String.valueOf((long) input.attrValue);
                } else if (input.attrValue instanceof Integer) {
                    return String.valueOf((int) input.attrValue);
                } else {
                    throw new IllegalArgumentException(input.attrValue + " should be String, Integer or Long!");
                }
            default:
                throw new IllegalArgumentException("Not supported inputType: " + input.inputType);
        }
    }

    private boolean getBooleanAttrValue(Input input) {
        switch (input.inputType) {
            case CHECKBOX:
                if (input.attrValue instanceof Boolean) {
                    return (boolean) input.attrValue;
                } else {
                    throw new IllegalArgumentException(input.attrValue + " should be Boolean!");
                }
            default:
                throw new IllegalArgumentException("Not supported inputType: " + input.inputType);
        }
    }

    private void enter(Editor editor, Input input) throws IOException, InterruptedException, TimeoutException {
        switch (input.inputType) {
            case TEXT:
                final String value = getStringAttrValue(input);
                switch (input.inputMethod) {
                    case HUMAN: editor.enterTextLikeHuman(input.identifier, value); break;
                    case MACHINE: editor.text(input.identifier, getStringAttrValue(input)); break;
                } break;
            case CHECKBOX:
                editor.checkbox(input.identifier, getBooleanAttrValue(input)); break;
            case SELECT:
                editor.select(input.identifier, getStringAttrValue(input)); break;
        }
    }

    public enum InputType {
        TEXT, SELECT, CHECKBOX;
    }

    public static class Builder {
        private final OnlineManagementClient client;
        private final Address resourceAddress;
        private ConfigFragment config;
        private WizardWindow wizardWindow;
        private List<Input> inputList;

        public Builder(OnlineManagementClient client, Address resourceAddress) {
            this.client = client;
            this.resourceAddress = resourceAddress;
        }

        /**
         * setter for {@link ConfigFragment}
         */
        public Builder configFragment(ConfigFragment config) {
            this.config = config;
            return this;
        }

        /**
         * Wizard window for use cases when config fragment is inside wizard window which is closed upon clicking on
         * save button. If wizard window remains open, do not use this setter.
         * @param wizardWindow wizard window containing config fragment
         */
        public Builder wizardWindow(WizardWindow wizardWindow) {
            this.wizardWindow = wizardWindow;
            return this;
        }

        /**
         * Set field identified by <b>{@code identifier}</b> to be edited with <b>{@code attrValue}</b>. Can be called
         * multiple times. The actual edit will be performed as soon as client calls the {@link #andSave()} </b>
         */
        public Builder edit(InputType inputType, String identifier, Object attrValue) {
            return edit(inputType, identifier, attrValue, InputMethod.MACHINE);
        }

        /**
         * use this method only if you need to set explicit {@link InputMethod}. Set field identified by
         * <b>{@code identifier}</b> to be edited with <b>{@code attrValue}</b>. Can be called multiple times. The
         * actual edit will be performed as soon as client calls the {@link #andSave()} </b>.
         * Use this method only when {@link #edit(InputType, String, Object)} method is not working properly as a
         * workaround.
         * @param inputMethod Input method to be used. For example InputMethod.HUMAN to edit form like human.
         */
        public Builder edit(InputType inputType, String identifier, Object attrValue, InputMethod inputMethod) {
            if (this.inputList == null) {
                this.inputList = new ArrayList<>();
            }
            this.inputList.add(new Input(inputType, identifier, attrValue, inputMethod));
            return this;
        }

        /**
         * Actually perform fields edit and try to save the form.
         */
        public ConfigChecker andSave() throws IOException, InterruptedException, TimeoutException {
            if (config == null) {
                throw new IllegalStateException("ConfigFragment has to be set!");
            }
            if (inputList == null) {
                throw new IllegalStateException("Input has to be set!");
            }
            return new ConfigChecker(this);
        }

        /**
         * Edits field identified by <b>{@code identifier}</b> with <b>{@code attrValue}</b> and try to save.
         */
        public ConfigChecker editAndSave(InputType inputType, String identifier, Object attrValue) throws IOException,
            InterruptedException, TimeoutException {
            return edit(inputType, identifier, attrValue).andSave();
        }
    }

    public enum InputMethod {
        MACHINE, HUMAN
    }

    private static class Input {
        private InputType inputType;
        private String identifier;
        private Object attrValue;
        private InputMethod inputMethod;

        private Input(InputType inputType, String identifier, Object attrValue, InputMethod inputMethod) {
            this.inputType = inputType;
            this.identifier = identifier;
            this.attrValue = attrValue;
            this.inputMethod = inputMethod;
        }
    }
}
