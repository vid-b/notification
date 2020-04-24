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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sap.iot.ain.reuse.Client;
import com.sap.iot.ain.reuse.ClientAccess;
import com.sap.iot.ain.reuse.ClientEntityListener;

@Entity(name = "NotificationFailureModesEntity")
@EntityListeners({ClientEntityListener.class})
@IdClass(NotificationFailureModesEntityPK.class)
@Table(name = NotificationFailureModesEntity.DB_TABLE)
public class NotificationFailureModesEntity implements Serializable, ClientAccess {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE =
			"\"sap.ain.metaData::Notification.NotificationFailureModes\"";

	@Embedded
	private Client client = new Client();

	@Id
	@Column(name = "\"ItemNumber\"")
	private Integer itemNumber;

	@Id
	@Column(name = "\"NotificationID\"")
	private String notificationId;


	@Id
	@Column(name = "\"FailureModeID\"")
	private String failureModeId;


	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "\"NotificationID\"", referencedColumnName = "\"NotificationID\"",
					insertable = false, updatable = false),
			@JoinColumn(name = "\"ItemNumber\"", referencedColumnName = "\"ItemNumber\"",
					insertable = false, updatable = false)})
	private NotificationItemEntity notificationItemEntityForFailureModes;


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


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


}
