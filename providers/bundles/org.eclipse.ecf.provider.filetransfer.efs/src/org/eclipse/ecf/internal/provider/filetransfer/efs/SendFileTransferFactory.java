/****************************************************************************
 * Copyright (c) 2007 Composent, Inc. and others.
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

package org.eclipse.ecf.internal.provider.filetransfer.efs;

import org.eclipse.ecf.filetransfer.service.ISendFileTransfer;
import org.eclipse.ecf.filetransfer.service.ISendFileTransferFactory;

/**
 *
 */
public class SendFileTransferFactory implements ISendFileTransferFactory {

	/* (non-Javadoc)
	 * @see org.eclipse.ecf.filetransfer.service.ISendFileTransferFactory#newInstance()
	 */
	public ISendFileTransfer newInstance() {
		return new SendFileTransfer();
	}

}
