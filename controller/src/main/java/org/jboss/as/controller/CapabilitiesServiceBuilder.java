package org.jboss.as.controller;

import org.jboss.msc.inject.Injector;
import org.jboss.msc.service.ServiceBuilder;

/**
 * @author Tomaz Cerar (c) 2014 Red Hat Inc.
 */
public interface CapabilitiesServiceBuilder<T> extends ServiceBuilder<T> {
    <I> CapabilitiesServiceBuilder<T> addCapabilityRequirement(String capabilityName, String referenceName, Injector<I> target);

    <I> CapabilitiesServiceBuilder<T> addCapabilityRequirement(String capabilityName, Injector<I> target);
}
