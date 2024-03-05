package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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

import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.repository.ImpLeadRepository;
import ai.rnt.crm.repository.LeadsRepository;

class LeadDaoServiceImplTest {

	@InjectMocks
	LeadDaoServiceImpl leadDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	LeadImportant leadImportant;

	@InjectMocks
	Leads Leads;

	@Mock
	private LeadsRepository leadsRepository;

	@Mock
	private ImpLeadRepository impLeadRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(leadDaoServiceImpl).build();
	}

	@Test
	void addLeadTest() {
		Leads leads = new Leads();
		when(leadsRepository.save(leads)).thenReturn(leads);
		Leads addedLead = leadDaoServiceImpl.addLead(leads);
		verify(leadsRepository).save(leads);
		assertEquals(leads, addedLead);
	}

	@Test
	void getLeadsByStatusTest() {
		String leadsStatus = "Open";
		List<Leads> leadsList = new ArrayList<>();
		leadsList.add(new Leads());
		leadsList.add(new Leads());
		when(leadsRepository.findByStatusOrderByCreatedDateDesc(leadsStatus)).thenReturn(leadsList);
		List<Leads> retrievedLeads = leadDaoServiceImpl.getLeadsByStatus(leadsStatus);
		assertEquals(leadsList.size(), retrievedLeads.size());
	}

	@Test
	void getAllLeadsTest() {
		List<Leads> leadsList = new ArrayList<>();
		leadsList.add(new Leads());
		leadsList.add(new Leads());
		when(leadsRepository.findByOrderByCreatedDateDesc()).thenReturn(leadsList);
		List<Leads> retrievedLeads = leadDaoServiceImpl.getAllLeads();
		assertEquals(leadsList.size(), retrievedLeads.size());
	}

	@Test
	void getLeadDashboardDataTest() {
		List<Leads> leadsList = new ArrayList<>();
		leadsList.add(new Leads());
		leadsList.add(new Leads());
		when(leadsRepository.findByOrderByCreatedDateDesc()).thenReturn(leadsList);
		List<Leads> retrievedLeads = leadDaoServiceImpl.getLeadDashboardData();
		assertEquals(leadsList.size(), retrievedLeads.size());
	}

	@Test
	void getLeadByIdTest() {
		Integer leadId = 1;
		Leads lead = new Leads();
		when(leadsRepository.findById(leadId)).thenReturn(Optional.of(lead));
		Optional<Leads> retrievedLead = leadDaoServiceImpl.getLeadById(leadId);
		assertTrue(retrievedLead.isPresent());
		assertEquals(lead, retrievedLead.get());
	}

	@Test
	void addImportantLeadTest() {
		LeadImportant leadImportant = new LeadImportant();
		when(impLeadRepository.save(leadImportant)).thenReturn(leadImportant);
		Optional<LeadImportant> addedLeadImportant = leadDaoServiceImpl.addImportantLead(leadImportant);
		assertTrue(addedLeadImportant.isPresent());
		assertEquals(leadImportant, addedLeadImportant.get());
	}

	@Test
	void deleteImportantLeadTest() {
		Integer leadId = 1;
		Integer staffId = 2;
		LeadImportant leadImportant = new LeadImportant();
		leadImportant.setId(1);
		when(impLeadRepository.findByLeadLeadIdAndEmployeeStaffId(leadId, staffId))
				.thenReturn(Optional.of(leadImportant));
		boolean result = leadDaoServiceImpl.deleteImportantLead(leadId, staffId);
		verify(impLeadRepository).deleteById(leadImportant.getId());
		assertTrue(result);
	}

	@Test
	void findLeadByEmployeeStaffIdTest() {
		Integer loggedInStaffId = 1;
		List<LeadImportant> leadImportantList = new ArrayList<>();
		leadImportantList.add(new LeadImportant());
		leadImportantList.add(new LeadImportant());
		when(impLeadRepository.findByEmployeeStaffId(loggedInStaffId)).thenReturn(leadImportantList);
		List<LeadImportant> retrievedLeadImportantList = leadDaoServiceImpl.findLeadByEmployeeStaffId(loggedInStaffId);
		assertEquals(leadImportantList.size(), retrievedLeadImportantList.size());
	}

	@Test
	void getFollowUpLeadsTest() {
		LocalDate todayAsDate = LocalDate.now();
		String time = "10:00";
		List<Leads> leadsList = new ArrayList<>();
		leadsList.add(new Leads());
		leadsList.add(new Leads());
		when(leadsRepository.findByRemainderDueOnAndRemainderDueAtAndIsFollowUpRemainder(todayAsDate, time, true))
				.thenReturn(leadsList);
		List<Leads> retrievedLeads = leadDaoServiceImpl.getFollowUpLeads(todayAsDate, time);
		assertEquals(leadsList.size(), retrievedLeads.size());
	}
}
