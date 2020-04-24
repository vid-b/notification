package com.sap.iot.ain.notification.payload;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sap.iot.ain.reuse.annotation.Size;
import com.sap.iot.ain.validation.utils.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationItems {


	private Integer itemNumber;

	@Size(field = "Short Description", max = 40, groups = Order.Level1.class)
	private String shortDescription;

	@Size(field = "Long Description", max = 5000, groups = Order.Level1.class)
	private String longDescription;

	@Valid
	private List<NotificationCauseCodes> notificationCauseCodes = new ArrayList<>();
	@Valid
	private NotificationFailureModes notificationFailureModes;

	public Integer getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(Integer itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public List<NotificationCauseCodes> getNotificationCauseCodes() {
		return notificationCauseCodes;
	}

	public void setNotificationCauseCodes(List<NotificationCauseCodes> notificationCauseCodes) {
		this.notificationCauseCodes = notificationCauseCodes;
	}

	public NotificationFailureModes getNotificationFailureModes() {
		return notificationFailureModes;
	}

	public void setNotificationFailureModes(NotificationFailureModes notificationFailureModes) {
		this.notificationFailureModes = notificationFailureModes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		NotificationItems other = (NotificationItems) obj;
		if (itemNumber == null) {
			if (other.itemNumber != null)
				return false;
		} else if (!itemNumber.equals(other.itemNumber))
			return false;
		return true;
	}



}
