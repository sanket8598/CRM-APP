package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class StateDtoTest {

	@Test
	void testSetAndGetStateData() {
		StateDto stateDto = new StateDto();
		StateDto dto = new StateDto();
		StateDto dto1 = new StateDto();
		CountryDto countryDto = new CountryDto();
		countryDto.setCountryId(1);
		countryDto.setCountry("India");
		stateDto.setStateId(1);
		stateDto.setState("New York");
		stateDto.setCountry(countryDto);
		dto1.setStateId(1);
		dto1.setState("New York");
		dto1.setCountry(countryDto);
		Integer stateId = stateDto.getStateId();
		String stateName = stateDto.getState();
		CountryDto associatedCountry = stateDto.getCountry();
		stateDto.equals(dto1);
		dto.hashCode();
		dto1.hashCode();
		dto.toString();
		assertNotNull(stateId);
		assertEquals(1, stateId);
		assertEquals("New York", stateName);
		assertNotNull(associatedCountry);
		assertEquals(1, associatedCountry.getCountryId());
		assertEquals("India", associatedCountry.getCountry());
	}
}
