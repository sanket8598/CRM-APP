package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CityDtoTest {

	@Test
	void testCityDto() {
		CityDto cityDto = new CityDto();
		CityDto cityDto1 = new CityDto();
		cityDto.setCityId(1);
		cityDto.setCity("Test City");
		cityDto.setStateId(1);
		cityDto1.setCityId(1);
		cityDto1.setCity("Test City");
		cityDto1.setStateId(1);
		cityDto.equals(cityDto1);
		cityDto.hashCode();
		cityDto1.hashCode();
		cityDto.toString();
		assertEquals(1, cityDto.getCityId());
		assertEquals("Test City", cityDto.getCity());
		assertEquals(1, cityDto.getStateId());
	}
}
