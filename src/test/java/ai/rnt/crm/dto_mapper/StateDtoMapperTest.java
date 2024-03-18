package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.StateMaster;

class StateDtoMapperTest {

	@Test
	void testToState() {
		StateDto stateDto = new StateDto();
		Optional<StateMaster> stateMasterOptional = StateDtoMapper.TO_STATE.apply(stateDto);
		assertNotNull(stateMasterOptional);
	}

	@Test
	void testToStates() {
		Collection<StateDto> stateDtoCollection = new ArrayList<>();
		List<StateMaster> stateMasterList = StateDtoMapper.TO_STATES.apply(stateDtoCollection);
		assertNotNull(stateMasterList);
	}

	@Test
	void testToStateDto() {
		StateMaster stateMaster = new StateMaster();
		Optional<StateDto> stateDtoOptional = StateDtoMapper.TO_STATE_DTO.apply(stateMaster);
		assertNotNull(stateDtoOptional);
	}

	@Test
	void testToStateDtos() {
		Collection<StateMaster> stateMasterCollection = new ArrayList<>();
		List<StateDto> stateDtoList = StateDtoMapper.TO_STATE_DTOS.apply(stateMasterCollection);
		assertNotNull(stateDtoList);
	}
}
