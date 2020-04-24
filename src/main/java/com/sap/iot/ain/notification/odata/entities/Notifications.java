package com.sap.iot.ain.notification.odata.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.apache.olingo.odata2.api.annotation.edm.EdmConcurrencyControl;
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;

import com.sap.iot.ain.equipment.odata.entities.Equipments;
import com.sap.iot.ain.odata.core.ODataEntity;
import com.sap.iot.ain.odata.core.annotations.CustomParameters;
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.MappingEntity;
import com.sap.iot.ain.odata.core.annotations.SapAnnotation;
import com.sap.iot.ain.odata.core.annotations.SapPropertyAnnotationList;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.security.Secure;
import com.sap.iot.ain.workorder.odata.entities.WorkOrderNotificationMapping;
import com.sap.iot.ain.workorder.odata.entities.WorkOrders;


@Table(name = Notifications.DB_TABLE)
@Entity(name = "Notifications")
@SystemParameters(client = true, language = true, scope = true, user_bp_id = true)
@CustomParameters(parameters = {"iv_equipmentid"})
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQUIPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class Notifications extends ODataEntity {
	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/Notifications\"";

	@Id
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"NotificationID\"")
	@FacetsProperty(maxLength = 32, nullable = false)
	private String notificationId;

	@Column(name = "\"ShortDescription\"")
	@FacetsProperty(maxLength = 256)
	private String shortDescription;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"Status\"")
	@FacetsProperty(maxLength = 20)
	private String status;

	@Column(name = "\"StatusDescription\"")
	@FacetsProperty(maxLength = 256)
	private String statusDescription;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"NotificationType\"")
	@FacetsProperty(maxLength = 4)
	private String notificationType;

	@Column(name = "\"NotificationTypeDescription\"")
	@FacetsProperty(maxLength = 256)
	private String notificationTypeDescription;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"Priority\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.Int32)
	private int priority;

	@Column(name = "\"PriorityDescription\"")
	@FacetsProperty(maxLength = 256)
	private String priorityDescription;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"IsInternal\"")
	@FacetsProperty(maxLength = 1)
	private String isInternal;

	@Column(name = "\"CreatedBy\"")
	@FacetsProperty(maxLength = 256)
	private String createdBy;

	@Column(name = "\"CreationDateTime\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	public Timestamp creationDateTime;


	@Column(name = "\"LastChangedBy\"")
	@FacetsProperty(maxLength = 256)
	private String lastChangedBy;

	@Column(name = "\"LastChangeDateTime\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	@EdmConcurrencyControl
	public Timestamp lastChangeDateTime;


	@Column(name = "\"LongDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String longDescription;

	@Column(name = "\"StartDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	public Timestamp startDate;


	@Column(name = "\"EndDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	public Timestamp endDate;

	@Column(name = "\"MalfunctionStartDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	public Timestamp malfunctionStartDate;

	@Column(name = "\"MalfunctionEndDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	public Timestamp malfunctionEndDate;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"ProgressStatus\"")
	@FacetsProperty(maxLength = 256)
	private String progressStatus;

	@Column(name = "\"ProgressStatusDescription\"")
	@FacetsProperty(maxLength = 256)
	private String progressStatusDescription;

	@Column(name = "\"EquipmentID\"")
	@FacetsProperty(maxLength = 32)
	private String equipmentId;

	@Column(name = "\"EquipmentName\"")
	@FacetsProperty(maxLength = 256)
	public String equipmentName;

	@Column(name = "\"RootEquipmentID\"")
	@FacetsProperty(maxLength = 32)
	private String rootEquipmentId;

	@Column(name = "\"RootEquipmentName\"")
	@FacetsProperty(maxLength = 256)
	public String rootEquipmentName;

	@Column(name = "\"LocationID\"")
	@FacetsProperty(maxLength = 32)
	public String locationId;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"SourceID\"")
	@FacetsProperty(maxLength = 32)
	public String sourceId;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"IsSourceActive\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.Int32)
	public int isSourceActive;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"Breakdown\"")
	@FacetsProperty(maxLength = 1)
	public String breakdown;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"Coordinates\"")
	@FacetsProperty(maxLength = 5000)
	public String coordinates;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"Source\"")
	@FacetsProperty(maxLength = 256)
	public String source;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"OperatorID\"")
	@FacetsProperty(maxLength = 32)
	public String operatorId;

	@Column(name = "\"Location\"")
	@FacetsProperty(maxLength = 256)
	public String location;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"AssetCoreEquipmentID\"")
	@FacetsProperty(maxLength = 32)
	public String assetCoreEquipmentId;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"Operator\"")
	@FacetsProperty(maxLength = 256)
	public String operator;

