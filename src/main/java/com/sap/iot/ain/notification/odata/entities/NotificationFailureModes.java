package com.sap.iot.ain.notification.odata.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

import com.sap.iot.ain.odata.core.ODataEntity;
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.security.Secure;

@Table(name = NotificationFailureModes.DB_TABLE)
@Entity(name = "NotificationFailureModes")
@SystemParameters(client = true, language = true, scope = true, user_bp_id = true)
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQIUPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class NotificationFailureModes extends ODataEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/NotificationFailureModes\"";

	@Id
	@Column(name = "\"ItemNumber\"")
	@FacetsProperty(nullable = false, edmSimpleTypeKind = EdmSimpleTypeKind.Int64)
	private Integer itemNumber;

	@Id
	@Column(name = "\"NotificationID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String notificationId;

	@Id
	@Column(name = "\"FailureModeID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String failureModeId;

	@Column(name = "\"FailureMode\"")
	@FacetsProperty(maxLength = 50)
	private String failureMode;

	@Column(name = "\"FailureModeShortDescription\"")
	@FacetsProperty(maxLength = 256)
	private String failureModeShortDescription;

	@Column(name = "\"FailureModeLongDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String failureModeLongDescription;


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

	public String getFailureMode() {
		return failureMode;
	}

	public void setFailureMode(String failureMode) {
		this.failureMode = failureMode;
	}

	public String getFailureModeShortDescription() {
		return failureModeShortDescription;
	}

	public void setFailureModeShortDescription(String failureModeShortDescription) {
		this.failureModeShortDescription = failureModeShortDescription;
	}

	public String getFailureModeLongDescription() {
		return failureModeLongDescription;
	}

	public void setFailureModeLongDescription(String failureModeLongDescription) {
		this.failureModeLongDescription = failureModeLongDescription;
	}



}
