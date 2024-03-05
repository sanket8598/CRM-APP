package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.repository.ContactRepository;

class ContactDaoServiceImplTest {

	@InjectMocks
	ContactDaoServiceImpl contactDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Contacts contacts;

	@Mock
	private ContactRepository contactRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(contactDaoServiceImpl).build();
	}

	@Test
	void addContactTest() throws Exception {
		assertNull(contactDaoServiceImpl.addContact(contacts));
	}

	@Test
	void contactsOfLeadTest() throws Exception {
        when(contactRepository.findById(anyInt())).thenReturn(Optional.of(contacts));
        contactDaoServiceImpl.contactsOfLead(1);
        verify(contactRepository).findByLeadLeadIdOrderByCreatedDate(1);
	}

	@Test
	void findByIdTest() throws Exception {
		when(contactRepository.findById(anyInt())).thenReturn(Optional.of(contacts));
		contactDaoServiceImpl.findById(1);
		verify(contactRepository).findById(1);
	}
}
