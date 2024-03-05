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
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.repository.CallRepository;
import ai.rnt.crm.repository.CallTaskRepository;

class CallDaoServiceImplTest {

	@InjectMocks
	CallDaoServiceImpl callDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	Call call;

	@InjectMocks
	PhoneCallTask callTask;

	@Mock
	private CallRepository callRepository;

	@Mock
	private CallTaskRepository callTaskRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(callDaoServiceImpl).build();
	}

	@Test
	void callTest() throws Exception {
		assertNull(callDaoServiceImpl.call(call));
	}

	@Test
	void getCallsByLeadIdTest() throws Exception {
        when(callRepository.findById(anyInt())).thenReturn(Optional.of(call));
        callDaoServiceImpl.getCallsByLeadId(1);
        verify(callRepository).findByLeadLeadIdOrderByCreatedDateDesc(1);
	}

	@Test
	void getCallByIdTest() throws Exception {
		when(callRepository.findById(anyInt())).thenReturn(Optional.of(call));
		Optional<Call> call = callDaoServiceImpl.getCallById(1);
		verify(callRepository).findById(1);
		 assertTrue(call.isPresent());
	}

	@Test
	void addCallTaskTest() throws Exception {
		assertNull(callDaoServiceImpl.addCallTask(callTask));
	}

	@Test
	void getCallTaskByIdTest() throws Exception {
		when(callTaskRepository.findById(anyInt())).thenReturn(Optional.of(callTask));
		Optional<PhoneCallTask> task = callDaoServiceImpl.getCallTaskById(1);
		verify(callTaskRepository).findById(1);
		 assertTrue(task.isPresent());
	}

	@Test
	void getAllTaskTest() throws Exception {
		List<PhoneCallTask> list = new ArrayList<>();
		when(callTaskRepository.findAll()).thenReturn(list);
		callDaoServiceImpl.getAllTask();
		verify(callTaskRepository).findAll();
	}

	@Test
	void getTodaysCallTaskTest() throws Exception {
		List<PhoneCallTask> list = new ArrayList<>();
		LocalDate todayDate = LocalDate.now();
		when(callTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, "10:10", true))
				.thenReturn(list);
		callDaoServiceImpl.getTodaysCallTask(todayDate, "10:10");
		verify(callTaskRepository).findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, "10:10", true);
	}

	@Test
	void getCallsByLeadIdAndIsOpportunityTest() throws Exception {
		List<Call> list = new ArrayList<>();
		when(callRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(1, false)).thenReturn(list);
		callDaoServiceImpl.getCallsByLeadIdAndIsOpportunity(1);
		verify(callRepository).findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(1, false);
	}
}
