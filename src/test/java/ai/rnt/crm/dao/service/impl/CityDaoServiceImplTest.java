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

import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.repository.CityMasterRepository;

class CityDaoServiceImplTest {

	@InjectMocks
	CityDaoServiceImpl cityDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	CityMaster cityMaster;

	@Mock
	private CityMasterRepository cityMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(cityDaoServiceImpl).build();
	}

	@Test
	void getAllCityTest() throws Exception {
		List<CityMaster> list = new ArrayList<>();
		when(cityMasterRepository.findAll()).thenReturn(list);
		cityDaoServiceImpl.getAllCity();
		verify(cityMasterRepository).findByOrderByCityAsc();
	}

	@Test
	void existCityByNameTest() throws Exception {
		when(cityMasterRepository.findTopByCity(anyString())).thenReturn(Optional.of(cityMaster));
		Optional<CityMaster> city = cityDaoServiceImpl.existCityByName("Pune");
		verify(cityMasterRepository).findTopByCity("Pune");
		 assertTrue(city.isPresent());
	}

	@Test
	void addCityTest() throws Exception {
		assertNull(cityDaoServiceImpl.addCity(cityMaster));
	}
}
