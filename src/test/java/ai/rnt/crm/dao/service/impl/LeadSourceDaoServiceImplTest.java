package ai.rnt.crm.dao.service.impl;

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

import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.repository.LeadSourceMasterRepository;

class LeadSourceDaoServiceImplTest {

	@InjectMocks
	LeadSourceDaoServiceImpl leadSourceDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	LeadSourceMaster leadSourceMaster;

	@Mock
	private LeadSourceMasterRepository leadSourceMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(leadSourceDaoServiceImpl).build();
	}

	@Test
	void getLeadSourceByIdTest() throws Exception {
        when(leadSourceMasterRepository.findById(anyInt())).thenReturn(Optional.of(leadSourceMaster));
        leadSourceDaoServiceImpl.getLeadSourceById(1);
        verify(leadSourceMasterRepository).findById(1);
	}

	@Test
	void getAllLeadSourceTest() throws Exception {
		List<LeadSourceMaster> list = new ArrayList<>();
		when(leadSourceMasterRepository.findByDeletedDateIsNullOrderBySourceNameAsc()).thenReturn(list);
		leadSourceDaoServiceImpl.getAllLeadSource();
		verify(leadSourceMasterRepository).findByDeletedDateIsNullOrderBySourceNameAsc();
	}

	@Test
	void getByNameTest() throws Exception {
		when(leadSourceMasterRepository.findBySourceName(anyString())).thenReturn(Optional.of(leadSourceMaster));
		Optional<LeadSourceMaster> leadSource = leadSourceDaoServiceImpl.getByName("Online");
		verify(leadSourceMasterRepository).findBySourceName("Online");
		 assertTrue(leadSource.isPresent());
	}

	@Test
	void saveTest() throws Exception {
        when(leadSourceMasterRepository.save(any(LeadSourceMaster.class))).thenReturn(leadSourceMaster);
	    Optional<LeadSourceDto> leadSource = leadSourceDaoServiceImpl.save(leadSourceMaster);
		verify(leadSourceMasterRepository).save(leadSourceMaster);
		assertTrue(leadSource.isPresent());
	}

}
