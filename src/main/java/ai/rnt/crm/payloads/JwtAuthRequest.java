package ai.rnt.crm.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String userId;
	
	private String password;



}
