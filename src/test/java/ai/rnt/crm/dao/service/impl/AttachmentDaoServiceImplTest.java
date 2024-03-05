package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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

import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.repository.AttachmentRepository;

class AttachmentDaoServiceImplTest {

	@InjectMocks
	AttachmentDaoServiceImpl attachmentDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Attachment attachment;

	@Mock
	private AttachmentRepository attachmentRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(attachmentDaoServiceImpl).build();
	}

	@Test
	void addAttachmentTest() throws Exception {
		assertNull(attachmentDaoServiceImpl.addAttachment(attachment));
	}

	@Test
	void findByIdTest() throws Exception {
        when(attachmentRepository.findById(anyInt())).thenReturn(Optional.of(attachment));
        Optional<Attachment> optionalAttachment = attachmentDaoServiceImpl.findById(1);
        verify(attachmentRepository).findById(1);
        assertTrue(optionalAttachment.isPresent());
	}
}
