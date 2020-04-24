package com.sap.iot.ain.notification.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.sap.iot.ain.group.dao.ValidationHelperDao;
import com.sap.iot.ain.group.payload.UpdateGroupWithBusinessObject;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class NotificationGroupBusinessObjectValidator
		implements ConstraintValidator<NotificationGroupBusinessObjectValidations, Object[]> {

	public void initialize(NotificationGroupBusinessObjectValidations constraintAnnotation) {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public boolean isValid(Object[] values, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();
		ValidationHelperDao validationHelperDao = new ValidationHelperDao(context);
		List<String> businessObjectsList = new ArrayList<String>();
		String groupId = (String) values[0];

		List<UpdateGroupWithBusinessObject> businessObjects =
				(List<UpdateGroupWithBusinessObject>) values[1];

		if (!validationHelperDao.isGroupIdValid(groupId)) {
			return false;
		}

		if (!validationHelperDao.checkIfBusinessObjectTypeIsPartOfTheSpecificGroup(groupId,
				businessObjects)) {
			return false;
		}

		return true;
	}

}
