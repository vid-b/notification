package com.sap.iot.ain.notification.context;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.sap.iot.ain.notification.payload.NotificationPUT;
import com.sap.iot.ain.reuse.cache.CacheManager.ObjectHandler;
import com.sap.iot.ain.reuse.utils.HanaStoredProcedure;
import com.sap.iot.ain.security.AuthenticatedUserDetails;
import com.sap.iot.ain.validation.utils.AINSingletonSpringBeans;

/**
 * 
 * @author I322597
 *
 */
public class NotificationPutObjectHandler implements ObjectHandler<String, NotificationContext> {

	private final NotificationPUT notificationPut;

	public NotificationPutObjectHandler(NotificationPUT notificationPut) {
		this.notificationPut = notificationPut;
	}

	@Override
	public NotificationContext getObject() {
		NotificationPutStoredProcedure proc = new NotificationPutStoredProcedure();
		NotificationContext notificationPutContext = null;

		notificationPutContext = proc.execute(notificationPut);

		return notificationPutContext;
	}

	@Override
	public String getKey() {
		return "Notification_PUT_VALIDATION";
	}

	private class NotificationPutStoredProcedure extends HanaStoredProcedure {

		private static final String IN_CLIENT = "iv_client_id";
		private static final String IN_USER_BP_ID = "iv_user_bp_id";
		private static final String IN_NOTIFICATION_ID = "iv_notification_id";
		private static final String IN_EXTERNAL_NOTIFICATION_ID = "iv_external_notification_id";
		private static final String IN_EQUIPMENT_ID = "iv_equipment";
		private static final String IN_SCOPE = "iv_scope";
		private static final String IN_NOTIFICATION_TYPE = "iv_type";
		private static final String IN_NOTIFICATION_PRIORITY = "iv_priority";
		private static final String IN_NOTIFICATION_STATUS = "iv_status";
		private static final String IN_LOCATION_ID = "iv_location";
		private static final String IN_SYSTEM_ID = "iv_system_id";
		private static final String IN_PROPOSEDFAILUREMODEID = "iv_proposedFailureModeID";
		private static final String IN_CONFIRMEDFAILUREMODEID = "iv_confirmedFailureModeID";
		private static final String IN_SYSTEMPROPOSEDFAILUREMODEID = "iv_systemProposedFailureModeID";
		private static final String IN_EFFECTID = "iv_effectID";
		private static final String IN_CAUSEID = "iv_causeID";
		private static final String IN_INSTRUCTIONID = "iv_instructionID";
		
		private static final String OUT_IS_NOTIFICATIONID_EXIST = "ov_is_notification_id_exist";
		private static final String OUT_IS_NOTIFICATION_EXIST = "ov_is_notification_exist";
		private static final String OUT_IS_EQUIPMENT_VALID = "ov_is_equipment_valid";
		private static final String OUT_HAS_WRITE_ACCESS_ON_EQUIPMENT =
				"ov_has_write_access_on_equipment";
		private static final String OUT_IS_NOTIFICATION_TYPE_VALID = "ov_is_type_valid";
		private static final String OUT_IS_NOTIFICATION_PRIORITY_VALID = "ov_is_priority_valid";
		private static final String OUT_IS_NOTIFICATION_STATUS_VALID = "ov_is_status_valid";
		private static final String OUT_IS_LOCATION_VALID = "ov_is_location_valid";
		private static final String OUT_IS_PROPOSEDFAILUREMODEID_VALID = "ov_is_proposedFailureModeID_valid";
		private static final String OUT_IS_CONFIRMEDFAILUREMODEID_VALID = "ov_is_confirmedFailureModeID_valid";
		private static final String OUT_IS_SYSTEMPROPOSEDFAILUREMODEID_VALID = "ov_is_systemProposedFailureModeID_valid";
		private static final String OUT_IS_EFFECTID_VALID = "ov_is_effectID_valid";
		private static final String OUT_IS_CAUSEID_VALID = "ov_is_causeID_valid";
		private static final String OUT_IS_INSTRUCTIONID_VALID = "ov_is_instructionID_valid";

