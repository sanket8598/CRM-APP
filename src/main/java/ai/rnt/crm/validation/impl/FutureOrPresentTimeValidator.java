package ai.rnt.crm.validation.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTimeToLocalDate;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import ai.rnt.crm.validation.FutureOrPresentTime;

public class FutureOrPresentTimeValidator implements ConstraintValidator<FutureOrPresentTime, Object> {

	private String timefieldOne;
	private String timefieldSec;
	private String dateFieldOne;
	private String dateFieldSec;

	@Override
	public void initialize(FutureOrPresentTime constraintAnnotation) {
		this.timefieldOne = constraintAnnotation.timefieldOne();
		this.timefieldSec = constraintAnnotation.timefieldSec();
		this.dateFieldOne = constraintAnnotation.dateFieldOne();
		this.dateFieldSec = constraintAnnotation.dateFieldSec();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (isNull(value))
			return true;
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(value);
		String startTime = (String) beanWrapperImpl.getPropertyValue(timefieldOne);
		String endTime = (String) beanWrapperImpl.getPropertyValue(timefieldSec);
		Date startDate = (Date) beanWrapperImpl.getPropertyValue(dateFieldOne);
		Date endDate = (Date) beanWrapperImpl.getPropertyValue(dateFieldSec);
		context.disableDefaultConstraintViolation();
		if (nonNull(startTime) && nonNull(startDate) && nonNull(endTime) && nonNull(endDate)) {
			LocalDateTime currentdateTime = now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE))
					.toLocalDateTime();
			LocalDateTime inputStartDate = convertDateDateWithTimeToLocalDate(startDate, startTime);
			LocalDateTime inputEndDate = convertDateDateWithTimeToLocalDate(endDate, endTime);
			if(inputStartDate.isBefore(currentdateTime)) {
			   customMessageForValidation(context,dateFieldOne,"Start Date & Time must not be smaller than today's date!!");
			   return false;
			}
			if(inputEndDate.isBefore(currentdateTime)) {
				customMessageForValidation(context,dateFieldSec, "End Date & Time must not be smaller than today's Date & Time!!");
				return false;
			}
			if(inputEndDate.isBefore(inputStartDate)) {
				customMessageForValidation(context,dateFieldSec, "End Date and Time must not be smaller than Start Date and Time!!");
				return false;
			}
		}
		return true;
	}
	
	 private void customMessageForValidation(ConstraintValidatorContext constraintContext,String fieldName, String message) {
			constraintContext.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName)
					.addConstraintViolation();
	    }

}
