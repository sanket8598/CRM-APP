package ai.rnt.crm.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ai.rnt.crm.validation.impl.CallDirectionValidator;


@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CallDirectionValidator.class })
public @interface ValidCallDirection {

	String message() default "Call direction is not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
