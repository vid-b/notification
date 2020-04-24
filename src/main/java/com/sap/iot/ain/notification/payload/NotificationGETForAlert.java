package com.sap.iot.ain.notification.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationGETForAlert {

	private int count;

	private List<NotificationAlertMapping> data;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<NotificationAlertMapping> getData() {
		return data;
	}

	public void setData(List<NotificationAlertMapping> data) {
		this.data = data;
	}



}
