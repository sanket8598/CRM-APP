package ai.rnt.crm.validation.impl;

import static ai.rnt.crm.enums.DisqualifiedAs.values;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.validation.ValidDisqualifiedStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DisqualifiedAsStatusValidator implements ConstraintValidator<ValidDisqualifiedStatus, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		log.info("inside the DisqualifiedAsStatusValidator class isValid method...{}",value);
		return isNull(value) || stream(values()).anyMatch(reason -> reason.getDisqualifiedAs().equalsIgnoreCase(value));
	}
}
