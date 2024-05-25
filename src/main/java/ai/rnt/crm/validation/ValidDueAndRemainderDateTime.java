package ai.rnt.crm.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ai.rnt.crm.validation.impl.DueAndRemainderDateTimeValidator;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy= { DueAndRemainderDateTimeValidator.class })
public @interface ValidDueAndRemainderDateTime {
	
	 String message() default "Fields values don't match!";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};

	    String timefieldOne();

	    String timefieldSec();
	    
	    String dateFieldOne() default "";
	    
	    String dateFieldSec() default "";
	    
	    String remainderField() default "false";

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
    	ValidDueAndRemainderDateTime[] value();
    }
}