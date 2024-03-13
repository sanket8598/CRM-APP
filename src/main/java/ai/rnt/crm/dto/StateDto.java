package ai.rnt.crm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateDto {

	private Integer stateId;
	private String state;

	private CountryDto country;

}
