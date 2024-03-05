package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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

import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.repository.CountryMasterRepository;

class CountryDaoServiceImplTest {

	@InjectMocks
	CountryDaoServiceImpl countryDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	CountryMaster countryMaster;

	@Mock
	private CountryMasterRepository countryMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(countryDaoServiceImpl).build();
	}

	@Test
	void getAllCountryTest() throws Exception {
		List<CountryMaster> list = new ArrayList<>();
		when(countryMasterRepository.findAll()).thenReturn(list);
		countryDaoServiceImpl.getAllCountry();
		verify(countryMasterRepository).findAll();
	}

	@Test
	void findByCountryNameTest() throws Exception {
		when(countryMasterRepository.findTopByCountry(anyString())).thenReturn(Optional.of(countryMaster));
		Optional<CountryMaster> countryMaster = countryDaoServiceImpl.findByCountryName("India");
		verify(countryMasterRepository).findTopByCountry("India");
		 assertTrue(countryMaster.isPresent());
	}

	@Test
	void addCountryTest() throws Exception {
		assertNull(countryDaoServiceImpl.addCountry(countryMaster));
	}

}
