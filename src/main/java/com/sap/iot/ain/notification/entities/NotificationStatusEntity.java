package com.sap.iot.ain.notification.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "NotificationStatusEntity")
@IdClass(NotificationStatusEntityPK.class)
@Table(name = NotificationStatusEntity.DB_TABLE)
public class NotificationStatusEntity implements Serializable {
	public static final String DB_TABLE = "\"sap.ain.metaData::Notification.Status\"";
	private static final long serialVersionUID = 5411921595152740962L;

	@Id
	@Column(name = "\"ID\"")
	private String id;

	@Id
	@Column(name = "\"Status\"")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "\"ID\"", referencedColumnName = "\"NotificationID\"", insertable = false, updatable = false) })
	private NotificationHeaderEntity headerEntity;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
