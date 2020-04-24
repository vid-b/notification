package com.sap.iot.ain.notification.odata.entities;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

import com.sap.iot.ain.odata.core.ODataEntity;
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.security.Secure;

@Table(name = NotificationItems.DB_TABLE)
@Entity(name = "NotificationItems")
@SystemParameters(client = true, language = true)
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQUIPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class NotificationItems extends ODataEntity {


	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/NotificationItems\"";

	@Id
	@Column(name = "\"ItemNumber\"")
	@FacetsProperty(nullable = false, edmSimpleTypeKind = EdmSimpleTypeKind.Int64)
	private Integer itemNumber;

	@Id
	@Column(name = "\"NotificationID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String notificationId;

	@Column(name = "\"ShortDescription\"")
	@FacetsProperty(maxLength = 40)
	private String shortDescription;

	@Column(name = "\"LongDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String longDescription;

	@Column(name = "\"NoOfCauseCodesForItem\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.Int32)
	public Integer noOfCauseCodesForItem;

	@Column(name = "\"ItemCauseIDs\"")
	@FacetsProperty(maxLength = 5000)
	private String itemCauseIds;

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

	public Integer getNoOfCauseCodesForItem() {
		return noOfCauseCodesForItem;
	}

	public void setNoOfCauseCodesForItem(Integer noOfCauseCodesForItem) {
		this.noOfCauseCodesForItem = noOfCauseCodesForItem;
	}

	public String getItemCauseIds() {
		return itemCauseIds;
	}

	public void setItemCauseIds(String itemCauseIds) {
		this.itemCauseIds = itemCauseIds;
	}



}
