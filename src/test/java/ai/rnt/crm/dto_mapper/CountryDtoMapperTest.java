package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.CityDto;
import ai.rnt.crm.dto.CountryAndStateDto;
import ai.rnt.crm.dto.StateAndCityDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;

class CountryDtoMapperTest {

	 @Test
	    void testNullCountryMaster() {
	        Optional<CountryAndStateDto> result = CountryDtoMapper.TO_COUNTRY_DTO_DATA.apply(null);
	        assertFalse(result.isPresent());
	    }

	    @Test
	    void testCountryMasterWithNoStates() {
	        CountryMaster countryMaster = mock(CountryMaster.class);
	        when(countryMaster.getCountryId()).thenReturn(1);
	        when(countryMaster.getCountry()).thenReturn("Test Country");
	        when(countryMaster.getStates()).thenReturn(Collections.emptyList());

	        Optional<CountryAndStateDto> result = CountryDtoMapper.TO_COUNTRY_DTO_DATA.apply(countryMaster);

	        assertTrue(result.isPresent());
	        CountryAndStateDto countryDto = result.get();
	        assertEquals(1, countryDto.getCountryId());
	        assertEquals("Test Country", countryDto.getCountry());
	        assertTrue(countryDto.getStates().isEmpty());
	    }

	    @Test
	    void testCountryMasterWithStatesButNoCities() {
	        CountryMaster countryMaster = mock(CountryMaster.class);
	        when(countryMaster.getCountryId()).thenReturn(1);
	        when(countryMaster.getCountry()).thenReturn("Test Country");

	        StateMaster stateMaster = mock(StateMaster.class);
	        when(stateMaster.getStateId()).thenReturn(10);
	        when(stateMaster.getState()).thenReturn("Test State");
	        when(stateMaster.getCities()).thenReturn(Collections.emptyList());

	        List<StateMaster> states = new ArrayList<>();
	        states.add(stateMaster);
	        when(countryMaster.getStates()).thenReturn(states);

	        Optional<CountryAndStateDto> result = CountryDtoMapper.TO_COUNTRY_DTO_DATA.apply(countryMaster);

	        assertTrue(result.isPresent());
	        CountryAndStateDto countryDto = result.get();
	        assertEquals(1, countryDto.getCountryId());
	        assertEquals("Test Country", countryDto.getCountry());
	        assertEquals(1, countryDto.getStates().size());

	        StateAndCityDto stateDto = countryDto.getStates().get(0);
	        assertEquals(10, stateDto.getStateId());
	        assertEquals("Test State", stateDto.getState());
	        assertTrue(stateDto.getCities().isEmpty());
	    }

	    @Test
	    void testCountryMasterWithStatesAndCities() {
	        CountryMaster countryMaster = mock(CountryMaster.class);
	        when(countryMaster.getCountryId()).thenReturn(1);
	        when(countryMaster.getCountry()).thenReturn("Test Country");

	        StateMaster stateMaster = mock(StateMaster.class);
	        when(stateMaster.getStateId()).thenReturn(10);
	        when(stateMaster.getState()).thenReturn("Test State");

	        CityMaster cityMaster = mock(CityMaster.class);
	        when(cityMaster.getCityId()).thenReturn(100);
	        when(cityMaster.getCity()).thenReturn("Test City");

	        List<CityMaster> cities = new ArrayList<>();
	        cities.add(cityMaster);
	        when(stateMaster.getCities()).thenReturn(cities);

	        List<StateMaster> states = new ArrayList<>();
	        states.add(stateMaster);
	        when(countryMaster.getStates()).thenReturn(states);

	        Optional<CountryAndStateDto> result = CountryDtoMapper.TO_COUNTRY_DTO_DATA.apply(countryMaster);

	        assertTrue(result.isPresent());
	        CountryAndStateDto countryDto = result.get();
	        assertEquals(1, countryDto.getCountryId());
	        assertEquals("Test Country", countryDto.getCountry());
	        assertEquals(1, countryDto.getStates().size());

	        StateAndCityDto stateDto = countryDto.getStates().get(0);
	        assertEquals(10, stateDto.getStateId());
	        assertEquals("Test State", stateDto.getState());
	        assertEquals(1, stateDto.getCities().size());

	        CityDto cityDto = stateDto.getCities().get(0);
	        assertEquals(100, cityDto.getCityId());
	        assertEquals("Test City", cityDto.getCity());
	    }
}
