package ai.rnt.crm.dao.service.impl;

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

import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.repository.MeetingRepository;
import ai.rnt.crm.repository.MeetingTaskRepository;

class MeetingDaoServiceImplTest {

	@InjectMocks
	MeetingDaoServiceImpl meetingDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Meetings meetings;

	@InjectMocks
	MeetingTask meetingTask;

	@Mock
	private MeetingRepository meetingRepository;

	@Mock
	private MeetingTaskRepository meetingTaskRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(meetingDaoServiceImpl).build();
	}

	@Test
	void addMeetingTest() throws Exception {
		assertNull(meetingDaoServiceImpl.addMeeting(meetings));
	}

	@Test
	void getMeetingByIdTest() throws Exception {
        when(meetingRepository.findById(anyInt())).thenReturn(Optional.of(meetings));
        meetingDaoServiceImpl.getMeetingById(1);
        verify(meetingRepository).findById(1);
	}

	@Test
	void addMeetingTaskTest() throws Exception {
		assertNull(meetingDaoServiceImpl.addMeetingTask(meetingTask));
	}

	@Test
	void getMeetingByLeadIdTest() throws Exception {
        when(meetingRepository.findById(anyInt())).thenReturn(Optional.of(meetings));
        meetingDaoServiceImpl.getMeetingByLeadId(1);
        verify(meetingRepository).findByLeadLeadIdOrderByCreatedDateDesc(1);
	}
	
	@Test
	void getMeetingTaskByIdTest() throws Exception {
		when(meetingTaskRepository.findById(anyInt())).thenReturn(Optional.of(meetingTask));
		Optional<MeetingTask> task = meetingDaoServiceImpl.getMeetingTaskById(1);
		verify(meetingTaskRepository).findById(1);
		 assertTrue(task.isPresent());
	}
	
	@Test
	void getTodaysMeetingTaskTest() throws Exception {
		List<MeetingTask> list = new ArrayList<>();
		LocalDate todayDate = LocalDate.now();
		when(meetingTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, "10:10", true))
				.thenReturn(list);
		meetingDaoServiceImpl.getTodaysMeetingTask(todayDate, "10:10");
		verify(meetingTaskRepository).findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, "10:10", true);
	}
	
	@Test
	void getAllMeetingTaskTest() throws Exception {
		List<MeetingTask> list = new ArrayList<>();
		when(meetingTaskRepository.findAll()).thenReturn(list);
		meetingDaoServiceImpl.getAllMeetingTask();
		verify(meetingTaskRepository).findAll();
	}
	
	@Test
	void getMeetingByLeadIdAndIsOpportunityTest() throws Exception {
		List<Meetings> list = new ArrayList<>();
		when(meetingRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(1, false)).thenReturn(list);
		meetingDaoServiceImpl.getMeetingByLeadIdAndIsOpportunity(1);
		verify(meetingRepository).findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(1, false);
	}
}
