package com.sap.iot.ain.notification.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sap.iot.ain.reuse.Client;
import com.sap.iot.ain.reuse.ClientAccess;
import com.sap.iot.ain.reuse.ClientEntityListener;

@Entity(name = "NotificationItemEntity")
@EntityListeners({ClientEntityListener.class})
@IdClass(NotificationItemEntityPK.class)
@Table(name = NotificationItemEntity.DB_TABLE)
public class NotificationItemEntity implements Serializable, ClientAccess {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE = "\"sap.ain.metaData::Notification.Items\"";

	@Embedded
	private Client client = new Client();

	@Id
	@Column(name = "\"ItemNumber\"")
	private Integer itemNumber;

	@Id
	@Column(name = "\"NotificationID\"")
	private String notificationId;

	@Column(name = "\"ShortDescription\"")
	private String shortDescription;

	@Column(name = "\"LongDescription\"")
	private String longDescription;

	@OneToMany(mappedBy = "notificationItemEntityForCauseCodes", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.EAGER)
	private List<NotificationCauseCodesEntity> notificationCauseCodesEntity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({@JoinColumn(name = "\"NotificationID\"",
			referencedColumnName = "\"NotificationID\"", insertable = false, updatable = false)})
	private NotificationHeaderEntity notificationHeaderEntityForItems;

	@OneToOne(mappedBy = "notificationItemEntityForFailureModes", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.EAGER)
	private NotificationFailureModesEntity notificationFailureModesEntity;

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


	public List<NotificationCauseCodesEntity> getNotificationCauseCodesEntity() {
		return notificationCauseCodesEntity;
	}

	public void setNotificationCauseCodesEntity(
			List<NotificationCauseCodesEntity> notificationCauseCodesEntity) {
		this.notificationCauseCodesEntity = notificationCauseCodesEntity;
	}

	public NotificationFailureModesEntity getNotificationFailureModesEntity() {
		return notificationFailureModesEntity;
	}

	public void setNotificationFailureModesEntity(
			NotificationFailureModesEntity notificationFailureModesEntity) {
		this.notificationFailureModesEntity = notificationFailureModesEntity;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}



}
