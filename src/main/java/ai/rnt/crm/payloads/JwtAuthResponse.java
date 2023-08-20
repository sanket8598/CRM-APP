package ai.rnt.crm.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

// @// @formatter:off
/**
 * This class provides Response for Login/Logout And Token Parse Api's
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 21-08-2023
 * 
 */
@Data
@Builder
public class JwtAuthResponse {
	@JsonProperty("SUCCESS")
	private boolean status;
	@JsonProperty("TOKEN")
	private String token;

}
//@formatter:on