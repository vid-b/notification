package com.sap.iot.ain.notification.payload;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sap.iot.ain.reuse.payload.ID;

public class NotificationIDList {

	@JsonProperty("notifications")
	private List<ID> IDs = new ArrayList<ID>();

	public List<ID> getIDs() {
		return IDs;
	}

	public void setIDs(List<ID> iDs) {
		IDs = iDs;
	}

}
