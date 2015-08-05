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

package org.jboss.as.controller.capability.registry;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.as.controller.PathAddress;
import org.jboss.as.controller.capability.Capability;
import org.jboss.as.controller.logging.ControllerLogger;

/**
 * @author Tomaz Cerar (c) 2015 Red Hat Inc.
 */
public abstract class DefinedCapabilityRegistry<C extends CapabilityRegistration, R extends RequirementRegistration> implements CapabilityRegistry<C, R> {

    private final Map<CapabilityId, CapabilityRegistration> capabilities = new ConcurrentHashMap<>();


    @Override
    public void registerCapabilityDefinition(Capability capability, PathAddress registrationPoint) {
        final CapabilityId capabilityId = new CapabilityId(capability.getName(), CapabilityContext.GLOBAL);
        RegistrationPoint point = new RegistrationPoint(registrationPoint, null);
        CapabilityRegistration capabilityRegistration = new CapabilityRegistration<>(capability, CapabilityContext.GLOBAL, point);


        capabilities.computeIfPresent(capabilityId, (capabilityId1, currentRegistration) -> {
            RegistrationPoint rp = capabilityRegistration.getOldestRegistrationPoint();
            // The actual capability must be the same, and we must not already have a registration
            // from this resource
            if (!Objects.equals(capabilityRegistration.getCapability(), currentRegistration.getCapability())
                    || !currentRegistration.addRegistrationPoint(rp)) {
                throw ControllerLogger.MGMT_OP_LOGGER.capabilityAlreadyRegisteredInContext(capabilityId.getName(),
                        capabilityId.getContext().getName());
            }
            return capabilityRegistration;
        });
        capabilities.putIfAbsent(capabilityId, capabilityRegistration);

        /*RegistrationPoint rp = capabilityRegistration.getOldestRegistrationPoint();
        CapabilityRegistration currentRegistration = capabilities.get(capabilityId);
        if (currentRegistration != null) {
            // The actual capability must be the same, and we must not already have a registration
            // from this resource
            if (!Objects.equals(capabilityRegistration.getCapability(), currentRegistration.getCapability())
                    || !currentRegistration.addRegistrationPoint(rp)) {
                throw ControllerLogger.MGMT_OP_LOGGER.capabilityAlreadyRegisteredInContext(capabilityId.getName(),
                        capabilityId.getContext().getName());
            }
            // else it was ok, and we just recorded the additional registration point
        } else {
            capabilities.putIfAbsent(capabilityId, capabilityRegistration);
        }*/

    }

    @Override
    public CapabilityRegistration removeCapabilityDefinition(String capabilityName, CapabilityContext context, PathAddress registrationPoint) {
        return capabilities.remove(new CapabilityId(capabilityName, context));
    }

    public Set<RegistrationPoint> getPossibleProviderPoints(CapabilityId capabilityId){
        CapabilityRegistration registration =  capabilities.get(capabilityId);
        return registration.getRegistrationPoints();

    }
}
