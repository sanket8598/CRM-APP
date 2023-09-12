package ai.rnt.crm.dto;

import lombok.Data;

@Data
public class StateDto {

	private Integer stateId;
	private String state;

	private CountryDto country;

}
