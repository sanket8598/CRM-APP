package ai.rnt.crm.util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;

class CompanyUtilTest {

	@InjectMocks
    private CompanyUtil companyUtil;

    @Mock
    private CityDaoService cityDaoService;
    
    @Mock
    private StateDaoService stateDaoService;
    
    @Mock
    private CountryDaoService countryDaoService;
    
    @Mock
    private CompanyMasterDaoService companyMasterDaoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    //@Test
    public void testAddUpdateCompanyDetails_ExistingCompany() {
        // Mocking input data
        UpdateLeadDto dto = new UpdateLeadDto();
        dto.setCity("ExistingCity");
        dto.setState("ExistingState");
        dto.setCountry("ExistingCountry");
        dto.setCompanyName("ExistingCompany");
        dto.setCompanyWebsite("http://example.com");
        Contacts contact = new Contacts();
        CompanyDto existingCompanyDto = new CompanyDto();
        existingCompanyDto.setCompanyName("ExistingCompany");

        // Mocking behaviors
        when(cityDaoService.existCityByName("ExistingCity")).thenReturn(Optional.of(new CityMaster()));
        when(stateDaoService.findBystate("ExistingState")).thenReturn(Optional.of(new StateMaster()));
        when(countryDaoService.findByCountryName("ExistingCountry")).thenReturn(Optional.of(new CountryMaster()));
        when(companyMasterDaoService.findByCompanyName("ExistingCompany")).thenReturn(Optional.empty());

        // Calling the method under test
        companyUtil.addUpdateCompanyDetails(cityDaoService, stateDaoService, countryDaoService, companyMasterDaoService, dto, contact);

        // Add your assertions here to verify the expected behavior
    }

   // @Test
    public void testAddUpdateCompanyDetails_NewCompany() {
        // Mocking input data
        UpdateLeadDto dto = new UpdateLeadDto();
        dto.setCity("NewCity");
        dto.setState("NewState");
        dto.setCountry("NewCountry");
        dto.setCompanyName("NewCompany");
        dto.setCompanyWebsite("http://example.com");
        Contacts contact = new Contacts();

        // Mocking behaviors
        when(cityDaoService.existCityByName("NewCity")).thenReturn(Optional.empty());
        when(stateDaoService.findBystate("NewState")).thenReturn(Optional.empty());
        when(countryDaoService.findByCountryName("NewCountry")).thenReturn(Optional.empty());

        // Calling the method under test
        companyUtil.addUpdateCompanyDetails(cityDaoService, stateDaoService, countryDaoService, companyMasterDaoService, dto, contact);

        // Add your assertions here to verify the expected behavior
    }
}
