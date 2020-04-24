package com.sap.iot.ain.notification.entities;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * 
 * @author I322597
 *
 */
@Embeddable
public class NotificationDescriptionPK implements Serializable {

	private static final long serialVersionUID = 1L;

	private String notificationID;
	private String languageISOCode;

	public String getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}

	public String getLanguageISOCode() {
		return languageISOCode;
	}

	public void setLanguageISOCode(String languageISOCode) {
		this.languageISOCode = languageISOCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((languageISOCode == null) ? 0 : languageISOCode.hashCode());
		result = prime * result + ((notificationID == null) ? 0 : notificationID.hashCode());
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
		NotificationDescriptionPK other = (NotificationDescriptionPK) obj;
		if (languageISOCode == null) {
			if (other.languageISOCode != null)
				return false;
		} else if (!languageISOCode.equals(other.languageISOCode))
			return false;
		if (notificationID == null) {
			if (other.notificationID != null)
				return false;
		} else if (!notificationID.equals(other.notificationID))
			return false;
		return true;
	}

}
