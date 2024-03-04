package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CompanyDtoTest {

	CompanyDto companyDto = new CompanyDto();
	CompanyDto companyDto1 = new CompanyDto();

	@Test
	void getterDataTest() {
		companyDto.getCompanyId();
		companyDto.getCompanyName();
		companyDto.getCompanyWebsite();
		companyDto.getAddressLineOne();
		companyDto.getCountry();
		companyDto.getState();
		companyDto.getCity();
		companyDto.getZipCode();
		companyDto.hashCode();
		companyDto.toString();
		companyDto.equals(companyDto1);
		companyDto.builder();

	}

	@Test
	void testConstructor() {
		CountryDto countryDto = new CountryDto();
		StateDto stateDto = new StateDto();
		CityDto cityDto = new CityDto();
		CompanyDto companyDto = new CompanyDto(1, "ABC Company", "www.abc.com", "123 Main Street", countryDto, stateDto,
				cityDto, "12345");
		Assertions.assertNotNull(companyDto);
		assertEquals(1, companyDto.getCompanyId());
		assertEquals("ABC Company", companyDto.getCompanyName());
		assertEquals("www.abc.com", companyDto.getCompanyWebsite());
		assertEquals("123 Main Street", companyDto.getAddressLineOne());
		assertEquals(countryDto, companyDto.getCountry());
		assertEquals(stateDto, companyDto.getState());
		assertEquals(cityDto, companyDto.getCity());
		assertEquals("12345", companyDto.getZipCode());
	}

	CountryDto country = new CountryDto();
	StateDto state = new StateDto();
	CityDto city = new CityDto();

	@Test
	void setterDataTest() {
		companyDto.setCompanyId(1);
		companyDto.setCompanyName("RNT");
		companyDto.setCompanyWebsite("www.rnt.ai");
		companyDto.setAddressLineOne("pune kharadi");
		companyDto.setCountry(country);
		companyDto.setState(state);
		companyDto.setCity(city);
		companyDto.setZipCode("cdvh24");
		companyDto.hashCode();
		companyDto.toString();
		companyDto.equals(companyDto1);
	}
}
