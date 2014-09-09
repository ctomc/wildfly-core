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

package org.jboss.as.controller.capability;

import java.util.Set;

import org.jboss.msc.service.ServiceName;

/**
 * A capability exposed in a running WildFly process.
 *
 * @param <T> the type of the runtime API object exposed by the capability
 * @author Brian Stansberry (c) 2014 Red Hat Inc.
 */
public class RuntimeCapability<T> extends AbstractCapability {

    private final Class<T> classType;
    private final ServiceName baseServiceName;

    protected RuntimeCapability(String name, Class<T> runtimeAPI, ServiceName baseServiceName, Set<String> requirements, Set<String> optionalRequirements) {
        super(name, requirements, optionalRequirements);
        this.baseServiceName = baseServiceName;
        this.classType = runtimeAPI;
    }

    protected RuntimeCapability(String name, Class<T> runtimeAPI, ServiceName baseServiceName, Set<String> requirements) {
        this(name, runtimeAPI, baseServiceName, requirements, null);
    }

    protected RuntimeCapability(String name, Class<T> runtimeAPI, ServiceName baseServiceName, String... requirements) {
        super(name, requirements);
        this.classType = runtimeAPI;
        this.baseServiceName = baseServiceName;
    }

    /**
     * Object encapsulating the API exposed by this capability to other capabilities that require it, if it does
     * expose such an API.
     *
     * @return the API object, or {@code null} if the capability exposes no API to other capabilities
     */
    public Class<T> getClassType() {
        return classType;
    }

    public ServiceName getBaseServiceName() {
        return baseServiceName;
    }

    public ServiceName getServiceName(String name){
        return baseServiceName.append(name);
    }
}
