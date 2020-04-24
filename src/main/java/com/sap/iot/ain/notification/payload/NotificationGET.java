package com.sap.iot.ain.notification.payload;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sap.iot.ain.reuse.DateSerializer;
import com.sap.iot.ain.reuse.SystemAdministrativeData;
import com.sap.iot.ain.reuse.TimestampSerializer;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationGET {

	private String notificationID;

	private String externalID;

	private String equipmentID;

	private String type;

	private String typeDescription;

	private String priority;

	private String priorityDescription;

	private List<String> status;

	private String statusDescription;

	@JsonSerialize(using = DateSerializer.class)
	private DateTime startDate;

	@JsonSerialize(using = DateSerializer.class)
	private DateTime endDate;

	@JsonSerialize(using = TimestampSerializer.class)
	private Timestamp malfunctionStartDate;

	@JsonSerialize(using = TimestampSerializer.class)
	private Timestamp malfunctionEndDate;

	private String isInternal;
	
	private String internalId;
	
	private String  proposedFailureModeID;
	
	private String proposedFailureModeDisplayID;
	
	private String proposedFailureModeDesc;
	
	private String confirmedFailureModeID;
	
	private String confirmedFailureModeDisplayID;
	
	private String confirmedFailureModeDesc;
	
	private String systemProposedFailureModeID;
	
	private String systemProposedFailureModeDisplayID;
	
	private String systemProposedFailureModeDesc;
	
	private String effectID;
	
	private String effectDisplayID;

	private String effectDesc;
	
	private String causeID;
	
	private String causeDisplayID;

	private String causeDesc;
	
	private String instructionID;
	
	private String instructionTitle;

	private NotificationDescription description;

	private SystemAdministrativeData systemAdministrativeData;

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

	public String getPriorityDescription() {
		return priorityDescription;
	}

	public void setPriorityDescription(String priorityDescription) {
		this.priorityDescription = priorityDescription;
	}

	public NotificationDescription getDescription() {
		return description;
	}

	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public void setDescription(NotificationDescription description) {
		this.description = description;
	}

	public SystemAdministrativeData getSystemAdministrativeData() {
		return systemAdministrativeData;
	}

	public void setSystemAdministrativeData(SystemAdministrativeData systemAdministrativeData) {
		this.systemAdministrativeData = systemAdministrativeData;
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

	public String getProposedFailureModeID() {
		return proposedFailureModeID;
	}

	public void setProposedFailureModeID(String proposedFailureModeID) {
		this.proposedFailureModeID = proposedFailureModeID;
	}

	public String getConfirmedFailureModeID() {
		return confirmedFailureModeID;
	}

	public void setConfirmedFailureModeID(String confirmedFailureModeID) {
		this.confirmedFailureModeID = confirmedFailureModeID;
	}

	public String getSystemProposedFailureModeID() {
		return systemProposedFailureModeID;
	}

	public void setSystemProposedFailureModeID(String systemProposedFailureModeID) {
		this.systemProposedFailureModeID = systemProposedFailureModeID;
	}

	public String getSystemProposedFailureModeDisplayID() {
		return systemProposedFailureModeDisplayID;
	}

	public void setSystemProposedFailureModeDisplayID(String systemProposedFailureModeDisplayID) {
		this.systemProposedFailureModeDisplayID = systemProposedFailureModeDisplayID;
	}

	public String getEffectID() {
		return effectID;
	}

	public void setEffectID(String effectID) {
		this.effectID = effectID;
	}

	public String getEffectDisplayID() {
		return effectDisplayID;
	}

	public void setEffectDisplayID(String effectDisplayID) {
		this.effectDisplayID = effectDisplayID;
	}

	public String getCauseID() {
		return causeID;
	}

	public void setCauseID(String causeID) {
		this.causeID = causeID;
	}

	public String getCauseDisplayID() {
		return causeDisplayID;
	}

	public void setCauseDisplayID(String causeDisplayID) {
		this.causeDisplayID = causeDisplayID;
	}

	public String getInstructionID() {
		return instructionID;
	}

	public void setInstructionID(String instructionID) {
		this.instructionID = instructionID;
	}

	public String getInstructionTitle() {
		return instructionTitle;
	}

	public void setInstructionTitle(String instructionTitle) {
		this.instructionTitle = instructionTitle;
	}

	public String getProposedFailureModeDisplayID() {
		return proposedFailureModeDisplayID;
	}

	public void setProposedFailureModeDisplayID(String proposedFailureModeDisplayID) {
		this.proposedFailureModeDisplayID = proposedFailureModeDisplayID;
	}

	public String getProposedFailureModeDesc() {
		return proposedFailureModeDesc;
	}

	public void setProposedFailureModeDesc(String proposedFailureModeDesc) {
		this.proposedFailureModeDesc = proposedFailureModeDesc;
	}

	public String getConfirmedFailureModeDisplayID() {
		return confirmedFailureModeDisplayID;
	}

	public void setConfirmedFailureModeDisplayID(String confirmedFailureModeDisplayID) {
		this.confirmedFailureModeDisplayID = confirmedFailureModeDisplayID;
	}

	public String getConfirmedFailureModeDesc() {
		return confirmedFailureModeDesc;
	}

	public void setConfirmedFailureModeDesc(String confirmedFailureModeDesc) {
		this.confirmedFailureModeDesc = confirmedFailureModeDesc;
	}

	public String getSystemProposedFailureModeDesc() {
		return systemProposedFailureModeDesc;
	}

	public void setSystemProposedFailureModeDesc(String systemProposedFailureModeDesc) {
		this.systemProposedFailureModeDesc = systemProposedFailureModeDesc;
	}

	public String getEffectDesc() {
		return effectDesc;
	}

	public void setEffectDesc(String effectDesc) {
		this.effectDesc = effectDesc;
	}

	public String getCauseDesc() {
		return causeDesc;
	}

	public void setCauseDesc(String causeDesc) {
		this.causeDesc = causeDesc;
	}
}
