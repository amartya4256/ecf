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
package org.eclipse.ecf.provider.xmpp.identity;

import org.eclipse.ecf.core.identity.ID;
import org.eclipse.ecf.core.identity.IDCreateException;
import org.eclipse.ecf.core.identity.Namespace;
import org.eclipse.ecf.internal.provider.xmpp.Messages;

public class XMPPSNamespace extends Namespace {

	private static final long serialVersionUID = -820087396161230667L;

	private static final String XMPPS_PROTOCOL = "xmpps"; //$NON-NLS-1$

	private String getInitFromExternalForm(Object[] args) {
		if (args == null || args.length < 1 || args[0] == null)
			return null;
		if (args[0] instanceof String) {
			final String arg = (String) args[0];
			if (arg.startsWith(getScheme() + Namespace.SCHEME_SEPARATOR)) {
				final int index = arg.indexOf(Namespace.SCHEME_SEPARATOR);
				if (index >= arg.length())
					return null;
				return arg.substring(index + 1);
			}
		}
		return null;
	}

	public ID createInstance(Object[] args) throws IDCreateException {
		try {
			final String init = getInitFromExternalForm(args);
			if (init != null)
				return new XMPPSID(this, init);
			return new XMPPSID(this, (String) args[0]);
		} catch (final Exception e) {
			throw new IDCreateException(Messages.XMPPSNamespace_EXCEPTION_ID_CREATE, e);
		}
	}

	public String getScheme() {
		return XMPPS_PROTOCOL;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ecf.core.identity.Namespace#getSupportedParameterTypesForCreateInstance()
	 */
	public Class[][] getSupportedParameterTypes() {
		return new Class[][] {{String.class}};
	}
}
