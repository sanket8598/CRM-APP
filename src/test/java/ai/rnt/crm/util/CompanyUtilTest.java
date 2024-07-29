package ai.rnt.crm.util;

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
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;

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

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(companyUtil).build();
	}

	@Test
	void testAddUpdateCompanyDetailsCompanyExists() {
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
	void testAddUpdateCompanyDetailsCompanyDoesNotExist() {
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
}
