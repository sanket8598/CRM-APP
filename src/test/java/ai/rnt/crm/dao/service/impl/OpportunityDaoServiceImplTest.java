package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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

import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.repository.OpportunityRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class OpportunityDaoServiceImplTest {

	@InjectMocks
	OpportunityDaoServiceImpl opportunityDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Opportunity opportunity;

	@Mock
	private OpportunityRepository opportunityRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(opportunityDaoServiceImpl).build();
	}

	@Test
	void getOpportunityDashboardDataTest() {
		List<Opportunity> opportunityList = new ArrayList<>();
		opportunityList.add(new Opportunity());
		opportunityList.add(new Opportunity());
		when(opportunityRepository.findByOrderByCreatedDateDesc()).thenReturn(opportunityList);
		List<Opportunity> retrievedLeads = opportunityDaoServiceImpl.getOpportunityDashboardData();
		assertEquals(opportunityList.size(), retrievedLeads.size());
	}

	@Test
	void getOpportunityByStatusTest() {
		String opportunityStatus = "Open";
		List<Opportunity> opportunityList = new ArrayList<>();
		opportunityList.add(new Opportunity());
		opportunityList.add(new Opportunity());
		when(opportunityRepository.findByStatusOrderByCreatedDateDesc(opportunityStatus)).thenReturn(opportunityList);
		List<Opportunity> retrievedLeads = opportunityDaoServiceImpl.getOpportunityByStatus(opportunityStatus);
		assertEquals(opportunityList.size(), retrievedLeads.size());
	}

	@Test
	void addOpportunityTest() {
		Opportunity opportunuty = new Opportunity();
		when(opportunityRepository.save(opportunuty)).thenReturn(opportunuty);
		Opportunity addedOpportunity = opportunityDaoServiceImpl.addOpportunity(opportunuty);
		verify(opportunityRepository).save(opportunuty);
		assertEquals(opportunuty, addedOpportunity);
	}

	@Test
	void findOpportunityTest() {
		Integer opptyId = 1;
		Opportunity opportunity = new Opportunity();
		when(opportunityRepository.findById(opptyId)).thenReturn(Optional.of(opportunity));
		Optional<Opportunity> retrievedLead = opportunityDaoServiceImpl.findOpportunity(opptyId);
		assertTrue(retrievedLead.isPresent());
		assertEquals(opportunity, retrievedLead.get());
	}

	@Test
	void testGetOpportunityByStatusIn() {
		List<String> statusList = Arrays.asList("Open", "Close");
		Opportunity opportunity1 = new Opportunity();
		Opportunity opportunity2 = new Opportunity();
		List<Opportunity> expectedOpportunities = Arrays.asList(opportunity1, opportunity2);
		when(opportunityRepository.findByStatusInOrderByCreatedDateDesc(statusList)).thenReturn(expectedOpportunities);
		List<Opportunity> actualOpportunities = opportunityDaoServiceImpl.getOpportunityByStatusIn(statusList);
		assertEquals(expectedOpportunities.size(), actualOpportunities.size());
		assertEquals(expectedOpportunities.get(0), actualOpportunities.get(0));
		assertEquals(expectedOpportunities.get(1), actualOpportunities.get(1));
		verify(opportunityRepository).findByStatusInOrderByCreatedDateDesc(statusList);
	}
}