		public NotificationPutStoredProcedure() {
			super(AINSingletonSpringBeans.getInstance().getJdbcTemplate(),
					"\"sap.ain.proc.notification::NotificationPutValidation\"");
			declareParameter();
			compile();
		}

		public NotificationContext execute(NotificationPUT notificationPut) {

			Map<String, Object> inputs = new HashMap<String, Object>();

			inputs.put(IN_CLIENT,
					AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());
			inputs.put(IN_USER_BP_ID,
					AuthenticatedUserDetails.getInstance().getUserDetails().getUserBpId());
			inputs.put(IN_NOTIFICATION_ID, notificationPut.getNotificationID());
			inputs.put(IN_EXTERNAL_NOTIFICATION_ID, notificationPut.getExternalID());
			inputs.put(IN_EQUIPMENT_ID, notificationPut.getEquipmentID());
			inputs.put(IN_SCOPE,
					AuthenticatedUserDetails.getInstance().getUserDetails().getScope());
			inputs.put(IN_NOTIFICATION_TYPE, notificationPut.getType());
			inputs.put(IN_NOTIFICATION_PRIORITY, notificationPut.getPriority());
			inputs.put(IN_NOTIFICATION_STATUS,
					getCommaSeparatedString(notificationPut.getStatus()));
			inputs.put(IN_LOCATION_ID, notificationPut.getLocationID());
			inputs.put(IN_SYSTEM_ID, notificationPut.getExternalSystemID());
			inputs.put(IN_PROPOSEDFAILUREMODEID, notificationPut.getProposedFailureModeID());
			inputs.put(IN_CONFIRMEDFAILUREMODEID, notificationPut.getConfirmedFailureModeID());
			inputs.put(IN_SYSTEMPROPOSEDFAILUREMODEID, notificationPut.getSystemProposedFailureModeID());
			inputs.put(IN_EFFECTID, notificationPut.getEffectID());
			inputs.put(IN_CAUSEID, notificationPut.getCauseID());
			inputs.put(IN_INSTRUCTIONID, notificationPut.getInstructionID());
			
			NotificationContext context = new NotificationContext();
			try {
				Map<String, Object> output = super.execute(inputs);

				if (output.get(OUT_IS_NOTIFICATIONID_EXIST) != null) {
					context.setNotificationIdExist(
							Boolean.parseBoolean((String) output.get(OUT_IS_NOTIFICATIONID_EXIST)));
				}

				if (output.get(OUT_IS_NOTIFICATION_EXIST) != null) {
					context.setNotificationExist(
							Boolean.parseBoolean((String) output.get(OUT_IS_NOTIFICATION_EXIST)));
				}
				if (output.get(OUT_IS_EQUIPMENT_VALID) != null) {
					context.setEquipmentExist(
							Boolean.parseBoolean((String) output.get(OUT_IS_EQUIPMENT_VALID)));
				}
				if (output.get(OUT_HAS_WRITE_ACCESS_ON_EQUIPMENT) != null) {
					context.setHasWriteAcceesOnEquipment(Boolean
							.parseBoolean((String) output.get(OUT_HAS_WRITE_ACCESS_ON_EQUIPMENT)));
				}
				if (output.get(OUT_IS_NOTIFICATION_TYPE_VALID) != null) {
					context.setTypeValid(Boolean
							.parseBoolean((String) output.get(OUT_IS_NOTIFICATION_TYPE_VALID)));
				}
				if (output.get(OUT_IS_NOTIFICATION_PRIORITY_VALID) != null) {
					context.setPriorityValid(Boolean
							.parseBoolean((String) output.get(OUT_IS_NOTIFICATION_PRIORITY_VALID)));
				}
				if (output.get(OUT_IS_NOTIFICATION_STATUS_VALID) != null) {
					context.setStatusValid(Boolean
							.parseBoolean((String) output.get(OUT_IS_NOTIFICATION_STATUS_VALID)));
				}
				if (output.get(OUT_IS_LOCATION_VALID) != null) {
					context.setLocationExist(
							Boolean.parseBoolean((String) output.get(OUT_IS_LOCATION_VALID)));
				}
				if (output.get(OUT_IS_CONFIRMEDFAILUREMODEID_VALID) != null) {
					context.setConfirmedFailureModeValid(
							Boolean.parseBoolean((String) output.get(OUT_IS_CONFIRMEDFAILUREMODEID_VALID)));
				}
				if (output.get(OUT_IS_PROPOSEDFAILUREMODEID_VALID) != null) {
					context.setProposeFailureModeValid(
							Boolean.parseBoolean((String) output.get(OUT_IS_PROPOSEDFAILUREMODEID_VALID)));
				}
				if (output.get(OUT_IS_SYSTEMPROPOSEDFAILUREMODEID_VALID) != null) {
					context.setSystemProposedFailureModeValid(
							Boolean.parseBoolean((String) output.get(OUT_IS_SYSTEMPROPOSEDFAILUREMODEID_VALID)));
				}
				if (output.get(OUT_IS_EFFECTID_VALID) != null) {
					context.setEffectValid(
							Boolean.parseBoolean((String) output.get(OUT_IS_EFFECTID_VALID)));
				}
				if (output.get(OUT_IS_CAUSEID_VALID) != null) {
					context.setCauseValid(
							Boolean.parseBoolean((String) output.get(OUT_IS_CAUSEID_VALID)));
				}
				if (output.get(OUT_IS_INSTRUCTIONID_VALID) != null) {
					context.setInstructionValid(
							Boolean.parseBoolean((String) output.get(OUT_IS_INSTRUCTIONID_VALID)));
				}

			} catch (Exception e) {
				logger.debug(
						"Exception occured while executing notification put validation procedure..");
			}

			return context;

		}

