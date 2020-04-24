package com.sap.iot.ain.notification.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.sap.iot.ain.notification.validation.NotificationPOSTValidator;

/**
 * 
 * @author I322597
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotificationPOSTValidator.class)
@Documented
public @interface NotificationPOSTValiadtions {

  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String field() default "Field";
  


}
