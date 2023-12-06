package ai.rnt.crm.util;

import static java.util.Objects.isNull;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PhoneNumberValidateApi {

	private static final String API_KEY_NAME = "X-RapidAPI-Key";
	private static final String API_HOST_NAME = "X-RapidAPI-Host";
	private static final String API_KEY_VALUE = "e6d2e92131msh337aa15cbae4edep1a7dfdjsn615e63686049";
	private static final String API_HOST_VALUE = "phonenumbervalidatefree.p.rapidapi.com";
	private static final String KEY="Success";

	public Map<String, Object> checkPhoneNumberInfo(String number) {
		log.info("Calling Phone Info Number API...");
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put(KEY, "Error");
		try {
			String url = "https://phonenumbervalidatefree.p.rapidapi.com/ts_PhoneNumberValidateTest.jsp?number="
					+ number + "&country=" + getRegionCode(number);

			HttpResponse<String> response = Unirest.get(url).header(API_KEY_NAME, API_KEY_VALUE)
					.header(API_HOST_NAME, API_HOST_VALUE).asString();

			if (response.getStatus() == 200) {
				responseMap.put(KEY, "OK");
				responseMap.put("Data", new JSONObject(response.getBody()));
				responseMap.put("isValidNumber", new JSONObject(response.getBody()).get("isValidNumber"));
			} 
		} catch (Exception e) {
			log.error("error occurred in the Phone Number Validation Api...{}", e.getMessage());
		}
		return responseMap;
	}

	private String getRegionCode(String phoneNumber) throws NumberParseException {
		if (isNull(phoneNumber) || phoneNumber.isEmpty())
			return null;
		if (!phoneNumber.startsWith("+"))
			return "IN";

		PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
		PhoneNumber phoneNumberObject = phoneNumberUtil.parse(phoneNumber, phoneNumber.substring(1, 3));
		return phoneNumberUtil.isValidNumber(phoneNumberObject)
				? phoneNumberUtil.getRegionCodeForNumber(phoneNumberObject)
				: null;
	}
	
}
