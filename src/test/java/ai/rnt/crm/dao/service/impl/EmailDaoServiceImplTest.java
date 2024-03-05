package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
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

import ai.rnt.crm.entity.Email;
import ai.rnt.crm.repository.EmailRepository;

/**
 * @author Nikhil Gaikwad
 * @since 05/03/2024
 *
 */
class EmailDaoServiceImplTest {

	@InjectMocks
	EmailDaoServiceImpl emailDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Email email;

	@Mock
	private EmailRepository emailRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(emailDaoServiceImpl).build();
	}

	@Test
	void emailTest() throws Exception {
		assertNull(emailDaoServiceImpl.email(email));
	}

	@Test
	void getEmailByLeadIdTest() throws Exception {
		List<Email> list = new ArrayList<>();
		when(emailRepository.findByLeadLeadIdOrderByCreatedDateDesc(1)).thenReturn(list);
		emailDaoServiceImpl.getEmailByLeadId(1);
		verify(emailRepository).findByLeadLeadIdOrderByCreatedDateDesc(1);
	}

	@Test
	void findByIdTest() throws Exception {
		Email email = new Email();
		when(emailRepository.findById(anyInt())).thenReturn(Optional.of(email));
		emailDaoServiceImpl.findById(1);
		verify(emailRepository).findById(1);
	}

	@Test
	void emailPresentForLeadLeadIdTest() {
		Integer addMailId = 1;
		Integer leadId = 1;
		when(emailRepository.existsByMailIdAndLeadLeadId(addMailId, leadId)).thenReturn(true);
		Boolean isEmailPresent = emailDaoServiceImpl.emailPresentForLeadLeadId(addMailId, leadId);
		assertTrue(isEmailPresent);
	}

	@Test
	void isScheduledEmailsTest() {
		LocalDate todayAsDate = LocalDate.now();
		String time = "10:00";
		boolean isScheduled = true;
		List<Email> scheduledEmails = new ArrayList<>();
		scheduledEmails.add(new Email());
		scheduledEmails.add(new Email());
		scheduledEmails.add(new Email());
		when(emailRepository.findByScheduledOnAndScheduledAtAndScheduled(todayAsDate, time, isScheduled))
				.thenReturn(scheduledEmails);
		List<Email> retrievedScheduledEmails = emailDaoServiceImpl.isScheduledEmails(todayAsDate, time);
		assertEquals(scheduledEmails.size(), retrievedScheduledEmails.size());
	}

	@Test
	void getEmailByLeadIdAndIsOpportunityTest() {
		Integer leadId = 1;
		boolean isOpportunity = false;
		List<Email> emails = new ArrayList<>();
		emails.add(new Email());
		emails.add(new Email());
		emails.add(new Email());
		when(emailRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, isOpportunity))
				.thenReturn(emails);
		List<Email> retrievedEmails = emailDaoServiceImpl.getEmailByLeadIdAndIsOpportunity(leadId);
		assertEquals(emails.size(), retrievedEmails.size());
	}

}
