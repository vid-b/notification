package com.sap.iot.ain.notification.payload;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationAlertMapping implements RowMapper<NotificationAlertMapping> {

	private String alertId;

	private String notificationId;

	private String internalId;

	private String equipmentId;

	private static final Logger logger = LoggerFactory.getLogger(NotificationAlertMapping.class);

	public String getAlertId() {
		return alertId;
	}

	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public NotificationAlertMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
		NotificationAlertMapping notifications = null;

		try {
			notifications = this.getClass().newInstance();
			notifications.setAlertId(rs.getString("AlertID"));
			notifications.setNotificationId(rs.getString("NotificationID"));
			notifications.setInternalId(rs.getString("InternalID"));
			notifications.setEquipmentId(rs.getString("EquipmentID"));
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			logger.error(
					"Exception occured while retrieving database columns NotificationAlertMapping.mapRow");
		}
		return notifications;
	}

}
