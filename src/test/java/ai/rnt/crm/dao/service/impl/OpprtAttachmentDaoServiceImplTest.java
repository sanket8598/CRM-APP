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

import ai.rnt.crm.entity.OpprtAttachment;
import ai.rnt.crm.repository.OpprtAttachmentRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class OpprtAttachmentDaoServiceImplTest {

	@InjectMocks
	OpprtAttachmentDaoServiceImpl opprtAttachmentDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	OpprtAttachment opprtAttachment;

	@Mock
	private OpprtAttachmentRepository opprtAttachmentRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(opprtAttachmentDaoServiceImpl).build();
	}

	@Test
	void findByIdTest() throws Exception {
        when(opprtAttachmentRepository.findById(anyInt())).thenReturn(Optional.of(opprtAttachment));
        opprtAttachmentDaoServiceImpl.findById(1);
        verify(opprtAttachmentRepository).findById(1);
	}

	@Test
	void addOpprtAttachmentTest() throws Exception {
		assertNull(opprtAttachmentDaoServiceImpl.addOpprtAttachment(opprtAttachment));
	}
}
