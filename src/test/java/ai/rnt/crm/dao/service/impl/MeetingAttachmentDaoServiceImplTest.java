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

import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.repository.MeetingAttachmentRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 * 
 */
class MeetingAttachmentDaoServiceImplTest {

	@InjectMocks
	MeetingAttachmentDaoServiceImpl meetingAttachmentDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	MeetingAttachments meetingAttachments;

	@Mock
	private MeetingAttachmentRepository meetingAttachmentRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(meetingAttachmentDaoServiceImpl).build();
	}

	@Test
	void addMeetingAttachmentTest() throws Exception {
		assertNull(meetingAttachmentDaoServiceImpl.addMeetingAttachment(meetingAttachments));
	}

	@Test
	void findByIdTest() throws Exception {
        when(meetingAttachmentRepository.findById(anyInt())).thenReturn(Optional.of(meetingAttachments));
        meetingAttachmentDaoServiceImpl.findById(1);
        verify(meetingAttachmentRepository).findById(1);
	}

	@Test
	void removeExistingMeetingAttachmentTest() {
		MeetingAttachments meetingAttachment = new MeetingAttachments();
		meetingAttachmentDaoServiceImpl.removeExistingMeetingAttachment(meetingAttachment);
		verify(meetingAttachmentRepository).delete(meetingAttachment);
	}
}
