package com.sap.iot.ain.notification.payload;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationCountByStatus {
	
	@Column(name = "\"NotificationProgressStatusCode\"")
	private String notificationProgressStatusCode;
	

	@Column(name = "\"NotificationProgressStatusDescription\"")
	private String notificationProgressStatusDescription;
	
	@Column(name = "\"Count\"")
	private int  count;

	public String getNotificationProgressStatusCode() {
		return notificationProgressStatusCode;
	}

	public void setNotificationProgressStatusCode(String notificationProgressStatusCode) {
		this.notificationProgressStatusCode = notificationProgressStatusCode;
	}

	public String getNotificationProgressStatusDescription() {
		return notificationProgressStatusDescription;
	}

	public void setNotificationProgressStatusDescription(String notificationProgressStatusDescription) {
		this.notificationProgressStatusDescription = notificationProgressStatusDescription;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
