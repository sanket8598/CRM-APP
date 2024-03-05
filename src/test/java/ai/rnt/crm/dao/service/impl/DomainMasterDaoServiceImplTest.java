package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.repository.DomainMasterRepository;

class DomainMasterDaoServiceImplTest {

	@InjectMocks
	DomainMasterDaoServiceImpl domainMasterDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	DomainMaster domainMaster;

	@Mock
	private DomainMasterRepository domainMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(domainMasterDaoServiceImpl).build();
	}

	@Test
	void getAllDomainsTest() throws Exception {
		List<DomainMaster> list = new ArrayList<>();
		when(domainMasterRepository.findAll()).thenReturn(list);
		domainMasterDaoServiceImpl.getAllDomains();
		verify(domainMasterRepository).findAll();
	}

	@Test
	void addDomainTest() throws Exception {
        when(domainMasterRepository.save(any(DomainMaster.class))).thenReturn(domainMaster);
		Optional<DomainMaster> domainMaster1 = domainMasterDaoServiceImpl.addDomain(domainMaster);
		verify(domainMasterRepository).save(domainMaster);
		 assertTrue(domainMaster1.isPresent());
	}

	@Test
	void findByIdTest() throws Exception {
		when(domainMasterRepository.findById(anyInt())).thenReturn(Optional.of(domainMaster));
		Optional<DomainMaster> domainMaster1 = domainMasterDaoServiceImpl.findById(1);
		verify(domainMasterRepository).findById(1);
		assertTrue(domainMaster1.isPresent());
	}

	@Test
	void findByNameTest() throws Exception {
		when(domainMasterRepository.findById(anyInt())).thenReturn(Optional.of(domainMaster));
		Optional<DomainMaster> domainMaster1 = domainMasterDaoServiceImpl.findByName("Java");
		verify(domainMasterRepository).findByDomainName("Java");
	}

}
