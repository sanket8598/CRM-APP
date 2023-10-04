package ai.rnt.crm.util;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import ai.rnt.crm.exception.CRMException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PhoneNumberValidateApi {
	
	public Map<String,Object> checkPhoneNumberInfo(String number) {
		log.info("Calling Phone Info Number API...");
		try {
			Map<String,Object> responseMap=new HashMap<>();
			String url="https://phonenumbervalidatefree.p.rapidapi.com/ts_PhoneNumberValidateTest.jsp?number="+number+"&country=IN";
			 HttpResponse<String> response = Unirest.get(url)
					.header("X-RapidAPI-Key", "e6d2e92131msh337aa15cbae4edep1a7dfdjsn615e63686049")
					.header("X-RapidAPI-Host", "phonenumbervalidatefree.p.rapidapi.com").asString();
			 if(response.getStatus()==200) {
				 responseMap.put("Success", "OK");
				 responseMap.put("isValidNumber",new JSONObject(response.getBody()).get("isValidNumber"));
			 }else {
				 responseMap.put("Success", "Error");
			 }
			return responseMap;
		}catch (Exception e) {
			log.error("error occurred in the Phone Number Validation Api...{}",e.getMessage());
			throw new  CRMException(e);
		}
		
	}

}
