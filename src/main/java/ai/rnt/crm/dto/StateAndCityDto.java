package ai.rnt.crm.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateAndCityDto {

	private Integer stateId;
	private String state;
	private List<CityDto> cities;
}
