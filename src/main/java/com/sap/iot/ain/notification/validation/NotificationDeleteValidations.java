package com.sap.iot.ain.notification.validation;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import com.sap.iot.ain.reuse.utils.HanaStoredProcedure;
import com.sap.iot.ain.security.AuthenticatedUserDetails;

public class NotificationDeleteValidations extends HanaStoredProcedure {

	private static final String IN_IDS = "iv_ids";
	private static final String IN_CLIENT = "iv_client";

	public static final String ov_invalid_ids = "ov_invalid_ids";

	public NotificationDeleteValidations(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate, "\"sap.ain.proc.notification::NotificationDeleteValidations\"");
		declareParameter();
		compile();
	}

	public NotificationsDeleteValidation execute(String id) {

		Map<String, Object> inputs = new HashMap<String, Object>();

		inputs.put(IN_IDS, id);
		inputs.put(IN_CLIENT,
				AuthenticatedUserDetails.getInstance().getUserDetails().getUserClientId());

		Map<String, Object> output = null;
		try {
			output = super.execute(inputs);
		} catch (DataIntegrityViolationException ex) {
			logger.error(
					"AIN User Details : Procedure call failed due to DataIntegrityViolationException.."
							+ ex.getStackTrace());
		}

		NotificationsDeleteValidation outputproc = new NotificationsDeleteValidation();
		if (output != null) {
			if (output.get(ov_invalid_ids) == null) {
				outputproc.setInvalidIds("");
			} else {
				outputproc.setInvalidIds((String) output.get(ov_invalid_ids));
			}
		}
		return outputproc;

	}

	private void declareParameter() {

		declareParameter(new SqlParameter(IN_IDS, Types.NVARCHAR));
		declareParameter(new SqlParameter(IN_CLIENT, Types.NVARCHAR));

		declareParameter(new SqlOutParameter(ov_invalid_ids, Types.NVARCHAR));
	}

	public class NotificationsDeleteValidation {

		String invalidIds;
		boolean isEquipmentValid;
		boolean hasWriteAccess;

		public String getInvalidIds() {
			return invalidIds;
		}

		public void setInvalidIds(String invalidIds) {
			this.invalidIds = invalidIds;
		}

		public boolean isHasWriteAccess() {
			return hasWriteAccess;
		}

		public void setHasWriteAccess(boolean hasWriteAccess) {
			this.hasWriteAccess = hasWriteAccess;
		}

		public boolean isEquipmentValid() {
			return isEquipmentValid;
		}

		public void setEquipmentValid(boolean isEquipmentValid) {
			this.isEquipmentValid = isEquipmentValid;
		}

	}

}
