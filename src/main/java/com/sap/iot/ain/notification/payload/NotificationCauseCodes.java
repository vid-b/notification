package com.sap.iot.ain.notification.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sap.iot.ain.reuse.annotation.Size;
import com.sap.iot.ain.validation.utils.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationCauseCodes {

	private String causeID;
	
	@Size(field = "CauseDescription", max = 5000, groups = Order.Level1.class)
	private String causeDescription ;

	public String getCauseID() {
		return causeID;
	}

	public void setCauseID(String causeID) {
		this.causeID = causeID;
	}
	
	public String getCauseDescription() {
		return causeDescription;
	}

	public void setCauseDescription(String causeDescription) {
		this.causeDescription = causeDescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((causeID == null) ? 0 : causeID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NotificationCauseCodes other = (NotificationCauseCodes) obj;
		if (causeID == null) {
			if (other.causeID != null)
				return false;
		} else if (!causeID.equals(other.causeID))
			return false;
		return true;
	}


}
