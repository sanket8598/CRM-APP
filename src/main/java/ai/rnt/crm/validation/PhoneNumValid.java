package ai.rnt.crm.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ai.rnt.crm.validation.impl.PhoneNumberValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface PhoneNumValid {
	
	/**
	 * @return the error message template
	 */
	String message() default "Phone Number Is Not Valid or Not From India";

	/**
	 * @return the groups the constraint belongs to
	 */
	Class<?>[] groups() default { };

	/**
	 * @return the payload associated to the constraint
	 */
	Class<? extends Payload>[] payload() default { };

}
