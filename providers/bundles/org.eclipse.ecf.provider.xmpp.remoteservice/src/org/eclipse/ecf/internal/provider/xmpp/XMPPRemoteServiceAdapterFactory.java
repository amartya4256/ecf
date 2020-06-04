/****************************************************************************
 * Copyright (c) 2004 Composent, Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *    Composent, Inc. - initial API and implementation
 *
 * SPDX-License-Identifier: EPL-2.0
 *****************************************************************************/

package org.eclipse.ecf.internal.provider.xmpp;

import java.util.Dictionary;

import org.eclipse.ecf.core.events.IContainerConnectedEvent;
import org.eclipse.ecf.core.events.IContainerDisconnectedEvent;
import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.sharedobject.ISharedObject;
import org.eclipse.ecf.core.sharedobject.ISharedObjectContainer;
import org.eclipse.ecf.provider.remoteservice.generic.RegistrySharedObject;
import org.eclipse.ecf.provider.remoteservice.generic.RemoteServiceContainerAdapterFactory;
import org.eclipse.ecf.remoteservice.IRemoteServiceContainerAdapter;

public class XMPPRemoteServiceAdapterFactory extends
		RemoteServiceContainerAdapterFactory {

	class XMPPRegistrySharedObject extends RegistrySharedObject {
		
		protected ID getLocalContainerID() {
			return getContext().getConnectedID();
		}
		
		protected void handleContainerConnectedEvent(
				IContainerConnectedEvent event) {
			ID targetID = event.getTargetID();
			if (targetID != null) {
				synchronized (localRegistry) {
					localRegistry.setContainerID(targetID);
				}
			}
		}
		
		protected void handleContainerDisconnectedEvent(
				IContainerDisconnectedEvent event) {
			ID targetID = event.getTargetID();
			if (targetID.equals(event.getLocalContainerID())) {
				clearRemoteRegistrys();
				synchronized (localRegistry) {
					localRegistry.setContainerID(null);
				}
			}
		}
		
		protected void handleRegistryActivatedEvent() {
			// do nothing
		}
		
		protected ID[] getGroupMemberIDs() {
			return new ID[0];
		}
		
		protected ID[] getTargetsFromProperties(Dictionary properties) {
			ID[] targets = super.getTargetsFromProperties(properties);
			// returning ID[0] means that no sending of registrations should occur on
			// remote service registration (i.e. in RegistrySharedObject.registerRemoteService
			return (targets == null)?new ID[0]:targets;
			
		}
	}

	public XMPPRemoteServiceAdapterFactory() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ecf.provider.remoteservice.generic.
	 * RemoteServiceContainerAdapterFactory
	 * #createAdapter(org.eclipse.ecf.core.sharedobject.ISharedObjectContainer,
	 * java.lang.Class, org.eclipse.ecf.core.identity.ID)
	 */
	protected ISharedObject createAdapter(ISharedObjectContainer container,
			Class adapterType, ID adapterID) {
		if (adapterType.equals(IRemoteServiceContainerAdapter.class)) {
			return new XMPPRegistrySharedObject();
		} else {
			return null;
		}
	}

}
