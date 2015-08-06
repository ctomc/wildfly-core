/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc., and individual contributors as indicated
 * by the @authors tag.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.as.server.controller.resources;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.CAPABILITY_REGISTRY;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.CORE_SERVICE;

import org.jboss.as.controller.ModelController;
import org.jboss.as.controller.ObjectListAttributeDefinition;
import org.jboss.as.controller.ObjectTypeAttributeDefinition;
import org.jboss.as.controller.OperationContext;
import org.jboss.as.controller.OperationFailedException;
import org.jboss.as.controller.OperationStepHandler;
import org.jboss.as.controller.PathElement;
import org.jboss.as.controller.SimpleAttributeDefinition;
import org.jboss.as.controller.SimpleAttributeDefinitionBuilder;
import org.jboss.as.controller.SimpleResourceDefinition;
import org.jboss.as.controller.StringListAttributeDefinition;
import org.jboss.as.controller.capability.registry.CapabilityRegistry;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.server.Services;
import org.jboss.as.server.controller.descriptions.ServerDescriptions;
import org.jboss.dmr.ModelNode;
import org.jboss.dmr.ModelType;
import org.jboss.msc.value.Value;

/**
 * @author Tomaz Cerar (c) 2015 Red Hat Inc.
 */
class CapabilityRegistryResourceDefinition extends SimpleResourceDefinition {


    private static final SimpleAttributeDefinition NAME = SimpleAttributeDefinitionBuilder.create("name", ModelType.STRING)
            .setAllowNull(false)
            .build();

    private static final StringListAttributeDefinition REGISTRATION_POINTS = new StringListAttributeDefinition.Builder("registration-points")
            .build();

    private static final ObjectTypeAttributeDefinition CAPABILITY = new ObjectTypeAttributeDefinition.Builder("capability", NAME, REGISTRATION_POINTS)
            .build();


    private static final ObjectListAttributeDefinition DEFINED_CAPABILITIES = new ObjectListAttributeDefinition.Builder("defined-capabilities", CAPABILITY)
            .build();


    CapabilityRegistryResourceDefinition INSTANCE = new CapabilityRegistryResourceDefinition();

    private CapabilityRegistryResourceDefinition() {
        super(new Parameters(
                        PathElement.pathElement(CORE_SERVICE, CAPABILITY_REGISTRY), ServerDescriptions.getResourceDescriptionResolver("core", CAPABILITY_REGISTRY))
                        .setRuntime()
        );
    }

    @Override
    public void registerAttributes(ManagementResourceRegistration resourceRegistration) {
        resourceRegistration.registerReadOnlyAttribute(DEFINED_CAPABILITIES, new DefinedCapabilitiesAttributeHandler());
    }


    private static class DefinedCapabilitiesAttributeHandler implements OperationStepHandler {

        @Override
        public void execute(OperationContext context, ModelNode operation) throws OperationFailedException {
            final Value<CapabilityRegistry> controllerService = (Value<CapabilityRegistry>) context.getServiceRegistry(false).getService(CapabilityRegistry.SERVICE_NAME);
            assert controllerService != null;
            CapabilityRegistry capabilityRegistry = controllerService.getValue();
            capabilityRegistry.

        }
    }
}
