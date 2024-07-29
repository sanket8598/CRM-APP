package ai.rnt.crm.dao.service.impl;

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

import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.repository.StateMasterRepository;

/**
 * @author Nikhil Gaikwad
 * @since 06/03/2024.
 *
 */
class StateDaoServiceImplTest {

	@InjectMocks
	StateDaoServiceImpl stateDaoServiceImpl;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	StateMaster stateMaster;

	@Mock
	private StateMasterRepository stateMasterRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(stateDaoServiceImpl).build();
	}

	@Test
	void getAllStateTest() {
		List<StateMaster> stateList = new ArrayList<>();
		when(stateMasterRepository.findAll()).thenReturn(stateList);
		stateDaoServiceImpl.getAllState();
		verify(stateMasterRepository).findByOrderByStateAsc();
	}

	@Test
	void findBystateTest() {
		String stateName = "SomeState";
		StateMaster state = new StateMaster();
		when(stateMasterRepository.findTopByState(stateName)).thenReturn(Optional.of(state));
		stateDaoServiceImpl.findBystate(stateName);
		verify(stateMasterRepository).findTopByState(stateName);
	}

	@Test
	void addStateTest() {
		StateMaster state = new StateMaster();
		when(stateMasterRepository.save(state)).thenReturn(state);
		stateDaoServiceImpl.addState(state);
		verify(stateMasterRepository).save(state);
	}
}
