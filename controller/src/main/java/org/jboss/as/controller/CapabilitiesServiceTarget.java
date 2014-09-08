package org.jboss.as.controller;

import org.jboss.msc.service.Service;
import org.jboss.msc.service.ServiceName;
import org.jboss.msc.service.ServiceTarget;

/**
 * @author Tomaz Cerar (c) 2014 Red Hat Inc.
 */
public interface CapabilitiesServiceTarget extends ServiceTarget {

    <T> CapabilitiesServiceBuilder<T> addService(final ServiceName name, final Service<T> service) throws IllegalArgumentException;
}
