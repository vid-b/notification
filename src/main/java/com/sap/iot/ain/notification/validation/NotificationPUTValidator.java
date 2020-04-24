package com.sap.iot.ain.notification.validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.sap.iot.ain.acl.core.Authorization;
import com.sap.iot.ain.auth.core.AINAccessControlList;
import com.sap.iot.ain.core.AINObjectTypes;
import com.sap.iot.ain.notification.context.NotificationContext;
import com.sap.iot.ain.notification.context.NotificationPutObjectHandler;
import com.sap.iot.ain.notification.dao.NotificationDao;
import com.sap.iot.ain.notification.payload.NotificationCauseCodes;
import com.sap.iot.ain.notification.payload.NotificationFailureModes;
import com.sap.iot.ain.notification.payload.NotificationItems;
import com.sap.iot.ain.notification.payload.NotificationPUT;
import com.sap.iot.ain.reuse.Strings;
import com.sap.iot.ain.reuse.bp.exceptions.BusinessPartnerNotFoundException;
import com.sap.iot.ain.reuse.cache.CacheManager;
import com.sap.iot.ain.reuse.payload.ID;
import com.sap.iot.ain.reuse.utils.ObjectUtils;
import com.sap.iot.ain.service.BPServices;
import com.sap.iot.ain.spring.utils.ApplicationContextUtils;
import com.sap.iot.ain.validation.utils.AINSingletonSpringBeans;
import com.sap.iot.ain.validation.utils.MsgHelper;

/**
 * @author I322597
 *
 */
