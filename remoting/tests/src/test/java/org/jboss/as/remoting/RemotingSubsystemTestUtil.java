/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
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

package org.jboss.as.remoting;

import java.util.Collections;

import org.jboss.as.controller.ProcessType;
import org.jboss.as.controller.capability.registry.RuntimeCapabilityRegistry;
import org.jboss.as.controller.extension.ExtensionRegistry;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.Resource;
import org.jboss.as.subsystem.test.AdditionalInitialization;
import org.wildfly.extension.io.IOWorkerCapability;

/**
 * Utilities for the remoting subsystem tests.
 *
 * @author Brian Stansberry (c) 2014 Red Hat Inc.
 */
class RemotingSubsystemTestUtil {

    static final AdditionalInitialization DEFAULT_ADDITIONAL_INITIALIZATION =
            AdditionalInitialization.withCapabilities(RemotingSubsystemRootResource.IO_CAPABILITY);

    static final AdditionalInitialization HC_ADDITIONAL_INITIALIZATION =
            new AdditionalInitialization.ManagementAdditionalInitialization() {

                @Override
                protected ProcessType getProcessType() {
                    return ProcessType.HOST_CONTROLLER;
                }

                @Override
                protected void initializeExtraSubystemsAndModel(ExtensionRegistry extensionRegistry, Resource rootResource, ManagementResourceRegistration rootRegistration, RuntimeCapabilityRegistry capabilityRegistry) {
                    super.initializeExtraSubystemsAndModel(extensionRegistry, rootResource, rootRegistration, capabilityRegistry);
                    AdditionalInitialization.registerCapabilities(capabilityRegistry,
                            Collections.singletonMap(RemotingSubsystemRootResource.IO_CAPABILITY, (Object) new IOWorkerCapability()));
                }
            };

    private RemotingSubsystemTestUtil() {
        //
    }
}
