package com.sap.iot.ain.notification.context;

import java.io.Serializable;

import com.sap.iot.ain.reuse.dao.StateChangeContext;

/**
 * 
 * @author I322597
 *
 */
public class NotificationContext extends StateChangeContext implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isNotificationExist;
	private boolean isTypeValid;
	private boolean isPriorityValid;
	private boolean isStatusValid;

	private boolean isNotificationIdExist;
	private boolean isEquipmentExist;
	private boolean hasWriteAcceesOnEquipment;
	
	private boolean isLocationExist;
	private boolean isProposeFailureModeValid;
	private boolean isConfirmedFailureModeValid;
	private boolean isSystemProposedFailureModeValid;
	private boolean isEffectValid;
	private boolean isCauseValid;
	private boolean isInstructionValid;
	
	public boolean isTypeValid() {
		return isTypeValid;
	}

	public void setTypeValid(boolean isTypeValid) {
		this.isTypeValid = isTypeValid;
	}

	public boolean isPriorityValid() {
		return isPriorityValid;
	}

	public void setPriorityValid(boolean isPriorityValid) {
		this.isPriorityValid = isPriorityValid;
	}

	public boolean isStatusValid() {
		return isStatusValid;
	}

	public void setStatusValid(boolean isStatusValid) {
		this.isStatusValid = isStatusValid;
	}

	public boolean isNotificationExist() {
		return isNotificationExist;
	}

	public void setNotificationExist(boolean isNotificationExist) {
		this.isNotificationExist = isNotificationExist;
	}

	public boolean isNotificationIdExist() {
		return isNotificationIdExist;
	}

	public void setNotificationIdExist(boolean isNotificationIdExist) {
		this.isNotificationIdExist = isNotificationIdExist;
	}

	public boolean isEquipmentExist() {
		return isEquipmentExist;
	}

	public void setEquipmentExist(boolean isEquipmentExist) {
		this.isEquipmentExist = isEquipmentExist;
	}

	public boolean isHasWriteAcceesOnEquipment() {
		return hasWriteAcceesOnEquipment;
	}

	public void setHasWriteAcceesOnEquipment(boolean hasWriteAcceesOnEquipment) {
		this.hasWriteAcceesOnEquipment = hasWriteAcceesOnEquipment;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isLocationExist() {
		return isLocationExist;
	}

	public void setLocationExist(boolean isLocationExist) {
		this.isLocationExist = isLocationExist;
	}

	public boolean isProposeFailureModeValid() {
		return isProposeFailureModeValid;
	}

	public void setProposeFailureModeValid(boolean isProposeFailureModeValid) {
		this.isProposeFailureModeValid = isProposeFailureModeValid;
	}

	public boolean isConfirmedFailureModeValid() {
		return isConfirmedFailureModeValid;
	}

	public void setConfirmedFailureModeValid(boolean isConfirmedFailureModeValid) {
		this.isConfirmedFailureModeValid = isConfirmedFailureModeValid;
	}

	public boolean isSystemProposedFailureModeValid() {
		return isSystemProposedFailureModeValid;
	}

	public void setSystemProposedFailureModeValid(boolean isSystemProposedFailureModeValid) {
		this.isSystemProposedFailureModeValid = isSystemProposedFailureModeValid;
	}

	public boolean isEffectValid() {
		return isEffectValid;
	}

	public void setEffectValid(boolean isEffectValid) {
		this.isEffectValid = isEffectValid;
	}

	public boolean isCauseValid() {
		return isCauseValid;
	}

	public void setCauseValid(boolean isCauseValid) {
		this.isCauseValid = isCauseValid;
	}

	public boolean isInstructionValid() {
		return isInstructionValid;
	}

	public void setInstructionValid(boolean isInstructionValid) {
		this.isInstructionValid = isInstructionValid;
	}


}
