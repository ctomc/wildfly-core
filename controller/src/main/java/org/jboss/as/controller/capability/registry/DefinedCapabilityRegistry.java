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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.as.controller.PathAddress;

/**
 * @author Tomaz Cerar (c) 2015 Red Hat Inc.
 */
public abstract class DefinedCapabilityRegistry<C extends CapabilityRegistration, R extends RequirementRegistration> implements CapabilityRegistry<C,R> {

    private Map<CapabilityId, CapabilityRegistration> capabilities = new ConcurrentHashMap<>();


    @Override
    public void registerCapabilityDefinition(CapabilityRegistration capability) {

    }

    @Override
    public CapabilityRegistration removeCapabilityDefinition(String capabilityName, CapabilityContext context, PathAddress registrationPoint) {
        return null;
    }
}
