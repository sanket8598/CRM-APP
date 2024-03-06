package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertNull;
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

import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.repository.LeadTaskRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 * 
 */
class LeadTaskDaoServiceImplTest {

	@InjectMocks
	LeadTaskDaoServiceImpl leadTaskDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	LeadTask leadTask;

	@Mock
	private LeadTaskRepository leadTaskRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(leadTaskDaoServiceImpl).build();
	}

	@Test
	void addTaskTest() throws Exception {
		assertNull(leadTaskDaoServiceImpl.addTask(leadTask));
	}

	@Test
	void getTaskByIdTest() throws Exception {
        when(leadTaskRepository.findById(anyInt())).thenReturn(Optional.of(leadTask));
        leadTaskDaoServiceImpl.getTaskById(1);
        verify(leadTaskRepository).findById(1);
	}

	@Test
	void getAllTaskTest() throws Exception {
		List<LeadTask> list = new ArrayList<>();
		when(leadTaskRepository.findAll()).thenReturn(list);
		leadTaskDaoServiceImpl.getAllTask();
		verify(leadTaskRepository).findAll();
	}

	@Test
	void getTodaysLeadTaskTest() throws Exception {
		List<LeadTask> list = new ArrayList<>();
		LocalDate todayDate = LocalDate.now();
		when(leadTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, "10:10", true))
				.thenReturn(list);
		leadTaskDaoServiceImpl.getTodaysLeadTask(todayDate, "10:10");
		verify(leadTaskRepository).findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, "10:10", true);
	}
}
