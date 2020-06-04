/****************************************************************************
 * Copyright (c)2010 REMAIN B.V. The Netherlands. (http://www.remainsoftware.com).
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  Contributors:
 *    Wim Jongman - initial API and implementation 
 *    Ahmed Aadel - initial API and implementation     
 *
 * SPDX-License-Identifier: EPL-2.0
 *****************************************************************************/
package org.eclipse.ecf.provider.zookeeper.core;

import java.net.URI;

import org.eclipse.core.runtime.Assert;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IDCreateException;
import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.discovery.identity.IServiceTypeID;
import org.eclipse.ecf.discovery.identity.ServiceTypeID;

public class ZooDiscoveryNamespace extends Namespace {

	private static final long serialVersionUID = 3925693055869405334L;
	public static final String NAME = "ecf.namespace.zoodiscovery"; //$NON-NLS-1$	

	public ZooDiscoveryNamespace() {
		super(NAME, "ZooKeeper Discovery Namespace"); //$NON-NLS-1$
	}

	public ID createInstance(Object[] parameters) throws IDCreateException {
		Assert.isTrue(parameters != null && parameters.length > 0);
		try {
			if (parameters[0] instanceof String && parameters instanceof String[]) {
				return new ZooDiscoveryTargetID(this, (String[]) parameters);
			} else if (parameters.length == 1
					&& parameters[0] instanceof IServiceTypeID) {
				return new ZooDiscoveryServiceTypeID(this,
						(IServiceTypeID) parameters[0]);
			} else if (parameters.length == 2
					&& parameters[0] instanceof IServiceTypeID
					&& parameters[1] instanceof URI) {
				return new ZooDiscoveryServiceID(this,
						((IServiceTypeID) parameters[0]), ((URI) parameters[1]));
			} else if (parameters.length == 2
					&& parameters[0] instanceof String
					&& parameters[1] instanceof URI) {
				final String type = (String) parameters[0];
				return new ZooDiscoveryServiceID(this,
						new ServiceTypeID(this, type), ((URI) parameters[1]));
			}

		} catch (Exception e) {
			throw new IDCreateException(getName()+" createInstance()", e); //$NON-NLS-1$
		}
		return null;

	}

	public String getScheme() {
		return this.getClass().toString();
	}

	public Class[][] getSupportedParameterTypes() {
		return new Class[][] { { String.class }, { IServiceTypeID.class },
				{ IServiceTypeID.class, URI.class } };
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
