package com.sap.iot.ain.notification.payload;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;


import java.sql.SQLException;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
//
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sap.iot.ain.reuse.DateHelper;
import com.sap.iot.ain.reuse.DateSerializer;
import com.sap.iot.ain.reuse.annotation.Size;
import com.sap.iot.ain.validation.utils.Order;

@JsonInclude(JsonInclude.Include.ALWAYS)
public class NotificationsForEquipment implements RowMapper<NotificationsForEquipment>{
	
	private String type;
	
	private Integer count;

	private String notificationID;
	
	private String InternalID;
	
	private NotificationDescription description;
	
	private String typeDescription;
	
	private String priority;
	
	private String priorityDescription;
	
	private String progressStatus;
	
	private String progressStatusDescription;
	
	private String status;
	
	private String statusDescription;
	
	@JsonSerialize(using = DateSerializer.class)
	private DateTime startDate;

	@JsonSerialize(using = DateSerializer.class)
	private DateTime endDate;

	private String malfunctionStartDate;

	private String malfunctionEndDate;
	
	private String proposedFailureModeID;
	
	private String confirmedFailureModeID;
	
	private String systemProposedFailureModeID;
	
	private String effectID;
	
	private String causeID;
	
	private String instructionID;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(String notificationID) {
		this.notificationID = notificationID;
	}
	
	public String getInternalID() {
		return InternalID;
	}

	public void setInternalID(String InternalID) {
		this.InternalID = InternalID;
	}
	
	public NotificationDescription getDescription() {
		return description;
	}

	public void setDescription(NotificationDescription description) {
		this.description = description;
	}
	
	public String getTypeDescription() {
		return typeDescription;
	}

	public void setTypeDescription(String typeDescription) {
		this.typeDescription = typeDescription;
	}
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getPriorityDescription() {
		return priorityDescription;
	}

	public void setPriorityDescription(String priorityDescription) {
		this.priorityDescription = priorityDescription;
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

	public String getMalfunctionStartDate() {
		return malfunctionStartDate;
	}

	public void setMalfunctionStartDate(String malfunctionStartDate) {
		this.malfunctionStartDate = malfunctionStartDate;
	}

	public String getMalfunctionEndDate() {
		return malfunctionEndDate;
	}

	public void setMalfunctionEndDate(String malfunctionEndDate) {
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

	@Override
	public NotificationsForEquipment mapRow(ResultSet rs, int rowNum) throws SQLException {
		NotificationsForEquipment notifications = null;
		
		try{
			notifications = this.getClass().newInstance();
			notifications.setType(rs.getString("NotificationType"));
			notifications.setCount(rs.getInt("Count"));
			notifications.setNotificationID(rs.getString("NotificationID"));
			notifications.setInternalID(rs.getString("InternalID"));
			NotificationDescription desc = new NotificationDescription();
			desc.setShortDescription(rs.getString("ShortDescription"));
			desc.setLongDescription(rs.getString("LongDescription"));
			notifications.setDescription(desc);
			notifications.setTypeDescription(rs.getString("NotificationTypeDescription"));
			notifications.setPriority(rs.getString("Priority"));
			notifications.setPriorityDescription(rs.getString("PriorityDescription"));
			notifications.setProgressStatus(rs.getString("ProgressStatus"));
			notifications.setProgressStatusDescription(rs.getString("ProgressStatusDescription"));
			notifications.setStatus(rs.getString("Status"));
			notifications.setStatusDescription(rs.getString("StatusDescription"));
			notifications.setStartDate(DateHelper.getDateTime(rs.getTimestamp("StartDate")));
			notifications.setEndDate(DateHelper.getDateTime(rs.getTimestamp("EndDate")));
			notifications.setConfirmedFailureModeID(rs.getString("ConfirmedFailureModeID"));
			notifications.setProposedFailureModeID(rs.getString("ProposedFailureModeID"));
			notifications.setCauseID(rs.getString("CauseID"));
			notifications.setInstructionID(rs.getString("InstructionID"));
			notifications.setSystemProposedFailureModeID(rs.getString("SystemProposedFailureModeID"));
			notifications.setEffectID(rs.getString("EffectID"));
		}catch(Exception e){
			Logger logger = LoggerFactory.getLogger(NotificationsForEquipment.class);
            logger.error("[NotificationDao.NotificationsForEquipment]: " + Arrays.toString(e.getStackTrace()));
		}
		return notifications;
	}
}



