package com.sap.iot.ain.notification.payload;

import java.sql.Timestamp;
import java.util.List;

import org.geojson.GeoJsonObject;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sap.iot.ain.reuse.DateSerializer;
import com.sap.iot.ain.reuse.TimestampSerializer;
import com.sap.iot.ain.reuse.annotation.Size;
import com.sap.iot.ain.reuse.payload.AdminData;
import com.sap.iot.ain.validation.utils.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationGETWithItems {

	private String notificationID;

	private String externalID;

	private String equipmentID;

	private String locationID;

	private String breakdown;

	private String type;

	private String priority;

	private List<String> status;

	@JsonSerialize(using = DateSerializer.class)
	private DateTime startDate;

	@JsonSerialize(using = DateSerializer.class)
	private DateTime endDate;

	@JsonSerialize(using = TimestampSerializer.class)
	private Timestamp malfunctionStartDate;

	@JsonSerialize(using = TimestampSerializer.class)
	private Timestamp malfunctionEndDate;

	private NotificationDescription description;

	private AdminData adminData;

	private GeoJsonObject geometry;

	private String operator;
	
	private String confirmedFailureModeID;
	
	private String proposedFailureModeID;

	private String systemProposedFailureModeID;
	
	private String effectID;
	
	private String causeID;
	
	private String instructionID;
//	private List<AlertID> alertIDs;

	private List<NotificationItems> items;

	private String internalID;

	public String getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}

	public String getExternalID() {
		return externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	public String getEquipmentID() {
		return equipmentID;
	}

	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(String breakdown) {
		this.breakdown = breakdown;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}

	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public Timestamp getMalfunctionStartDate() {
		return malfunctionStartDate;
	}

	public void setMalfunctionStartDate(Timestamp malfunctionStartDate) {
		this.malfunctionStartDate = malfunctionStartDate;
	}

	public Timestamp getMalfunctionEndDate() {
		return malfunctionEndDate;
	}

	public void setMalfunctionEndDate(Timestamp malfunctionEndDate) {
		this.malfunctionEndDate = malfunctionEndDate;
	}


	public NotificationDescription getDescription() {
		return description;
	}

	public void setDescription(NotificationDescription description) {
		this.description = description;
	}


	public AdminData getAdminData() {
		return adminData;
	}

	public void setAdminData(AdminData adminData) {
		this.adminData = adminData;
	}

	public GeoJsonObject getGeometry() {
		return geometry;
	}

	public void setGeometry(GeoJsonObject geometry) {
		this.geometry = geometry;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

//	public List<AlertID> getAlertIDs() {
//		return alertIDs;
//	}
//
//	public void setAlertIDs(List<AlertID> alertIDs) {
//		this.alertIDs = alertIDs;
//	}

	public List<NotificationItems> getItems() {
		return items;
	}

	public void setItems(List<NotificationItems> items) {
		this.items = items;
	}

	public String getInternalID() {
		return internalID;
	}

	public void setInternalID(String internalID) {
		this.internalID = internalID;
	}

	public String getConfirmedFailureModeID() {
		return confirmedFailureModeID;
	}

	public void setConfirmedFailureModeID(String confirmedFailureModeID) {
		this.confirmedFailureModeID = confirmedFailureModeID;
	}

	public String getProposedFailureModeID() {
		return proposedFailureModeID;
	}

	public void setProposedFailureModeID(String proposedFailureModeID) {
		this.proposedFailureModeID = proposedFailureModeID;
	}

	public String getSystemProposedFailureModeID() {
		return systemProposedFailureModeID;
	}

	public void setSystemProposedFailureModeID(String systemProposedFailureModeID) {
		this.systemProposedFailureModeID = systemProposedFailureModeID;
	}

	public String getEffectID() {
		return effectID;
	}

	public void setEffectID(String effectID) {
		this.effectID = effectID;
	}

	public String getCauseID() {
		return causeID;
	}

	public void setCauseID(String causeID) {
		this.causeID = causeID;
	}

	public String getInstructionID() {
		return instructionID;
	}

	public void setInstructionID(String instructionID) {
		this.instructionID = instructionID;
	}
	
}
