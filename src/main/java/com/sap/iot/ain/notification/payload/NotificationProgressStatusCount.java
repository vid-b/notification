package com.sap.iot.ain.notification.payload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationProgressStatusCount {

	private List<NotificationCountByStatus> notificationProgressStatus;

	private int totalCount;


	public List<NotificationCountByStatus> getNotificationProgressStatus() {
		return notificationProgressStatus;
	}

	public void setNotificationProgressStatus(
			List<NotificationCountByStatus> notificationProgressStatus) {
		this.notificationProgressStatus = notificationProgressStatus;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}



}
