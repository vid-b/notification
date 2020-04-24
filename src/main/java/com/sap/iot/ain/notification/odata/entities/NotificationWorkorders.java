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
import com.sap.iot.ain.odata.core.annotations.Entity;
import com.sap.iot.ain.odata.core.annotations.FacetsProperty;
import com.sap.iot.ain.odata.core.annotations.MappingEntity;
import com.sap.iot.ain.odata.core.annotations.SystemParameters;
import com.sap.iot.ain.odata.core.annotations.Table;
import com.sap.iot.ain.security.Secure;
import com.sap.iot.ain.workorder.odata.entities.WorkOrderNotificationMapping;
import com.sap.iot.ain.workorder.odata.entities.WorkOrderStepsEntity;

@Table(name = NotificationWorkorders.DB_TABLE)
@Entity(name = "NotificationWorkOrders")
@SystemParameters(client = true, language = true, scope = true, user_bp_id = true)
@Secure(read = {"EQUIPMENT_READ"}, write = {"EQIUPMENT_EDIT"}, delete = {"EQUIPMENT_DELETE"})
public class NotificationWorkorders {
	public static final String DB_TABLE = "\"_SYS_BIC\".\"sap.ain.views/NotificationWorkorders\"";

	@Id
	@Column(name = "\"NotificationID\"")
	@FacetsProperty(maxLength = 40, nullable = false)
	private String notificationID;

	@Id
	@Column(name = "\"WorkOrderID\"")
	@FacetsProperty(maxLength = 40, nullable = false)
	private String workOrderID;

	@Column(name = "\"ShortDescription\"")
	@FacetsProperty(maxLength = 40)
	private String shortDescription;

	@Column(name = "\"Status\"")
	@FacetsProperty(maxLength = 20)
	private String status;

	@Column(name = "\"StatusDescription\"")
	@FacetsProperty(maxLength = 256)
	private String statusDescription;

	@Column(name = "\"WorkOrderType\"")
	@FacetsProperty(maxLength = 4)
	private String workOrderType;

	@Column(name = "\"WorkOrderTypeDescription\"")
	@FacetsProperty(maxLength = 256)
	private String workOrderTypeDescription;

