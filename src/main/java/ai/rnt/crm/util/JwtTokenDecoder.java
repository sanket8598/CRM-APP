package ai.rnt.crm.util;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtTokenDecoder {
	public String testDecodeJWT(String token) {
	return new String(new Base64(true).decode(token.split("\\.")[1]));
	}
	public static Boolean isTokenValid(String token) {
		try {
		String string = new String(new Base64(true).decode(token.split("\\.")[1]));
		System.out.println(string);
		JsonNode json;
			json = new ObjectMapper().readTree(new JwtTokenDecoder().testDecodeJWT(token));
			System.out.println(json.get("exp"));
			return true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
	public static void main(String[] args) {
		isTokenValid("eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQ1JNIFVTRVIiLCJzdWIiOiJzdzEzNzUiLCJmdWxsTmFtZSI6InNhbmtldCB3YWthbmthciIsImV4cCI6MTY5Mjg0ODIwNCwiaWF0IjoxNjkyODQ3ODQ0fQ.GWT_d2cbNPeSWZ0iM4OChivhTW89JhurHmHInObnZBU");
	}
}
