package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.CallDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.entity.Call;

public class CallDtoMapper {

	CallDtoMapper() {

	}

	/**
	 * This function will convert AddCallDto into optional AddCall Entity. <b>This
	 * function will return null if passed AddCallDto is null</b> <br>
	 * <b>Param</b> AddCallDto <br>
	 * <b>Return</b> AddCall
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<CallDto, Optional<Call>> TO_CALL = e -> evalMapper(e, Call.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CallDto>, List<Call>> TO_CALLS = e -> e.stream()
			.map(dm -> TO_CALL.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<Call, Optional<CallDto>> TO_CALL_DTO = e -> evalMapper(e, CallDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Call>, List<CallDto>> TO_CALL_DTOS = e -> e.stream()
			.map(dm -> TO_CALL_DTO.apply(dm).get()).collect(Collectors.toList());

	
	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed AddCall is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<Call, Optional<EditCallDto>> TO_EDIT_CALL_DTO = e -> evalMapper(e, EditCallDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Call>, List<EditCallDto>> TO_EDIT_CALL_DTOS = e -> e.stream()
			.map(dm -> TO_EDIT_CALL_DTO.apply(dm).get()).collect(Collectors.toList());

}
