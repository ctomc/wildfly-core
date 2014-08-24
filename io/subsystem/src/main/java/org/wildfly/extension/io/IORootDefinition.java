/*
 *
 *  JBoss, Home of Professional Open Source.
 *  Copyright 2013, Red Hat, Inc., and individual contributors
 *  as indicated by the @author tags. See the copyright.txt file in the
 *  distribution for a full listing of individual contributors.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * /
 */

package org.wildfly.extension.io;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.jboss.as.controller.AttributeDefinition;
import org.jboss.as.controller.PersistentResourceDefinition;
import org.jboss.as.controller.ReloadRequiredRemoveStepHandler;
import org.jboss.as.controller.capability.RuntimeCapability;
import org.jboss.as.controller.registry.OperationEntry;
import org.jboss.logging.Logger;
import org.wildfly.extension.io.logging.IOLogger;

/**
 * @author <a href="mailto:tomaz.cerar@redhat.com">Tomaz Cerar</a> (c) 2013 Red Hat Inc.
 */
class IORootDefinition extends PersistentResourceDefinition {

    static final RuntimeCapability<IOCapability> IO_CAPABILITY_RUNTIME_CAPABILITY =
            new RuntimeCapability<IOCapability>("org.wildfly.extension.io", new IOCapability()) {
                @Override
                public String getDescription(Locale locale) {
                    IOLogger i18n = Logger.getMessageLogger(IOLogger.class, "", locale);
                    return i18n.ioCapability();
                }
            };

    static final IORootDefinition INSTANCE = new IORootDefinition();

    static final PersistentResourceDefinition[] CHILDREN = {
            WorkerResourceDefinition.INSTANCE,
            BufferPoolResourceDefinition.INSTANCE
        };

    private IORootDefinition() {
        super(IOExtension.SUBSYSTEM_PATH,
                IOExtension.getResolver(),
                IOSubsystemAdd.INSTANCE,
                new ReloadRequiredRemoveStepHandler(IO_CAPABILITY_RUNTIME_CAPABILITY),
                OperationEntry.Flag.RESTART_NONE,
                OperationEntry.Flag.RESTART_ALL_SERVICES);
    }

    @Override
    public Collection<AttributeDefinition> getAttributes() {
        return Collections.emptySet();
    }

    @Override
    protected List<? extends PersistentResourceDefinition> getChildren() {
        return Arrays.asList(CHILDREN);
    }
}
