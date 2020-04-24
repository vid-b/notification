package com.sap.iot.ain.notification.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import com.sap.iot.ain.acl.core.Authorization;
import com.sap.iot.ain.auth.core.AINAccessControlList;
import com.sap.iot.ain.core.AINObjectTypes;
import com.sap.iot.ain.spring.utils.ApplicationContextUtils;
import com.sap.iot.ain.validation.utils.AINSingletonSpringBeans;

@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class NotificationReadValidator
		implements ConstraintValidator<NotificationReadValidations, Object[]> {

	@Override
	public void initialize(NotificationReadValidations arg0) {}

	@Override
	public boolean isValid(Object[] values, ConstraintValidatorContext context) {
		context.disableDefaultConstraintViolation();

		String notificationId = (String) values[0];

		AINAccessControlList acl = ApplicationContextUtils.getApplicationContext().getBean(AINAccessControlList.class);;
		Authorization authorization = acl.hasPermission(notificationId);

		if (!authorization.exists()
				|| !authorization.getObjectType().equals(AINObjectTypes.NTF.toString())) {
			context.buildConstraintViolationWithTemplate("invalid.notification.id")
					.addConstraintViolation();
			return false;
		}
		return true;
	}

}
