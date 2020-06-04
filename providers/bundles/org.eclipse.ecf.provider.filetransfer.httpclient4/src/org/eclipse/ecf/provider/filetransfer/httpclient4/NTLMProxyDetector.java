/****************************************************************************
 * Copyright (c) 2009 EclipseSource and others.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *   EclipseSource - initial API and implementation
 *   Thomas Joiner - changed to work with HttpClient 4.1 and added SPNEGO detection
 *
 * SPDX-License-Identifier: EPL-2.0
 *****************************************************************************/
package org.eclipse.ecf.provider.filetransfer.httpclient4;

import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthState;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.protocol.HttpContext;

public class NTLMProxyDetector {

	private static final String PROXY_SPNEGO_VALUE = "NEGOTIATE"; //$NON-NLS-1$
	private static final String PROXY_NTLM_VALUE = "NTLM"; //$NON-NLS-1$

	/**
	 * This method will detect if the request connected to a NTLM proxy
	 * given the HttpContext provided to one of the HttpClient#execute()
	 * methods.
	 * 
	 * @param context the HttpContext given to the HttpClient at execution time
	 * @return true if it connected to an NTLM proxy
	 * @since 5.0
	 */
	public static boolean detectNTLMProxy(HttpContext context) {
		return isProxyType(context, PROXY_NTLM_VALUE);
	}

	private static boolean isProxyType(HttpContext context, String scheme) {
		if (context == null)
			return false;
		AuthState authState = (AuthState) context.getAttribute(ClientContext.PROXY_AUTH_STATE);
		if (authState == null)
			return false;
		AuthScheme authScheme = authState.getAuthScheme();
		if (authScheme == null)
			return false;
		String schemeName = authScheme.getSchemeName();
		if (schemeName == null)
			return false;
		return schemeName.equalsIgnoreCase(scheme);
	}

	/**
	 * This method will detect if the request connected to a SPNEGO proxy
	 * given the HttpContext provided to one of the HttpClient#execute()
	 * methods.
	 * 
	 * @param context the HttpContext given to the HttpClient at execution time
	 * @return true if it connected to an SPNEGO proxy
	 * @since 5.0
	 */
	public static boolean detectSPNEGOProxy(HttpContext context) {
		return isProxyType(context, PROXY_SPNEGO_VALUE);
	}
}
