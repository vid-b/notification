package com.sap.iot.ain.notification.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class NotificationFailureModesEntityPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer itemNumber;
	private String notificationId;
	private String failureModeId;
	public Integer getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}
	
	
	
	public String getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}
	public String getFailureModeId() {
		return failureModeId;
	}
	public void setFailureModeId(String failureModeId) {
		this.failureModeId = failureModeId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((notificationId == null) ? 0 : notificationId.hashCode());
		result = prime * result + ((failureModeId == null) ? 0 : failureModeId.hashCode());
		result = prime * result + ((itemNumber == null) ? 0 : itemNumber.hashCode());
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
		NotificationFailureModesEntityPK other = (NotificationFailureModesEntityPK) obj;
		if (notificationId == null) {
			if (other.notificationId != null)
				return false;
		} else if (!notificationId.equals(other.notificationId))
			return false;
		if (failureModeId == null) {
			if (other.failureModeId != null)
				return false;
		} else if (!failureModeId.equals(other.failureModeId))
			return false;
		if (itemNumber == null) {
			if (other.itemNumber != null)
				return false;
		} else if (!itemNumber.equals(other.itemNumber))
			return false;
		return true;
	}
}