public class NotificationPUTValidator
		implements ConstraintValidator<NotificationPUTValiadtions, NotificationPUT> {

	private static final Logger logger = LoggerFactory.getLogger(NotificationPUTValidator.class);

	@Autowired
	private NotificationDao notificationDao;

	@Override
	public void initialize(NotificationPUTValiadtions constraintAnnotation) {}

	@Override
	public boolean isValid(NotificationPUT notification, ConstraintValidatorContext context) {

		context.disableDefaultConstraintViolation();
		boolean isValid = true;

		this.trimNotificationFields(notification);

		// Mandatory fields validation
		
		if(Strings.isNullOrEmpty(notification.getExternalSystemID())) {
			notification.setExternalSystemID(notificationDao.getSystemId());
		}

		if (notification.getType() == null || notification.getType().trim().isEmpty()) {
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("invalid.notification.type")).addConstraintViolation();
			return false;

		}

		if (notification.getStatus() == null || notification.getStatus().size() < 1) {
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("invalid.notification.status")).addConstraintViolation();
			return false;

		}

		if (!Strings.isNullOrEmpty(notification.getOperator())) {
			BPServices bpServices =
					ApplicationContextUtils.getApplicationContext().getBean(BPServices.class);
			try {
				bpServices.getBPDetails(notification.getOperator());
			} catch (BusinessPartnerNotFoundException e) {
				context.buildConstraintViolationWithTemplate(
						MsgHelper.buildMsg("invalid.notification.operator"))
						.addConstraintViolation();
				return false;
			}
		}
		
		if (!Strings.isNullOrEmpty(notification.getExternalID())) {
			if(Strings.isNullOrEmpty(notification.getExternalSystemID())) {
				context.buildConstraintViolationWithTemplate(
						MsgHelper.buildMsg("notification.invalid.externalsystemid.invalid"))
						.addConstraintViolation();
				return false;
			}
			if(!notificationDao.isSystemIdValid(notification.getExternalSystemID())) {
				context.buildConstraintViolationWithTemplate(
						MsgHelper.buildMsg("notification.invalid.externalsystemid.invalid"))
						.addConstraintViolation();
				return false;
			}
		}

		/* Validating fields here to avoid duplicate checks */
		Set<String> payloadCauseIds = new HashSet<String>();
		Set<ID> failureModeIds = new HashSet<ID>();
		List<NotificationItems> items = notification.getItems();

		if (!ObjectUtils.isListEmpty(items)) {
			for (NotificationItems item : items) {
				Integer itemNumber = item.getItemNumber();
				if (itemNumber == null || itemNumber.toString().trim().isEmpty()
						|| itemNumber.toString().length() > 10) {
					context.buildConstraintViolationWithTemplate(
							MsgHelper.buildMsg("invalid.notification.itemNumber"))
							.addConstraintViolation();
					return false;
				}
				boolean isValidFailureMode = validateItemsFailureMode(
						item.getNotificationFailureModes(), failureModeIds, context);
				if (isValidFailureMode == false) {
					return false;
				}

				boolean isValidCauseCodes = validateItemsCauseCodes(
						item.getNotificationCauseCodes(), payloadCauseIds, context);
				if (isValidCauseCodes == false) {
					return false;
				}

			}
			List<NotificationItems> duplicateElements = ObjectUtils.getDuplicateElements(items);
			if (!duplicateElements.isEmpty()) {
				context.buildConstraintViolationWithTemplate("notification.duplicate.itemNumber")
						.addConstraintViolation();
				return false;
			}
		}
		/* Validating fields here to avoid duplicate checks */
		// DB validation

		CacheManager cacheManager = AINSingletonSpringBeans.getInstance().getCacheManager();
		NotificationContext notificationContext = null;
		notificationContext =
				cacheManager.getObject(new NotificationPutObjectHandler(notification));

		if (!notificationContext.isNotificationIdExist())

		{
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("invalid.notification.id")).addConstraintViolation();
			return false;
		}

		if (notificationContext.isNotificationExist())

		{
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("notification.exist.insystem")).addConstraintViolation();
			return false;
		}

		if (!notificationContext.isEquipmentExist()) {
			context.buildConstraintViolationWithTemplate(MsgHelper.buildMsg("equipment.id.invalid"))
					.addConstraintViolation();
			return false;
		}
		if (!notificationContext.isHasWriteAcceesOnEquipment()) {
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("equipment.no.write.access")).addConstraintViolation();
			return false;
		}


		if (!notificationContext.isLocationExist()) {
			context.buildConstraintViolationWithTemplate(MsgHelper.buildMsg("funcloc.not.found"))
					.addConstraintViolation();
			return false;
		}

		if (!notificationContext.isTypeValid())

		{
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("invalid.notification.type")).addConstraintViolation();
			return false;
		}

		if (!notificationContext.isPriorityValid())

		{
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("invalid.notification.priority")).addConstraintViolation();
			return false;
		}

		if (!notificationContext.isStatusValid())

		{
			context.buildConstraintViolationWithTemplate(
					MsgHelper.buildMsg("invalid.notification.status")).addConstraintViolation();
			return false;
		}

		// DB Validations for Items failure modes
		if (failureModeIds.size() > 0) {
			AINAccessControlList acl = ApplicationContextUtils.getApplicationContext().getBean(AINAccessControlList.class);
			List<Authorization> authorizationList =
					acl.hasPermission(new ArrayList<>(failureModeIds));
			List<Object> invalidAuthorizations = authorizationList.stream()
					.filter(auth -> (!auth.exists()
							|| !auth.getObjectType().equals(AINObjectTypes.FM.toString())))
					.collect(Collectors.toList());
			if (invalidAuthorizations.size() > 0) {
				context.buildConstraintViolationWithTemplate(
						MsgHelper.buildMsg("invalid.notification.failureMode"))
						.addConstraintViolation();
				return false;
			}
		}


		// DB Validations for Cause codes
		if (payloadCauseIds.size() > 0) {
			Set<String> dbCauseIds = notificationDao.getCauseIds(payloadCauseIds);
			payloadCauseIds.removeAll(dbCauseIds);
			if (!payloadCauseIds.isEmpty()) {
				context.buildConstraintViolationWithTemplate(
						MsgHelper.buildMsg("cause.mode.some.id.invalid",
								StringUtils.collectionToCommaDelimitedString(payloadCauseIds)))
						.addConstraintViolation();
				return false;
			}
		}

		return isValid;
	}

	private boolean validateItemsCauseCodes(List<NotificationCauseCodes> causeCodes,
			Set<String> payloadCauseIds, ConstraintValidatorContext context) {
		boolean isValid = true;
		if (!ObjectUtils.isListEmpty(causeCodes)) {
			for (NotificationCauseCodes causeCode : causeCodes) {
				String causeID = causeCode.getCauseID();
				if (causeID == null || causeID.toString().trim().isEmpty()
						|| causeID.toString().length() != 32) {
					context.buildConstraintViolationWithTemplate(
							MsgHelper.buildMsg("invalid.notification.causeID"))
							.addConstraintViolation();
					return false;
				}
				payloadCauseIds.add(causeID);
			}

			List<NotificationCauseCodes> duplicateCauseCodes =
					ObjectUtils.getDuplicateElements(causeCodes);

			if (!duplicateCauseCodes.isEmpty()) {
				context.buildConstraintViolationWithTemplate("notification.duplicate.causeCode")
						.addConstraintViolation();
				return false;
			}
		}
		return isValid;
	}

	private boolean validateItemsFailureMode(NotificationFailureModes notificationFailureModes,
			Set<ID> failureModeIds, ConstraintValidatorContext context) {
		boolean isValid = true;
		if (!ObjectUtils.isNull(notificationFailureModes)) {
			String failureModeId = notificationFailureModes.getFailureModeID();
			if (failureModeId == null || failureModeId.toString().trim().isEmpty()
					|| failureModeId.toString().length() != 32) {
				context.buildConstraintViolationWithTemplate(
						MsgHelper.buildMsg("invalid.notification.failureModeID"))
						.addConstraintViolation();
				return false;
			}
			ID id = new ID();
			id.setId(failureModeId);
			failureModeIds.add(id);
		}
		return isValid;
	}

	private void trimNotificationFields(NotificationPUT notification) {
		try {
			if (notification.getEquipmentID() != null
					&& notification.getEquipmentID().length() > 0) {
				notification.setEquipmentID(notification.getEquipmentID().trim());
			}

			if (notification.getExternalID() != null && notification.getExternalID().length() > 0) {
				notification.setExternalID(notification.getExternalID().trim());
			}

			if (notification.getLocationID() != null && notification.getLocationID().length() > 0) {
				notification.setLocationID(notification.getLocationID().trim());
			}

			if (notification.getDescription() != null
					&& notification.getDescription().getShortDescription() != null
					&& notification.getDescription().getShortDescription().length() > 0) {
				notification.getDescription().setShortDescription(
						notification.getDescription().getShortDescription().trim());
			}
			if (notification.getDescription() != null
					&& notification.getDescription().getLongDescription() != null
					&& notification.getDescription().getLongDescription().length() > 0) {
				notification.getDescription().setLongDescription(
						notification.getDescription().getLongDescription().trim());
			}
			if (notification.getType() != null && notification.getType().length() > 0) {
				notification.setType(notification.getType().trim());
			}
			if (notification.getPriority() != null && notification.getPriority().length() > 0) {
				notification.setPriority(notification.getPriority().trim());
			}

		} catch (Exception e) {

			logger.debug("Exception occured while trimming notification post payload fields...");

		}
	}
}
