package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.repository.CompanyMasterRepository;

class CompanyMasterDaoServiceImplTest {

	@InjectMocks
	CompanyMasterDaoServiceImpl companyMasterDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	CompanyDto companyDto;

	@InjectMocks
	CompanyMaster companyMaster;

	@Mock
	private CompanyMasterRepository companyMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(companyMasterDaoServiceImpl).build();
	}

	@Test
	void getByIdTest() throws Exception {
        when(companyMasterRepository.findById(anyInt())).thenReturn(Optional.of(companyMaster));
        companyMasterDaoServiceImpl.getById(1);
        verify(companyMasterRepository).findById(1);
	}

	@Test
	void saveTest() throws Exception {
        when(companyMasterRepository.save(any(CompanyMaster.class))).thenReturn(companyMaster);
        companyMasterDaoServiceImpl.save(companyMaster);
        verify(companyMasterRepository).save(companyMaster);
	}

	@Test
	void findByCompanyNameTest() throws Exception {
		when(companyMasterRepository.findTopByCompanyName(anyString())).thenReturn(companyMaster);
		companyMasterDaoServiceImpl.findByCompanyName("RNT");
		verify(companyMasterRepository).findTopByCompanyName("RNT");
	}
	
	 @Test
	    void testFindCompanyById() {
	        Integer companyId = 1;
	        Optional<CompanyMaster> company = Optional.of(new CompanyMaster());
	        when(companyMasterRepository.findById(companyId)).thenReturn(company);
	        Optional<CompanyMaster> result = companyMasterDaoServiceImpl.findCompanyById(companyId);
	        assertEquals(company, result);
	    }
	 
	 @Test
	    void testFindByCountryId() {
	        Integer countryId = 1;
	        List<CompanyMaster> companyMasters = new ArrayList<>();
	        when(companyMasterRepository.findByCountryCountryId(countryId)).thenReturn(companyMasters);
	        List<CompanyMaster> result = companyMasterDaoServiceImpl.findByCountryId(countryId);
	        assertEquals(companyMasters, result);
	    }
	 
	 @Test
	    void testFindByStateId() {
	        Integer stateId = 1;
	        List<CompanyMaster> companies = List.of(new CompanyMaster());
	        when(companyMasterRepository.findByStateStateId(stateId)).thenReturn(companies);
	        List<CompanyMaster> result = companyMasterDaoServiceImpl.findByStateId(stateId);
	        assertEquals(companies, result);
	    }
	 
	 @Test
	    void testFindByCityId() {
	        Integer cityId = 1;
	        List<CompanyMaster> companies = List.of(new CompanyMaster());
	        when(companyMasterRepository.findByCityCityId(cityId)).thenReturn(companies);
	        List<CompanyMaster> result = companyMasterDaoServiceImpl.findByCityId(cityId);
	        assertEquals(companies, result);
	    }
	 
	 @Test
	    void testIsCompanyPresent() {
	        String companyName = "Test Company";
	        Integer countryId = 1;
	        Integer stateId = 1;

	        when(companyMasterRepository.existsByCompanyNameAndCountryCountryIdAndStateStateId(companyName, countryId, stateId)).thenReturn(true);

	        boolean result = companyMasterDaoServiceImpl.isCompanyPresent(companyName, countryId, stateId);

	        assertTrue(result);
	    }
}
