package ai.rnt.crm.dao.service.impl;

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

import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.repository.VisitRepository;
import ai.rnt.crm.repository.VisitTaskRepository;

class VisitDaoServiceImplTest {

	@InjectMocks
	VisitDaoServiceImpl visitDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Visit visit;

	@InjectMocks
	VisitTask visitTask;

	@Mock
	private VisitRepository visitRepository;

	@Mock
	private VisitTaskRepository visitTaskRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(visitDaoServiceImpl).build();
	}

	@Test
	void saveVisitTest() {
		Visit visit = new Visit();
		when(visitRepository.save(visit)).thenReturn(visit);
		visitDaoServiceImpl.saveVisit(visit);
		verify(visitRepository).save(visit);
	}

	@Test
	void getVisitsByLeadIdTest() {
		Integer leadId = 1;
		List<Visit> visitsList = new ArrayList<>();
		when(visitRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId)).thenReturn(visitsList);
		visitDaoServiceImpl.getVisitsByLeadId(leadId);
		verify(visitRepository).findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Test
	void getVisitsByVisitIdTest() {
		Integer visitId = 1;
		Visit visit = new Visit();
		when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));
		visitDaoServiceImpl.getVisitsByVisitId(visitId);
		verify(visitRepository).findById(visitId);
	}

	@Test
	void addVisitTaskTest() {
		VisitTask visitTask = new VisitTask();
		when(visitTaskRepository.save(visitTask)).thenReturn(visitTask);
		visitDaoServiceImpl.addVisitTask(visitTask);
		verify(visitTaskRepository).save(visitTask);
	}

	@Test
	void getVisitTaskByIdTest() {
		Integer taskId = 1;
		VisitTask visitTask = new VisitTask();
		when(visitTaskRepository.findById(taskId)).thenReturn(Optional.of(visitTask));
		visitDaoServiceImpl.getVisitTaskById(taskId);
		verify(visitTaskRepository).findById(taskId);
	}

	@Test
	void getAllTaskTest() {
		List<VisitTask> taskList = new ArrayList<>();
		when(visitTaskRepository.findAll()).thenReturn(taskList);
		visitDaoServiceImpl.getAllTask();
		verify(visitTaskRepository).findAll();
	}

	@Test
	void getTodaysAllVisitTaskTest() {
		LocalDate todayAsDate = LocalDate.now();
		String time = "12:00";
		List<VisitTask> visitTaskList = new ArrayList<>();
		when(visitTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true))
				.thenReturn(visitTaskList);
		visitDaoServiceImpl.getTodaysAllVisitTask(todayAsDate, time);
		verify(visitTaskRepository).findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true);
	}

	@Test
	void getVisitsByLeadIdAndIsOpportunityTest() {
		Integer leadId = 1;
		List<Visit> visitList = new ArrayList<>();
		when(visitRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false))
				.thenReturn(visitList);
		visitDaoServiceImpl.getVisitsByLeadIdAndIsOpportunity(leadId);
		verify(visitRepository).findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false);
	}
}