//	@SapPropertyAnnotationList(
//			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
//	@Column(name = "\"NoOfAlertsForNotification\"")
//	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.Int32)
//	public Integer noOfAlertsForNotification;

	@Column(name = "\"InternalID\"")
	@FacetsProperty(maxLength = 256)
	public String internalId;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "false")})
	@Column(name = "\"ModelID\"")
	@FacetsProperty(maxLength = 32)
	public String modelId;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ProposedFailureModeID\"")
	@FacetsProperty(maxLength = 32)
	public String proposedFailureModeID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ProposedFailureModeDisplayID\"")
	@FacetsProperty(maxLength = 32)
	public String proposedFailureModeDisplayID;
	
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ProposedFailureModeDesc\"")
	@FacetsProperty(maxLength = 255)
	public String proposedFailureModeDesc;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ConfirmedFailureModeID\"")
	@FacetsProperty(maxLength = 32)
	public String confirmedFailureModeID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ConfirmedFailureModeDisplayID\"")
	@FacetsProperty(maxLength = 32)
	public String confirmedFailureModeDisplayID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"ConfirmedFailureModeDesc\"")
	@FacetsProperty(maxLength = 255)
	public String confirmedFailureModeDesc;
	
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"SystemProposedFailureModeID\"")
	@FacetsProperty(maxLength = 32)
	public String systemProposedFailureModeID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"SystemProposedFailureModeDisplayID\"")
	@FacetsProperty(maxLength = 32)
	public String systemProposedFailureModeDisplayID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"SystemProposedFailureModeDesc\"")
	@FacetsProperty(maxLength = 255)
	public String systemProposedFailureModeDesc;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"EffectID\"")
	@FacetsProperty(maxLength = 32)
	public String effectID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"EffectDisplayID\"")
	@FacetsProperty(maxLength = 32)
	public String effectDisplayID;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"EffectDesc\"")
	@FacetsProperty(maxLength = 255)
	public String effectDesc;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"CauseID\"")
	@FacetsProperty(maxLength = 32)
	public String causeID;
	
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"CauseDisplayID\"")
	@FacetsProperty(maxLength = 32)
	public String causeDisplayID;

	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"CauseDesc\"")
	@FacetsProperty(maxLength = 255)
	public String causeDesc;
	
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"InstructionID\"")
	@FacetsProperty(maxLength = 32)
	public String instructionID;
	
	@SapPropertyAnnotationList(
			annotationAttributeList = {@SapAnnotation(name = "filterable", text = "true")})
	@Column(name = "\"InstructionTitle\"")
	@FacetsProperty(maxLength = 256)
	public String instructionTitle;
	

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
			targetEntity = NotificationItems.class)
	@JoinColumns({@JoinColumn(name = "\"NotificationID\"",
			referencedColumnName = "\"NotificationID\"", insertable = false, updatable = false)})
	private List<NotificationItems> notificationItems;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = WorkOrders.class)
	@MappingEntity(name = WorkOrderNotificationMapping.class, value = {
			@JoinColumn(name = "\"NotificationID\"", referencedColumnName = "\"NotificationID\"")})
	private List<WorkOrders> orders;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
			targetEntity = NotificationAlertMapping.class)
	@JoinColumns({@JoinColumn(name = "\"NotificationID\"",
			referencedColumnName = "\"NotificationID\"", insertable = false, updatable = false)})
	private List<NotificationAlertMapping> alerts;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Equipments.class)
	@JoinColumns({@JoinColumn(name = "\"ID\"", referencedColumnName = "\"AssetCoreEquipmentID\"",
			insertable = false, updatable = false)})
	private Equipments equipments;

	/**
	 * @return the equipments
	 */
	public Equipments getEquipments() {
		return equipments;
	}

	/**
	 * @param equipments the equipments to set
	 */
	public void setEquipments(Equipments equipments) {
		this.equipments = equipments;
	}



	public List<NotificationItems> getNotificationItems() {
		return notificationItems;
	}

	public void setNotificationItems(List<NotificationItems> notificationItems) {
		this.notificationItems = notificationItems;
	}



	public List<WorkOrders> getOrders() {
		return orders;
	}

	public void setOrders(List<WorkOrders> orders) {
		this.orders = orders;
	}



	public List<NotificationAlertMapping> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<NotificationAlertMapping> alerts) {
		this.alerts = alerts;
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



	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public int getIsSourceActive() {
		return isSourceActive;
	}

	public void setIsSourceActive(int isSourceActive) {
		this.isSourceActive = isSourceActive;
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
