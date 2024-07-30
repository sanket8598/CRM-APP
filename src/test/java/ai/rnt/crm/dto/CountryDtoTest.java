package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CountryDtoTest {

	CountryDto dto = new CountryDto();
	CountryDto dto1 = new CountryDto();
	CurrencyDto currencyDto = new CurrencyDto();
	

	@Test
	void getterData() {
		dto.getCountryId();
		dto.getCountry();
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
		assertNotNull(dto);
	}

	@Test
	void setterData() {
		dto.setCountryId(1);
		dto.setCountry("India");
		dto.setCountryCode("+91");
		dto.setCurrency(currencyDto);
		assertNotNull(dto);
	}
}
