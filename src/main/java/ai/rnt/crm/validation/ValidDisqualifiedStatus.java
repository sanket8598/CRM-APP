package ai.rnt.crm.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
		ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { DisqualifiedAsStatusValidator.class })
public @interface ValidDisqualifiedStatus {
	
	String message() default "Disqualified status is not valid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
