/*******************************************************************************
 * Copyright (c) 2004 Composent, Inc. and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Composent, Inc. - initial API and implementation
 ******************************************************************************/

package org.eclipse.ecf.core;

public class SharedObjectAddAbortException extends SharedObjectAddException {

	private static final long serialVersionUID = 4120851079287223088L;

	protected long timeout = -1L;

    public SharedObjectAddAbortException() {
        super();
    }
    public SharedObjectAddAbortException(String arg0) {
        super(arg0);
    }
    public SharedObjectAddAbortException(String msg, Throwable cause) {
        super(msg, cause);
    }
    public SharedObjectAddAbortException(String msg, Throwable cause,
            int timeout) {
        super(msg, cause);
        this.timeout = timeout;
    }
    public SharedObjectAddAbortException(Throwable cause) {
        super(cause);
    }

    public long getTimeout() {
        return timeout;
    }

}