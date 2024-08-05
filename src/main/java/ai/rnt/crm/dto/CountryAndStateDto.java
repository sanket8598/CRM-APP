package ai.rnt.crm.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryAndStateDto {

	private Integer countryId;

	private String country;
	
	private String countryFlag;

	private List<StateAndCityDto> states;
}
