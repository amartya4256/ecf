/*******************************************************************************
 * Copyright (c) 2005, 2006 Erkki Lindpere and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Erkki Lindpere - initial API and implementation
 *******************************************************************************/
package org.eclipse.ecf.bulletinboard;

/**
 * The base class for all exceptions thrown by the BB API.
 * 
 * @author Erkki
 */
public class BBException extends Exception {
	private static final long serialVersionUID = -1739222507295443443L;

	public BBException() {
		super();
	}

	public BBException(String message) {
		super(message);
	}

	public BBException(String message, Throwable cause) {
		super(message, cause);
	}

	public BBException(Throwable cause) {
		super(cause);
	}
}