	@Column(name = "\"Priority\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.Int32)
	private int priority;

	@Column(name = "\"PriorityDescription\"")
	@FacetsProperty(maxLength = 256)
	private String priorityDescription;

	@Column(name = "\"Plant\"")
	@FacetsProperty(maxLength = 10)
	private String plant;

	@Column(name = "\"WorkCenter\"")
	@FacetsProperty(maxLength = 10)
	private String workCenter;

	@Column(name = "\"IsInternal\"")
	@FacetsProperty(maxLength = 1)
	private String isInternal;

	@Column(name = "\"CreatedBy\"")
	@FacetsProperty(maxLength = 256)
	private String createdBy;

	@Column(name = "\"CreationDateTime\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	private Timestamp creationDateTime;

	@Column(name = "\"LastChangedBy\"")
	@FacetsProperty(maxLength = 256)
	private String lastChangedBy;

	@Column(name = "\"LastChangeDateTime\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	@EdmConcurrencyControl
	private Timestamp lastChangeDateTime;

	@Column(name = "\"LongDescription\"")
	@FacetsProperty(maxLength = 5000)
	private String longDescription;

	@Column(name = "\"BasicStartDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	private Timestamp basicStartDate;

	@Column(name = "\"BasicEndDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	private Timestamp basicEndDate;

	@Column(name = "\"ActualStartDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	private Timestamp actualStartDate;

	@Column(name = "\"ActualEndDate\"")
	@FacetsProperty(edmSimpleTypeKind = EdmSimpleTypeKind.DateTime)
	private Timestamp actualEndDate;

	@Column(name = "\"InternalID\"")
	@FacetsProperty(maxLength = 40)
	private String internalID;

	@Column(name = "\"ProgressStatus\"")
	@FacetsProperty(maxLength = 10)
	private String progressStatus;

	@Column(name = "\"ProgressStatusDescription\"")
	@FacetsProperty(maxLength = 256)
	private String progressStatusDescription;

	@Column(name = "\"EquipmentID\"")
	@FacetsProperty(maxLength = 40)
	private String equipmentID;

	@Column(name = "\"EquipmentName\"")
	@FacetsProperty(maxLength = 256)
	private String equipmentName;

	@Column(name = "\"RootEquipmentID\"")
	@FacetsProperty(maxLength = 40)
	private String rootEquipmentID;

	@Column(name = "\"RootEquipmentName\"")
	@FacetsProperty(maxLength = 256)
	private String rootEquipmentName;

	@Column(name = "\"PersonResponsible\"")
	@FacetsProperty(maxLength = 40)
	private String personResponsible;

	@Column(name = "\"LocationID\"")
	@FacetsProperty(maxLength = 40)
	public String locationID;


	@Column(name = "\"Coordinates\"")
	@FacetsProperty(maxLength = 5000)
	public String coordinates;

	@Column(name = "\"Source\"")
	@FacetsProperty(maxLength = 40)
	public String source;

	@Column(name = "\"Operator\"")
	@FacetsProperty(maxLength = 256)
	public String operator;

	@Column(name = "\"OperatorID\"")
	@FacetsProperty(maxLength = 40)
	public String operatorID;

	@Column(name = "\"Location\"")
	@FacetsProperty(maxLength = 256)
	public String location;

	@Column(name = "\"AssetCoreEquipmentID\"")
	@FacetsProperty(maxLength = 40)
	public String assetCoreEquipmentID;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
			targetEntity = WorkOrderStepsEntity.class)
	@JoinColumns({@JoinColumn(name = "\"OrderID\"", referencedColumnName = "\"WorkOrderID\"",
			insertable = false, updatable = false)})
	private List<WorkOrderStepsEntity> Steps;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
			targetEntity = Notifications.class)
	@MappingEntity(name = WorkOrderNotificationMapping.class,
			value = {@JoinColumn(name = "\"OrderID\"", referencedColumnName = "\"WorkOrderID\"")})
	private List<Notifications> notifications;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Equipments.class)
	@JoinColumns({@JoinColumn(name = "\"ID\"", referencedColumnName = "\"AssetCoreEquipmentID\"",
			insertable = false, updatable = false)})
	private Equipments equipments;

	public String getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}

	public String getWorkOrderID() {
		return workOrderID;
	}

	public void setWorkOrderID(String workOrderID) {
		this.workOrderID = workOrderID;
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

	public String getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}

	public String getWorkOrderTypeDescription() {
		return workOrderTypeDescription;
	}

	public void setWorkOrderTypeDescription(String workOrderTypeDescription) {
		this.workOrderTypeDescription = workOrderTypeDescription;
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

	public String getPlant() {
		return plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
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

	public Timestamp getBasicStartDate() {
		return basicStartDate;
	}

	public void setBasicStartDate(Timestamp basicStartDate) {
		this.basicStartDate = basicStartDate;
	}

	public Timestamp getBasicEndDate() {
		return basicEndDate;
	}

	public void setBasicEndDate(Timestamp basicEndDate) {
		this.basicEndDate = basicEndDate;
	}

	public Timestamp getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Timestamp actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Timestamp getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(Timestamp actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	public String getInternalID() {
		return internalID;
	}

	public void setInternalID(String internalID) {
		this.internalID = internalID;
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

	public String getEquipmentID() {
		return equipmentID;
	}

	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getRootEquipmentID() {
		return rootEquipmentID;
	}

	public void setRootEquipmentID(String rootEquipmentID) {
		this.rootEquipmentID = rootEquipmentID;
	}

	public String getRootEquipmentName() {
		return rootEquipmentName;
	}

	public void setRootEquipmentName(String rootEquipmentName) {
		this.rootEquipmentName = rootEquipmentName;
	}

	public String getPersonResponsible() {
		return personResponsible;
	}

	public void setPersonResponsible(String personResponsible) {
		this.personResponsible = personResponsible;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
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

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAssetCoreEquipmentID() {
		return assetCoreEquipmentID;
	}

	public void setAssetCoreEquipmentID(String assetCoreEquipmentID) {
		this.assetCoreEquipmentID = assetCoreEquipmentID;
	}

	public List<WorkOrderStepsEntity> getSteps() {
		return Steps;
	}

	public void setSteps(List<WorkOrderStepsEntity> steps) {
		Steps = steps;
	}

	public List<Notifications> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notifications> notifications) {
		this.notifications = notifications;
	}

	public Equipments getEquipments() {
		return equipments;
	}

	public void setEquipments(Equipments equipments) {
		this.equipments = equipments;
	}
}