		private void declareParameter() {
			declareParameter(new SqlParameter(IN_CLIENT, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_USER_BP_ID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_NOTIFICATION_ID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_EXTERNAL_NOTIFICATION_ID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_EQUIPMENT_ID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_SCOPE, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_NOTIFICATION_TYPE, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_NOTIFICATION_PRIORITY, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_NOTIFICATION_STATUS, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_LOCATION_ID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_SYSTEM_ID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_PROPOSEDFAILUREMODEID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_CAUSEID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_CONFIRMEDFAILUREMODEID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_SYSTEMPROPOSEDFAILUREMODEID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_EFFECTID, Types.NVARCHAR));
			declareParameter(new SqlParameter(IN_INSTRUCTIONID, Types.NVARCHAR));
			
			declareParameter(new SqlOutParameter(OUT_IS_NOTIFICATIONID_EXIST, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_NOTIFICATION_EXIST, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_EQUIPMENT_VALID, Types.NVARCHAR));
			declareParameter(
					new SqlOutParameter(OUT_HAS_WRITE_ACCESS_ON_EQUIPMENT, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_NOTIFICATION_TYPE_VALID, Types.NVARCHAR));
			declareParameter(
					new SqlOutParameter(OUT_IS_NOTIFICATION_PRIORITY_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_NOTIFICATION_STATUS_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_LOCATION_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_CONFIRMEDFAILUREMODEID_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_EFFECTID_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_CAUSEID_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_INSTRUCTIONID_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_SYSTEMPROPOSEDFAILUREMODEID_VALID, Types.NVARCHAR));
			declareParameter(new SqlOutParameter(OUT_IS_PROPOSEDFAILUREMODEID_VALID, Types.NVARCHAR));

		}
	}

	private String getCommaSeparatedString(List<String> listOfElements) {
		String commaSeparatedString = "";
		if (listOfElements != null && listOfElements.size() > 0) {
			for (int i = 0; i < listOfElements.size(); i++) {

				String element = listOfElements.get(i);
				commaSeparatedString = commaSeparatedString + element;

				if (i < (listOfElements.size() - 1)) {
					commaSeparatedString = commaSeparatedString + ",";
				}
			}
		}
		return commaSeparatedString;
	}
}
