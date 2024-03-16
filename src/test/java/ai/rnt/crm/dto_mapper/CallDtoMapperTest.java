package ai.rnt.crm.dto_mapper;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.GetCallDto;
import ai.rnt.crm.entity.Call;

class CallDtoMapperTest {

	@Test
	void testToCall() {
		CallDto callDto = new CallDto();
		Optional<Call> result = CallDtoMapper.TO_CALL.apply(callDto);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCalls() {
		List<CallDto> callDtoList = new ArrayList<>();
		List<Call> result = CallDtoMapper.TO_CALLS.apply(callDtoList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToCallDto() {
		Call call = new Call();
		Optional<CallDto> result = CallDtoMapper.TO_CALL_DTO.apply(call);
		assertTrue(result.isPresent());
	}

	@Test
	void testToCallDtos() {
		List<Call> callList = new ArrayList<>();
		List<CallDto> result = CallDtoMapper.TO_CALL_DTOS.apply(callList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToEditCallDto() {
		Call call = new Call();
		Optional<EditCallDto> result = CallDtoMapper.TO_EDIT_CALL_DTO.apply(call);
		assertTrue(result.isPresent());
	}

	@Test
	void testToEditCallDtos() {
		List<Call> callList = new ArrayList<>();
		List<EditCallDto> result = CallDtoMapper.TO_EDIT_CALL_DTOS.apply(callList);
		assertTrue(result.isEmpty());
	}

	@Test
	void testToGetCallDto() {
		Call call = new Call();
		Optional<GetCallDto> result = CallDtoMapper.TO_GET_CALL_DTO.apply(call);
		assertTrue(result.isPresent());
	}
}
