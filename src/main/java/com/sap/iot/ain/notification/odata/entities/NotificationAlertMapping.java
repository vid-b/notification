package com.sap.iot.ain.notification.odata.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import com.sap.iot.ain.odata.core.ODataEntity;
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.security.Secure;

@Table(name = NotificationAlertMapping.DB_TABLE)
@Entity(name = "Alerts")
@SystemParameters(client = true)
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQIUPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class NotificationAlertMapping extends ODataEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/NotificationAlertMapping\"";

	@Id
	@Column(name = "\"NotificationID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	public String notificationId;

	@Id
	@Column(name = "\"AlertID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	public String alertId;



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



}
