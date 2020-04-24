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

@Table(name = NotificationCauseCodes.DB_TABLE)
@Entity(name = "NotificationCauseCodes")
@SystemParameters(client = true, language = true)
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQIUPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class NotificationCauseCodes extends ODataEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/NotificationCauseCodes\"";

	@Id
	@Column(name = "\"ItemNumber\"")
	@FacetsProperty(nullable = false, edmSimpleTypeKind = EdmSimpleTypeKind.Int64)
	private Integer itemNumber;

	@Id
	@Column(name = "\"NotificationID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String notificationId;

	@Id
	@Column(name = "\"CauseID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String causeId;

	@Column(name = "\"CauseCode\"")
	@FacetsProperty(maxLength = 50)
	private String causeCode;

	@Column(name = "\"CauseCodeShortDescription\"")
	@FacetsProperty(maxLength = 256)
	private String causeCodeShortDescription;

	@Column(name = "\"CauseCodeLongDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String causeCodeLongDescription;

	@Column(name = "\"CauseDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String causeDescription;

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

	public String getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}

	public String getCauseCodeShortDescription() {
		return causeCodeShortDescription;
	}

	public void setCauseCodeShortDescription(String causeCodeShortDescription) {
		this.causeCodeShortDescription = causeCodeShortDescription;
	}

	public String getCauseCodeLongDescription() {
		return causeCodeLongDescription;
	}

	public void setCauseCodeLongDescription(String causeCodeLongDescription) {
		this.causeCodeLongDescription = causeCodeLongDescription;
	}

	public String getCauseDescription() {
		return causeDescription;
	}

	public void setCauseDescription(String causeDescription) {
		this.causeDescription = causeDescription;
	}



}
