package ai.rnt.crm.payloads;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthRequest {
	
	@NotNull(message ="UserId should not be null !!")
	@NotEmpty(message ="UserId should not be empty !!")
	private String userId;
	
	@NotNull(message ="Password should not be null !!")
	@NotEmpty(message ="Password should not be empty !!")
	private String password;

	private boolean fromCorp;


}
