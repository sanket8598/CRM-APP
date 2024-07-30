package ai.rnt.crm.dto;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class StateAndCityDtoTest {

	@Test
	void testStateIdGetterSetter() {
		StateAndCityDto stateAndCityDto = new StateAndCityDto();
		Integer stateId = 123;
		stateAndCityDto.setStateId(stateId);
		assertEquals(stateId, stateAndCityDto.getStateId());
	}

	@Test
	void testStateGetterSetter() {
		StateAndCityDto stateAndCityDto = new StateAndCityDto();
		String state = "California";
		stateAndCityDto.setState(state);
		assertEquals(state, stateAndCityDto.getState());
	}

	@Test
	void testCitiesGetterSetter() {
		StateAndCityDto stateAndCityDto = new StateAndCityDto();
		CityDto city1 = new CityDto();
		city1.setCity("Los Angeles");
		CityDto city2 = new CityDto();
		city2.setCity("San Francisco");
		List<CityDto> cities = asList(city1, city2);
		stateAndCityDto.setCities(cities);
		assertEquals(cities, stateAndCityDto.getCities());
	}
}
