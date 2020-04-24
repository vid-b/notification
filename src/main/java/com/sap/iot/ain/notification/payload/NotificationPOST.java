package com.sap.iot.ain.notification.payload;

import java.util.List;

import javax.persistence.Column;
import javax.validation.Valid;

import org.geojson.GeoJsonObject;
import org.javers.core.metamodel.annotation.DiffIgnore;
import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.sap.iot.ain.funclocation.validations.GeoJsonValidations;
import com.sap.iot.ain.notification.validation.NotificationPOSTValiadtions;
import com.sap.iot.ain.reuse.DateSerializer;
import com.sap.iot.ain.reuse.annotation.DateFormat;
import com.sap.iot.ain.reuse.annotation.Pattern;
import com.sap.iot.ain.reuse.annotation.Size;
import com.sap.iot.ain.reuse.payload.AdminData;
import com.sap.iot.ain.validation.utils.Order;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NotificationPOSTValiadtions(groups = Order.Level4.class)
public class NotificationPOST {

	@Size(field = "externalID", max = 40, groups = Order.Level1.class)
	private String externalID;

	@Size(field = "externalSystemID", max = 32, groups = Order.Level1.class)
	private String externalSystemID;

	@Size(field = "equipmentID", max = 32, groups = Order.Level1.class)
	private String equipmentID;

	@Size(field = "locationID", max = 32, groups = Order.Level1.class)
	private String locationID;

	@Pattern(regexp = "0|1", field = "breakdown", groups = Order.Level1.class)
	@Size(field = "breakdown", max = 1)
	private String breakdown;

	@Size(field = "type", max = 4)
	private String type;

	@Size(field = "priority", max = 10)
	private String priority;

	@Valid
	private List<String> status;

	@DateFormat(field = "Start Date", groups = Order.Level2.class)
	@JsonSerialize(using = DateSerializer.class)
	private DateTime startDate;

	@DateFormat(field = "End Date", groups = Order.Level2.class)
	@JsonSerialize(using = DateSerializer.class)
	private DateTime endDate;

	private Object malfunctionStartDate;

	private Object malfunctionEndDate;

	@Valid
	private NotificationDescription description;

	@Valid
	private List<NotificationDescription> descriptions;

	@Valid
	private List<NotificationItems> items;

	@DiffIgnore
	private AdminData adminData;

	@Valid
//	@GeoJsonValidations
	private GeoJsonObject geometry;

	@Size(field = "Operator", max = 32, groups = Order.Level2.class)
	private String operator;

	@Valid
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<AlertID> alertIDs;

	@Size(field = "workCenterSystemId", max = 2000, groups = Order.Level1.class)
	private String workCenterSystemId;

	@Size(field = "workCenter", max = 2000, groups = Order.Level1.class)
	private String workCenter;

	@Size(field = "maintenancePlanGroupSystemId", max = 2000, groups = Order.Level1.class)
	private String maintenancePlanGroupSystemId;

	@Size(field = "maintenancePlannerGroup", max = 2000, groups = Order.Level1.class)
	private String maintenancePlannerGroup;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(field = "proposedFailureModeID", max = 32, groups = Order.Level1.class)
	private String proposedFailureModeID;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(field = "confirmedFailureModeID", max = 32, groups = Order.Level1.class)
	private String confirmedFailureModeID;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(field = "systemProposedFailureModeID", max = 32, groups = Order.Level1.class)
	public String systemProposedFailureModeID;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(field = "effectID", max = 32, groups = Order.Level1.class)
	public String effectID;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(field = "causeID", max = 32, groups = Order.Level1.class)
	public String causeID;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Size(field = "instructionID", max = 32, groups = Order.Level1.class)
	public String instructionID;
	
	public String getExternalID() {
		return externalID;
	}

	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}

	public String getExternalSystemID() {
		return externalSystemID;
	}

	public void setExternalSystemID(String externalSystemID) {
		this.externalSystemID = externalSystemID;
	}

	public String getEquipmentID() {
		return equipmentID;
	}

	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
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


	public Object getMalfunctionStartDate() {
		return malfunctionStartDate;
	}

	public void setMalfunctionStartDate(Object malfunctionStartDate) {
		this.malfunctionStartDate = malfunctionStartDate;
	}

	public Object getMalfunctionEndDate() {
		return malfunctionEndDate;
	}

	public void setMalfunctionEndDate(Object malfunctionEndDate) {
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

	public List<NotificationItems> getItems() {
		return items;
	}

	public void setItems(List<NotificationItems> items) {
		this.items = items;
	}

	public String getLocationID() {
		return locationID;
	}

	public void setLocationID(String locationID) {
		this.locationID = locationID;
	}

	public String getBreakdown() {
		if (breakdown == null || breakdown.trim().isEmpty())
			return null;
		else
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

	public GeoJsonObject getGeometry() {
		return geometry;
	}

	public void setGeometry(GeoJsonObject geometry) {
		this.geometry = geometry;
	}

	public List<AlertID> getAlertIDs() {
		return alertIDs;
	}

	public void setAlertIDs(List<AlertID> alertIDs) {
		this.alertIDs = alertIDs;
	}

	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	public String getMaintenancePlannerGroup() {
		return maintenancePlannerGroup;
	}

	public void setMaintenancePlannerGroup(String maintenancePlannerGroup) {
		this.maintenancePlannerGroup = maintenancePlannerGroup;
	}

	public String getWorkCenterSystemId() {
		return workCenterSystemId;
	}

	public void setWorkCenterSystemId(String workCenterSystemId) {
		this.workCenterSystemId = workCenterSystemId;
	}

	public String getMaintenancePlanGroupSystemId() {
		return maintenancePlanGroupSystemId;
	}

	public void setMaintenancePlanGroupSystemId(String maintenancePlanGroupSystemId) {
		this.maintenancePlanGroupSystemId = maintenancePlanGroupSystemId;
	}

	public List<NotificationDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(List<NotificationDescription> descriptions) {
		this.descriptions = descriptions;
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
