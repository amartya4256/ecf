package org.eclipse.ecf.osgi.services.remoteserviceadmin;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.ecf.core.identity.ID;

public class RemoteReferenceNotFoundException extends Exception {

	private static final long serialVersionUID = -4174685192086828376L;

	private ID targetID;
	private ID[] idFilter;
	private Collection<String> interfaces;
	private String rsFilter;
	
	public RemoteReferenceNotFoundException(ID targetID, ID[] idFilter,
			Collection<String> interfaces, String rsFilter) {
		this.targetID = targetID;
		this.idFilter = idFilter;
		this.interfaces = interfaces;
		this.rsFilter = rsFilter;
	}

	public ID getTargetID() {
		return targetID;
	}

	public ID[] getIdFilter() {
		return idFilter;
	}

	public Collection<String> getInterfaces() {
		return interfaces;
	}

	public String getRsFilter() {
		return rsFilter;
	}

	public String toString() {
		return "RemoteReferenceNotFoundException[targetID=" + targetID
				+ ", idFilter=" + Arrays.toString(idFilter) + ", interfaces="
				+ interfaces + ", rsFilter=" + rsFilter + "]";
	}
	
}
