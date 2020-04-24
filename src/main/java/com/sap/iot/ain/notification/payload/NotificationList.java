package com.sap.iot.ain.notification.payload;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.SapAnnotation;
import com.sap.iot.ain.odata.core.annotations.SapPropertyAnnotationList;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.reuse.TimestampSerializer;
import com.sap.iot.ain.security.Secure;

@Table(name = NotificationList.DB_TABLE)
@Entity(name = "NotificationList")
@SystemParameters(client = true, language = true, scope = true, user_bp_id = true)
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQIUPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class NotificationList {

	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/NotificationList\"";
	
	@Id
	@Column(name = "\"NotificationID\"")
	private String notificationId;

	@Column(name = "\"ShortDescription\"")
	private String shortDescription;

	@Column(name = "\"Status\"")
	private String status;

	@Column(name = "\"StatusDescription\"")
	private String statusDescription;

	@Column(name = "\"NotificationType\"")
	private String notificationType;

	@Column(name = "\"NotificationTypeDescription\"")
	private String notificationTypeDescription;

	@Column(name = "\"Priority\"")
	private int priority;

	@Column(name = "\"PriorityDescription\"")
	private String priorityDescription;

	@Column(name = "\"IsInternal\"")
	private String isInternal;

	@Column(name = "\"CreatedBy\"")
	private String createdBy;

	@Column(name = "\"CreationDateTime\"")
	@JsonSerialize(using = TimestampSerializer.class)
	public Timestamp creationDateTime;


	@Column(name = "\"LastChangedBy\"")
	private String lastChangedBy;

	@Column(name = "\"LastChangeDateTime\"")
	@JsonSerialize(using = TimestampSerializer.class)
	public Timestamp lastChangeDateTime;


	@Column(name = "\"LongDescription\"")
	private String longDescription;

	@Column(name = "\"StartDate\"")
	@JsonSerialize(using = TimestampSerializer.class)
	public Timestamp startDate;


	@Column(name = "\"EndDate\"")
	@JsonSerialize(using = TimestampSerializer.class)
	public Timestamp endDate;

	@Column(name = "\"MalfunctionStartDate\"")
	@JsonSerialize(using = TimestampSerializer.class)
	public Timestamp malfunctionStartDate;

	@Column(name = "\"MalfunctionEndDate\"")
	@JsonSerialize(using = TimestampSerializer.class)
	public Timestamp malfunctionEndDate;

	@Column(name = "\"ProgressStatus\"")
	private String progressStatus;

	@Column(name = "\"ProgressStatusDescription\"")
	private String progressStatusDescription;

	@Column(name = "\"EquipmentID\"")
	private String equipmentId;

	@Column(name = "\"EquipmentName\"")
	public String equipmentName;

	@Column(name = "\"RootEquipmentID\"")
	private String rootEquipmentId;

	@Column(name = "\"RootEquipmentName\"")
	public String rootEquipmentName;

	@Column(name = "\"LocationID\"")
	public String locationId;

	@Column(name = "\"Breakdown\"")
	public String breakdown;

	@Column(name = "\"Coordinates\"")
	public String coordinates;

	@Column(name = "\"Source\"")
	public String source;

	@Column(name = "\"OperatorID\"")
	public String operatorId;

	@Column(name = "\"Location\"")
	public String location;

	@Column(name = "\"AssetCoreEquipmentID\"")
	public String assetCoreEquipmentId;

	@Column(name = "\"Operator\"")
	public String operator;

//	@Column(name = "\"NoOfAlertsForNotification\"")
//	public Integer noOfAlertsForNotification;

	@Column(name = "\"InternalID\"")
	public String internalId;

	@Column(name = "\"ModelID\"")
	public String modelId;
	
	@Column(name = "\"ProposedFailureModeID\"")
	public String proposedFailureModeID;
	
	@Column(name = "\"ProposedFailureModeDisplayID\"")
	public String proposedFailureModeDisplayID;
	
	@Column(name = "\"ProposedFailureModeDesc\"")
	public String proposedFailureModeDesc;
	
	@Column(name = "\"ConfirmedFailureModeID\"")
	public String confirmedFailureModeID;
	
	@Column(name = "\"ConfirmedFailureModeDesc\"")
	public String confirmedFailureModeDesc;
	
	@Column(name = "\"ConfirmedFailureModeDisplayID\"")
	public String confirmedFailureModeDisplayID;
	
	@Column(name = "\"SystemProposedFailureModeID\"")
	public String systemProposedFailureModeID;
	
	@Column(name = "\"SystemProposedFailureModeDesc\"")
	public String systemProposedFailureModeDesc;
	
	@Column(name = "\"SystemProposedFailureModeDisplayID\"")
	public String systemProposedFailureModeDisplayID;
	
	@Column(name = "\"EffectID\"")
	public String effectID;
	
	@Column(name = "\"EffectDisplayID\"")
	public String effectDisplayID;

	@Column(name="\"EffectDesc\"")
	public String effectDesc;
	
	@Column(name = "\"CauseID\"")
	public String causeID;
	
	
	@Column(name = "\"CauseDisplayID\"")
	public String causeDisplayID;

	@Column(name="\"CauseDesc\"")
	public String causeDesc;
	
	
	@Column(name = "\"InstructionID\"")
	public String instructionID;
	
	@Column(name = "\"InstructionTitle\"")
	public String instructionTitle;
	
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationTypeDescription() {
		return notificationTypeDescription;
	}

	public void setNotificationTypeDescription(String notificationTypeDescription) {
		this.notificationTypeDescription = notificationTypeDescription;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getPriorityDescription() {
		return priorityDescription;
	}

	public void setPriorityDescription(String priorityDescription) {
		this.priorityDescription = priorityDescription;
	}

	public String getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreationDateTime() {
		return creationDateTime;
	}

	public void setCreationDateTime(Timestamp creationDateTime) {
		this.creationDateTime = creationDateTime;
	}

	public String getLastChangedBy() {
		return lastChangedBy;
	}

	public void setLastChangedBy(String lastChangedBy) {
		this.lastChangedBy = lastChangedBy;
	}

	public Timestamp getLastChangeDateTime() {
		return lastChangeDateTime;
	}

	public void setLastChangeDateTime(Timestamp lastChangeDateTime) {
		this.lastChangeDateTime = lastChangeDateTime;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
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

	public String getProgressStatus() {
		return progressStatus;
	}

	public void setProgressStatus(String progressStatus) {
		this.progressStatus = progressStatus;
	}

	public String getProgressStatusDescription() {
		return progressStatusDescription;
	}

	public void setProgressStatusDescription(String progressStatusDescription) {
		this.progressStatusDescription = progressStatusDescription;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getRootEquipmentId() {
		return rootEquipmentId;
	}

	public void setRootEquipmentId(String rootEquipmentId) {
		this.rootEquipmentId = rootEquipmentId;
	}

	public String getRootEquipmentName() {
		return rootEquipmentName;
	}

	public void setRootEquipmentName(String rootEquipmentName) {
		this.rootEquipmentName = rootEquipmentName;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(String breakdown) {
		this.breakdown = breakdown;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAssetCoreEquipmentId() {
		return assetCoreEquipmentId;
	}

	public void setAssetCoreEquipmentId(String assetCoreEquipmentId) {
		this.assetCoreEquipmentId = assetCoreEquipmentId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

//	public Integer getNoOfAlertsForNotification() {
//		return noOfAlertsForNotification;
//	}
//
//	public void setNoOfAlertsForNotification(Integer noOfAlertsForNotification) {
//		this.noOfAlertsForNotification = noOfAlertsForNotification;
//	}

	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getProposedFailureModeID() {
		return proposedFailureModeID;
	}

	public void setProposedFailureModeID(String proposedFailureModeID) {
		this.proposedFailureModeID = proposedFailureModeID;
	}

	public String getProposedFailureModeDisplayID() {
		return proposedFailureModeDisplayID;
	}

	public void setProposedFailureModeDisplayID(String proposedFailureModeDisplayID) {
		this.proposedFailureModeDisplayID = proposedFailureModeDisplayID;
	}

	public String getConfirmedFailureModeID() {
		return confirmedFailureModeID;
	}

	public void setConfirmedFailureModeID(String confirmedFailureModeID) {
		this.confirmedFailureModeID = confirmedFailureModeID;
	}

	public String getConfirmedFailureModeDisplayID() {
		return confirmedFailureModeDisplayID;
	}

	public void setConfirmedFailureModeDisplayID(String confirmedFailureModeDisplayID) {
		this.confirmedFailureModeDisplayID = confirmedFailureModeDisplayID;
	}

	public String getProposedFailureModeDesc() {
		return proposedFailureModeDesc;
	}

	public void setProposedFailureModeDesc(String proposedFailureModeDesc) {
		this.proposedFailureModeDesc = proposedFailureModeDesc;
	}

	public String getConfirmedFailureModeDesc() {
		return confirmedFailureModeDesc;
	}

	public void setConfirmedFailureModeDesc(String confirmedFailureModeDesc) {
		this.confirmedFailureModeDesc = confirmedFailureModeDesc;
	}

	public String getSystemProposedFailureModeID() {
		return systemProposedFailureModeID;
	}

	public void setSystemProposedFailureModeID(String systemProposedFailureModeID) {
		this.systemProposedFailureModeID = systemProposedFailureModeID;
	}

	public String getSystemProposedFailureModeDesc() {
		return systemProposedFailureModeDesc;
	}

	public void setSystemProposedFailureModeDesc(String systemProposedFailureModeDesc) {
		this.systemProposedFailureModeDesc = systemProposedFailureModeDesc;
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
