package ai.rnt.crm.validation;

import static java.util.Objects.isNull;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ai.rnt.crm.util.PhoneNumberValidateApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumValid,String>{

	private final PhoneNumberValidateApi phoneNumberValidateApi;

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		log.info("inside the is phone num valid annotation...{}",phoneNumber);
		if(isNull(phoneNumber))
			return false;
		Map<String, Object> mobMap = phoneNumberValidateApi.checkPhoneNumberInfo(phoneNumber);
		return (("OK".equalsIgnoreCase((String)mobMap.get("Success")) && (boolean)mobMap.get("isValidNumber")) || "Error".equalsIgnoreCase((String)mobMap.get("Success")));
	}

}
