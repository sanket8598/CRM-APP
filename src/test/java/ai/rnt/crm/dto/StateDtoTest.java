package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class StateDtoTest {

	StateDto dto = new StateDto();
	StateDto dto1 = new StateDto();

	@Test
	void testSetAndGetStateData() {
		StateDto stateDto = new StateDto();
		CountryDto countryDto = new CountryDto();
		countryDto.setCountryId(1);
		countryDto.setCountry("India");
		stateDto.setStateId(1);
		stateDto.setState("New York");
		stateDto.setCountry(countryDto);
		Integer stateId = stateDto.getStateId();
		String stateName = stateDto.getState();
		CountryDto associatedCountry = stateDto.getCountry();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(stateId);
		assertEquals(1, stateId);
		assertEquals("New York", stateName);
		assertNotNull(associatedCountry);
		assertEquals(1, associatedCountry.getCountryId());
		assertEquals("India", associatedCountry.getCountry());
	}
}
