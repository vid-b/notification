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

@Entity(name = "NotificationAlertMappingEntity")
@EntityListeners({ClientEntityListener.class})
@IdClass(NotificationAlertMappingEntityPK.class)
@Table(name = NotificationAlertMappingEntity.DB_TABLE)
public class NotificationAlertMappingEntity implements Serializable, ClientAccess {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE =
			"\"sap.ain.metaData::Notification.NotificationAlertMapping\"";

	@Embedded
	private Client client = new Client();

	@Id
	@Column(name = "\"NotificationID\"")
	private String notificationId;

	@Id
	@Column(name = "\"AlertID\"")
	private String alertId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({@JoinColumn(name = "\"NotificationID\"",
			referencedColumnName = "\"NotificationID\"", insertable = false, updatable = false)})
	private NotificationHeaderEntity notificationHeaderEntityForAlerts;



	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

}
