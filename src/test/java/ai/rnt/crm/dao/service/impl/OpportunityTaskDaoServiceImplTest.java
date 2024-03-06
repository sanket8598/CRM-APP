package ai.rnt.crm.dao.service.impl;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.repository.OpportunityTaskRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class OpportunityTaskDaoServiceImplTest {

	@InjectMocks
	OpportunityTaskDaoServiceImpl opportunityTaskDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	OpportunityTask opportunityTask;

	@Mock
	private OpportunityTaskRepository opportunityTaskRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(opportunityTaskDaoServiceImpl).build();
	}

	@Test
	void getAllTaskTest() throws Exception {
		List<OpportunityTask> list = new ArrayList<>();
		when(opportunityTaskRepository.findAll()).thenReturn(list);
		opportunityTaskDaoServiceImpl.getAllTask();
		verify(opportunityTaskRepository).findAll();
	}

	@Test
	void addOptyTaskTest() throws Exception {
		assertNull(opportunityTaskDaoServiceImpl.addOptyTask(opportunityTask));
	}

	@Test
	void getOptyTaskByIdTest() throws Exception {
        when(opportunityTaskRepository.findById(anyInt())).thenReturn(Optional.of(opportunityTask));
        opportunityTaskDaoServiceImpl.getOptyTaskById(1);
        verify(opportunityTaskRepository).findById(1);
	}
}
