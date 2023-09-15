package ai.rnt.crm.validation;

import static java.util.Objects.nonNull;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.util.PhoneNumberValidateApi;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumValid,String>{


	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		log.info("inside the is phone num valid annotation...{}",phoneNumber);
		if(nonNull(phoneNumber))
			return false;
		Map<String, Object> mobMap = new PhoneNumberValidateApi().checkPhoneNumberInfo(phoneNumber);
		if(("OK".equalsIgnoreCase((String)mobMap.get("Success")) && (boolean)mobMap.get("isValidNumber")))
				return true;
		else if("Error".equalsIgnoreCase((String)mobMap.get("Success"))) 
		     return true;
		return false;
	}

}
