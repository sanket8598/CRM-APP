package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CompanyDtoTest {

	CompanyDto companyDto = new CompanyDto();
	CompanyDto companyDto1 = new CompanyDto();

	@Test
	void getterDataTest() {
		CompanyDto build = CompanyDto.builder().zipCode("12343").companyId(1).companyName("abc").companyWebsite("abc").city(new CityDto()).
		country(new CountryDto()).build();
		build.toString();
		CompanyDto.builder().toString();
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
		companyDto1.toString();
		companyDto.equals(companyDto1);
		Assertions.assertNotNull(companyDto);

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
		companyDto1.setCompanyId(1);
		companyDto1.setCompanyName("RNT");
		companyDto1.setCompanyWebsite("www.rnt.ai");
		companyDto1.setAddressLineOne("pune kharadi");
		companyDto1.setCountry(country);
		companyDto1.setState(state);
		companyDto1.setCity(city);
		companyDto1.setZipCode("cdvh24");
		companyDto.hashCode();
		companyDto1.hashCode();
		companyDto.toString();
		companyDto1.toString();
		companyDto.equals(companyDto1);
		Assertions.assertNotNull(companyDto);
	}
	
	 @Test
	     void testCompanyDtoBuilder() {
	        CountryDto country = new CountryDto();
	        StateDto state = new StateDto();
	        CityDto city = new CityDto();
	        CompanyDto companyDto = CompanyDto.builder()
	            .companyId(1)
	            .companyName("Test Company")
	            .companyWebsite("www.testcompany.com")
	            .addressLineOne("123 Test Street")
	            .country(country)
	            .state(state)
	            .city(city)
	            .zipCode("12345")
	            .build();
	        assertEquals(1, companyDto.getCompanyId());
	        assertEquals("Test Company", companyDto.getCompanyName());
	        assertEquals("www.testcompany.com", companyDto.getCompanyWebsite());
	        assertEquals("123 Test Street", companyDto.getAddressLineOne());
	        assertEquals(country, companyDto.getCountry());
	        assertEquals(state, companyDto.getState());
	        assertEquals(city, companyDto.getCity());
	        assertEquals("12345", companyDto.getZipCode());
	    }
}
