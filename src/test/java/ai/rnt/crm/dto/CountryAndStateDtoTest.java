package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class CountryAndStateDtoTest {

	 @Test
	    void testCountryIdGetterSetter() {
	        CountryAndStateDto countryAndStateDto = new CountryAndStateDto();
	        Integer countryId = 101;
	        countryAndStateDto.setCountryId(countryId);
	        assertEquals(countryId, countryAndStateDto.getCountryId());
	    }

	    @Test
	    void testCountryGetterSetter() {
	        CountryAndStateDto countryAndStateDto = new CountryAndStateDto();
	        String country = "India";
	        String countryCode ="INR";
	        String countryFlag = "szdxfcgvhbnm,.fhbjnkm";
	        String currencySymbol = "$";
	        CurrencyDto currencyDto = new CurrencyDto();
	        currencyDto.setCurrencyId(1);
	        countryAndStateDto.setCurrency(currencyDto);
	        countryAndStateDto.setCountryCode(countryCode);
	        countryAndStateDto.getCurrency();
	        countryAndStateDto.getCountryCode();
	        countryAndStateDto.setCountry(country);
	        countryAndStateDto.setCountryFlag(countryFlag);
	        countryAndStateDto.setCurrencySymbol(currencySymbol);
	        assertEquals(country, countryAndStateDto.getCountry());
	        assertEquals(countryFlag, countryAndStateDto.getCountryFlag());
	        assertEquals(currencySymbol, countryAndStateDto.getCurrencySymbol());
	    }

	    @Test
	    void testStatesGetterSetter() {
	        CountryAndStateDto countryAndStateDto = new CountryAndStateDto();
	        StateAndCityDto state1 = new StateAndCityDto();
	        state1.setStateId(1);
	        state1.setState("Maharashtra");
	        StateAndCityDto state2 = new StateAndCityDto();
	        state2.setStateId(2);
	        state2.setState("Karnataka");
	        List<StateAndCityDto> states = Arrays.asList(state1, state2);
	        countryAndStateDto.setStates(states);
	        assertEquals(states, countryAndStateDto.getStates());
	    }
}
