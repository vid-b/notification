package com.sap.iot.ain.notification.payload;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationFailureModes {

	private String failureModeID;

	public String getFailureModeID() {
		return failureModeID;
	}

	public void setFailureModeID(String failureModeID) {
		this.failureModeID = failureModeID;
	}
}
