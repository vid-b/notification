package com.sap.iot.ain.notification.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sap.iot.ain.reuse.Client;
import com.sap.iot.ain.reuse.ClientAccess;
import com.sap.iot.ain.reuse.ClientEntityListener;
import com.sap.iot.ain.reuse.SystemAdministrativeData;

/**
 * 
 * @author I322597
 *
 */
@Entity(name = "NotificationDescriptionEntity")
@IdClass(NotificationDescriptionPK.class)
@EntityListeners({ ClientEntityListener.class })
@Table(name = NotificationDescriptionEntity.DB_TABLE)

public class NotificationDescriptionEntity implements Serializable, ClientAccess {

	public static final String DB_TABLE = "\"sap.ain.metaData::Notification.Description\"";
	private static final long serialVersionUID = 5411921595152740962L;

	@Embedded
	private Client client = new Client();

	@Id
	@Column(name = "\"NotificationID\"")
	private String notificationID;

	@Id
	@Column(name = "\"LanguageISOCode\"")
	private String languageISOCode;

	@Column(name = "\"ShortDescription\"")
	private String shortDescription;

	@Column(name = "\"LongDescription\"")
	private String longDescription;

	@Embedded
	private SystemAdministrativeData systemAdministrativeData = new SystemAdministrativeData();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "\"NotificationID\"", referencedColumnName = "\"NotificationID\"", insertable = false, updatable = false) })

	private NotificationHeaderEntity notificationHeaderEntity;

	public Client getClient() {
		return client;
	}

	public String getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getLanguageISOCode() {
		return languageISOCode;
	}

	public void setLanguageISOCode(String languageISOCode) {
		this.languageISOCode = languageISOCode;
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

	public SystemAdministrativeData getSystemAdministrativeData() {
		return systemAdministrativeData;
	}

	public void setSystemAdministrativeData(SystemAdministrativeData systemAdministrativeData) {
		this.systemAdministrativeData = systemAdministrativeData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
