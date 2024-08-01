package ai.rnt.crm.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.CurrencyDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.CurrencyMaster;
import ai.rnt.crm.entity.StateMaster;

class CompanyUtilTest {

	@InjectMocks
	private CompanyUtil companyUtil;

	@Mock
	private CityDaoService cityDaoService;

	@Autowired
	MockMvc mockMvc;

	@Mock
	private StateDaoService stateDaoService;

	@Mock
	private CountryDaoService countryDaoService;

	@Mock
	private CompanyMasterDaoService companyMasterDaoService;

	@Mock
	private CurrencyDaoService currencyDaoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(companyUtil).build();
	}

	@Test
	void testAddUpdateCompanyDetailsCompanyExists() throws Exception {
		CityDaoService cityDaoService = mock(CityDaoService.class);
		StateDaoService stateDaoService = mock(StateDaoService.class);
		CountryDaoService countryDaoService = mock(CountryDaoService.class);
		CurrencyDaoService currencyDaoService = mock(CurrencyDaoService.class);
		CompanyMasterDaoService companyMasterDaoService = mock(CompanyMasterDaoService.class);
		UpdateLeadDto dto = new UpdateLeadDto();
		dto.setCompanyName("RNT");
		Contacts contact = new Contacts();
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(1);
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencySymbol("$");
		dto.setCurrency(currencyDto);
		when(companyMasterDaoService.findByCompanyName(Mockito.anyString())).thenReturn(Optional.of(mock(CompanyDto.class)));
		when(companyMasterDaoService.save(companyMaster)).thenReturn(Optional.of(mock(CompanyDto.class)));
		CompanyUtil.addUpdateCompanyDetails(cityDaoService, stateDaoService, countryDaoService, companyMasterDaoService,
				currencyDaoService,dto, contact);
		verify(companyMasterDaoService).findByCompanyName(dto.getCompanyName());
	}

	@Test
	void testAddUpdateCompanyDetailsCompanyDoesNotExist() throws Exception {
		CityDaoService cityDaoService = mock(CityDaoService.class);
		StateDaoService stateDaoService = mock(StateDaoService.class);
		CountryDaoService countryDaoService = mock(CountryDaoService.class);
		CompanyMasterDaoService companyMasterDaoService = mock(CompanyMasterDaoService.class);
		CurrencyDaoService currencyDaoService = mock(CurrencyDaoService.class);
		UpdateLeadDto dto = new UpdateLeadDto();
		Contacts contact = new Contacts();
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(1);
		CurrencyDto currencyDto = new CurrencyDto();
		currencyDto.setCurrencySymbol("$");
		dto.setCurrency(currencyDto);
		when(companyMasterDaoService.findByCompanyName(dto.getCompanyName())).thenReturn(Optional.empty());
		when(companyMasterDaoService.save(companyMaster)).thenReturn(Optional.of(mock(CompanyDto.class)));
		CompanyUtil.addUpdateCompanyDetails(cityDaoService, stateDaoService, countryDaoService, companyMasterDaoService,
				currencyDaoService,dto, contact);
		verify(companyMasterDaoService).findByCompanyName(dto.getCompanyName());
	}
	
	@Test
    void testSetCompDetailsWithNewCountry() {
        CountryMaster country = new CountryMaster();
        country.setCountry("India");

        StateMaster state = new StateMaster();
        state.setState("Maharashtra");

        CityMaster city = new CityMaster();
        city.setCity("Pune");

        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setCurrencySymbol("$");
        currencyDto.setCurrencyName("USD");
        currencyDto.setCurrencyId(1);

        UpdateLeadDto dto = new UpdateLeadDto();
        dto.setCountry("India");
        dto.setState("Maharashtra");
        dto.setCity("Pune");
        dto.setZipCode("411001");
        dto.setAddressLineOne("Address Line One");
        dto.setCurrency(currencyDto);

        when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(country);
        when(stateDaoService.addState(any(StateMaster.class))).thenReturn(state);
        when(cityDaoService.addCity(any(CityMaster.class))).thenReturn(city);
        when(currencyDaoService.addCurrency(any(CurrencyMaster.class))).thenReturn(new CurrencyMaster());

        Optional<CountryMaster> findByCountryName = Optional.empty();
        Optional<StateMaster> findBystate = Optional.empty();
        Optional<CityMaster> existCityByName =Optional.empty();

        CompanyMaster companyMaster = new CompanyMaster();

        companyUtil.setCompDetails(findByCountryName, findBystate, existCityByName, dto, companyMaster, cityDaoService, stateDaoService, countryDaoService, currencyDaoService);

        assertEquals("India", companyMaster.getCountry().getCountry());
        assertEquals("Maharashtra", companyMaster.getState().getState());
        assertEquals("Pune", companyMaster.getCity().getCity());
        assertEquals("411001", companyMaster.getZipCode());
        assertEquals("Address Line One", companyMaster.getAddressLineOne());
    }
	
	 @Test
	    void testSetCompDetailsWithExistingCountry() {
	        CountryMaster country = new CountryMaster();
	        country.setCountry("India");

	        StateMaster state = new StateMaster();
	        state.setState("Maharashtra");

	        CityMaster city = new CityMaster();
	        city.setCity("Pune");

	        CurrencyDto currencyDto = new CurrencyDto();
	        currencyDto.setCurrencySymbol("$");
	        currencyDto.setCurrencyName("USD");
	        currencyDto.setCurrencyId(1);

	        UpdateLeadDto dto = new UpdateLeadDto();
	        dto.setCountry("India");
	        dto.setState("Maharashtra");
	        dto.setCity("Pune");
	        dto.setZipCode("411001");
	        dto.setAddressLineOne("Address Line One");
	        dto.setCurrency(currencyDto);

	        when(countryDaoService.addCountry(any(CountryMaster.class))).thenReturn(country);
	        when(stateDaoService.addState(any(StateMaster.class))).thenReturn(state);
	        when(cityDaoService.addCity(any(CityMaster.class))).thenReturn(city);
	        when(currencyDaoService.addCurrency(any(CurrencyMaster.class))).thenReturn(new CurrencyMaster());

	        Optional<CountryMaster> findByCountryName = Optional.of(country);
	        Optional<StateMaster> findBystate = Optional.of(state);
	        Optional<CityMaster> existCityByName = Optional.of(city);

	        CompanyMaster companyMaster = new CompanyMaster();

	        companyUtil.setCompDetails(findByCountryName, findBystate, existCityByName, dto, companyMaster, cityDaoService, stateDaoService, countryDaoService, currencyDaoService);

	        assertEquals("India", companyMaster.getCountry().getCountry());
	        assertEquals("Maharashtra", companyMaster.getState().getState());
	        assertEquals("Pune", companyMaster.getCity().getCity());
	        assertEquals("411001", companyMaster.getZipCode());
	        assertEquals("Address Line One", companyMaster.getAddressLineOne());
	    }
}
