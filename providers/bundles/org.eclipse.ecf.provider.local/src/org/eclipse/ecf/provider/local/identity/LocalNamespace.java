/****************************************************************************
 * Copyright (c) 2010 Composent, Inc. and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *   Composent, Inc. - initial API and implementation
 *
 * SPDX-License-Identifier: EPL-2.0
 *****************************************************************************/
package org.eclipse.ecf.provider.local.identity;

import java.net.URI;
import java.net.URL;
import org.eclipse.ecf.core.identity.*;

public class LocalNamespace extends Namespace {

	private static final long serialVersionUID = 3536863430112969997L;
	public static final String NAME = "ecf.namespace.local"; //$NON-NLS-1$
	public static final String SCHEME = "local"; //$NON-NLS-1$

	public LocalNamespace() {
		super(NAME, "Local Namespace"); //$NON-NLS-1$
	}

	public ID createInstance(Object[] parameters) throws IDCreateException {
		try {
			final String init = getInitStringFromExternalForm(parameters);
			if (init != null) {
				return new LocalID(this, init);
			}
			if (parameters != null) {
				if (parameters[0] instanceof URI)
					return new LocalID(this, ((URI) parameters[0]).toString());
				else if (parameters[0] instanceof String)
					return new LocalID(this, (String) parameters[0]);
				else if (parameters[0] instanceof URL)
					return new LocalID(this, ((URL) parameters[0]).toExternalForm());
				else if (parameters[0] instanceof LocalID)
					return (ID) parameters[0];
			}
			return new LocalID(this, IDFactory.getDefault().createGUID().getName());
		} catch (Exception e) {
			throw new IDCreateException("Could not create rest ID", e); //$NON-NLS-1$
		}
	}

	public String getScheme() {
		return SCHEME;
	}

	public Class[][] getSupportedParameterTypes() {
		return new Class[][] {{}, {URI.class}, {String.class}, {URL.class}};
	}
}
