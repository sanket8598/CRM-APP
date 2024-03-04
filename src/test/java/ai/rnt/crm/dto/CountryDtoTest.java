package ai.rnt.crm.dto;

import org.junit.jupiter.api.Test;

class CountryDtoTest {

	CountryDto dto = new CountryDto();
	CountryDto dto1 = new CountryDto();
	

	@Test
	void getterData() {
		dto.getCountryId();
		dto.getCountry();
		dto.canEqual(dto);
		dto.equals(dto1);
		dto.hashCode();
		dto.toString();
	}

	@Test
	void setterData() {
		dto.setCountryId(1);
		dto.setCountry("India");
	}
}
