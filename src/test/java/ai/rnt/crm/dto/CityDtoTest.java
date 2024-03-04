package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CityDtoTest {

	@Test
	void testEqualsAndHashCode() {
		CityDto cityDto1 = new CityDto();
		cityDto1.setCityId(1);
		cityDto1.setCity("New York");
		cityDto1.setStateId(1);

		CityDto cityDto2 = new CityDto();
		cityDto2.setCityId(1);
		cityDto2.setCity("New York");
		cityDto2.setStateId(1);
		assertEquals(cityDto1, cityDto2);
		assertEquals(cityDto1.hashCode(), cityDto2.hashCode());
		cityDto2.setCity("Los Angeles");
		assertNotEquals(cityDto1, cityDto2);
		assertNotEquals(cityDto1.hashCode(), cityDto2.hashCode());
	}

	@Test
	void testToString() {
		CityDto cityDto = new CityDto();
		cityDto.setCityId(1);
		cityDto.setCity("New York");
		cityDto.setStateId(1);
		assertEquals("CityDto(cityId=1, city=New York, stateId=1)", cityDto.toString());
	}

	@Test
	void testCanEquals() {
		CityDto cityDto = new CityDto();
		assertTrue(cityDto.canEqual(cityDto));
	}
}
