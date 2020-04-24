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

@Entity(name = "NotificationCauseCodesEntity")
@EntityListeners({ClientEntityListener.class})
@IdClass(NotificationCauseCodesEntityPK.class)
@Table(name = NotificationCauseCodesEntity.DB_TABLE)
public class NotificationCauseCodesEntity implements Serializable, ClientAccess {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE =
			"\"sap.ain.metaData::Notification.NotificationCauseCodes\"";
	@Embedded
	private Client client = new Client();

	@Id
	@Column(name = "\"ItemNumber\"")
	private Integer itemNumber;

	@Id
	@Column(name = "\"NotificationID\"")
	private String notificationId;


	@Id
	@Column(name = "\"CauseID\"")
	private String causeId;

	@Column(name = "\"CauseDescription\"")
	private String causeDescription;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "\"NotificationID\"", referencedColumnName = "\"NotificationID\"",
					insertable = false, updatable = false),
			@JoinColumn(name = "\"ItemNumber\"", referencedColumnName = "\"ItemNumber\"",
					insertable = false, updatable = false)})
	private NotificationItemEntity notificationItemEntityForCauseCodes;


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


	public String getCauseId() {
		return causeId;
	}


	public void setCauseId(String causeId) {
		this.causeId = causeId;
	}


	public NotificationItemEntity getNotificationItemEntityForCauseCodes() {
		return notificationItemEntityForCauseCodes;
	}


	public void setNotificationItemEntityForCauseCodes(
			NotificationItemEntity notificationItemEntityForCauseCodes) {
		this.notificationItemEntityForCauseCodes = notificationItemEntityForCauseCodes;
	}


	public Client getClient() {
		return client;
	}


	public void setClient(Client client) {
		this.client = client;
	}


	public String getCauseDescription() {
		return causeDescription;
	}


	public void setCauseDescription(String causeDescription) {
		this.causeDescription = causeDescription;
	}



}
