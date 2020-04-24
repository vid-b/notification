package com.sap.iot.ain.notification.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//import com.sap.iot.ain.funclocation.entities.GeometryConverter;
import com.sap.iot.ain.reuse.Client;
import com.sap.iot.ain.reuse.ClientAccess;
import com.sap.iot.ain.reuse.ClientEntityListener;
import com.sap.iot.ain.reuse.SystemAdministrativeData;
import com.vividsolutions.jts.geom.Geometry;

@Entity(name = "NotificationHeaderEntity")
@EntityListeners({ClientEntityListener.class})
@Table(name = "\"sap.ain.metaData::Notification.Header\"")
public class NotificationHeaderEntity implements Serializable, ClientAccess {

	private static final long serialVersionUID = 1L;

	@Embedded
	private Client client = new Client();

	@Id
	@Column(name = "\"ID\"")
	private String id;

	@Column(name = "\"ExternalID\"")
	private String externalId;

	@Column(name = "\"NotificationID\"")
	private String notificationId;

	@Column(name = "\"EquipmentID\"")
	private String equipmentId;

	@Column(name = "\"Type\"")
	private String type;

	@Column(name = "\"Priority\"")
	private int priority;

	@Column(name = "\"StartDate\"")
	private Timestamp startDate;

	@Column(name = "\"EndDate\"")
	private Timestamp endDate;

	@Column(name = "\"MalfunctionStartDate\"")
	private Timestamp malfunctionStartDate;

	@Column(name = "\"MalfunctionEndDate\"")
	private Timestamp malfunctionEndDate;

	@Column(name = "\"isInternal\"")
	private String isInternal;

	@Column(name = "\"isMarkedForDeletion\"")
	private String isMarkedForDeletion;

	@Column(name = "\"LocationID\"")
	private String locationId;

	@Column(name = "\"Breakdown\"")
	private String breakdown;

	@Column(name = "\"Geometry\"")
//	@Convert(converter = GeometryConverter.class)
	private Geometry geometry;

	@Column(name = "\"Operator\"")
	private String operator;

	@Column(name = "\"InternalID\"")
	private String internalId;
	
	@Column(name = "\"ConfirmedFailureModeID\"")
	private String confirmedFailureModeID;
	
	@Column(name = "\"ProposedFailureModeID\"")
	private String proposedFailureModeID;
	
	@Column (name= "\"SystemProposedFailureModeID\"")
	private String systemProposedFailureModeID;
	
	@Column (name= "\"EffectID\"")
	private String effectID;
	
	@Column (name= "\"CauseID\"")
	private String causeID;
	
	@Column (name="\"InstructionID\"")
	private String instructionID;

	@OneToMany(mappedBy = "headerEntity", cascade = CascadeType.ALL)
	private List<NotificationStatusEntity> status;

	@OneToMany(mappedBy = "notificationHeaderEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<NotificationDescriptionEntity> descriptions;

	@OneToMany(mappedBy = "notificationHeaderEntityForItems", cascade = CascadeType.ALL,
			orphanRemoval = true, fetch = FetchType.EAGER)
	private List<NotificationItemEntity> notificationItemEntity;

//	@OneToMany(mappedBy = "notificationHeaderEntityForAlerts", cascade = CascadeType.ALL,
//			orphanRemoval = true, fetch = FetchType.EAGER)
//	private List<NotificationAlertMappingEntity> notificationAlertMappingEntity;

	public List<NotificationStatusEntity> getStatus() {
		return status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setStatus(List<NotificationStatusEntity> status) {
		this.status = status;
	}

	@Embedded
	private SystemAdministrativeData systemAdministrativeData = new SystemAdministrativeData();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}



	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
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



	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}

	public String getIsMarkedForDeletion() {
		return isMarkedForDeletion;
	}

	public void setIsMarkedForDeletion(String isMarkedForDeletion) {
		this.isMarkedForDeletion = isMarkedForDeletion;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<NotificationDescriptionEntity> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<NotificationDescriptionEntity> descriptions) {
		this.descriptions = descriptions;
	}

	public SystemAdministrativeData getSystemAdministrativeData() {
		return systemAdministrativeData;
	}

	public void setSystemAdministrativeData(SystemAdministrativeData systemAdministrativeData) {
		this.systemAdministrativeData = systemAdministrativeData;
	}

	public List<NotificationItemEntity> getNotificationItemEntity() {
		return notificationItemEntity;
	}

	public void setNotificationItemEntity(List<NotificationItemEntity> notificationItemEntity) {
		this.notificationItemEntity = notificationItemEntity;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}



	public String getInternalId() {
		return internalId;
	}

	public void setInternalId(String internalId) {
		this.internalId = internalId;
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
	
	

//	public List<NotificationAlertMappingEntity> getNotificationAlertMappingEntity() {
//		return notificationAlertMappingEntity;
//	}

//	public void setNotificationAlertMappingEntity(
//			List<NotificationAlertMappingEntity> notificationAlertMappingEntity) {
//		this.notificationAlertMappingEntity = notificationAlertMappingEntity;
//	}

}
