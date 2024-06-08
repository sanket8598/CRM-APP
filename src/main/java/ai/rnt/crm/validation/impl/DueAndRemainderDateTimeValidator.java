package ai.rnt.crm.validation.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertLocalDateDateWithTimeToLocalDate;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;

import ai.rnt.crm.validation.ValidDueAndRemainderDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DueAndRemainderDateTimeValidator implements ConstraintValidator<ValidDueAndRemainderDateTime, Object> {

	public String timefieldOne;
	public String timefieldSec;
	public String dateFieldOne;
	public String dateFieldSec;
	public String remainderField;

	@Override
	public void initialize(ValidDueAndRemainderDateTime constraintAnnotation) {
		this.timefieldOne = constraintAnnotation.timefieldOne();
		this.timefieldSec = constraintAnnotation.timefieldSec();
		this.dateFieldOne = constraintAnnotation.dateFieldOne();
		this.dateFieldSec = constraintAnnotation.dateFieldSec();
		this.remainderField = constraintAnnotation.remainderField();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (isNull(value))
			return true;
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl(value);
		String dueTime = (String) beanWrapperImpl.getPropertyValue(timefieldOne);
		LocalDate dueDate = (LocalDate) beanWrapperImpl.getPropertyValue(dateFieldOne);
		boolean remainderOn = (boolean) beanWrapperImpl.getPropertyValue(remainderField);
		context.disableDefaultConstraintViolation();
		if (nonNull(dueTime) && nonNull(dueDate)) {
			LocalDateTime currentDateTime = now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE))
					.toLocalDateTime();
			LocalDateTime inputDueDate = convertLocalDateDateWithTimeToLocalDate(dueDate, dueTime);
			log.info("current time in isValid method...{}", currentDateTime);
			log.info("inputDueDate in isValid method...{}", inputDueDate);

			if (inputDueDate.isBefore(currentDateTime)) {
				customMessageForValidation(context, dateFieldOne,
						"Due Date & Time must not be smaller than current date & time!!");
				log.info("check condition of isBefore isValid method...{}", inputDueDate.isBefore(currentDateTime));
				return false;
			}
			if (remainderOn) {
				String remainderTime = (String) beanWrapperImpl.getPropertyValue(timefieldSec);
				LocalDate remainderDate = (LocalDate) beanWrapperImpl.getPropertyValue(dateFieldSec);
				LocalDateTime inputRemainderDate = convertLocalDateDateWithTimeToLocalDate(remainderDate,
						remainderTime);
				if (inputRemainderDate.isBefore(currentDateTime)) {
					customMessageForValidation(context, dateFieldSec,
							"Remainder Date & Time must not be smaller than current Date & Time!!");
					return false;
				}
				if (inputRemainderDate.isAfter(inputDueDate)) {
					customMessageForValidation(context, dateFieldSec,
							"Remainder Date & Time must not be smaller than Due Date & Time!!");
					return false;
				}
			}

		}
		return true;
	}

	private void customMessageForValidation(ConstraintValidatorContext constraintContext, String fieldName,
			String message) {
		constraintContext.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName)
				.addConstraintViolation();
	}
}
