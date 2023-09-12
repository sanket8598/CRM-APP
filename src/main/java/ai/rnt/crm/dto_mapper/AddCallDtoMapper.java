package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.AddCallDto;
import ai.rnt.crm.entity.AddCall;

public class AddCallDtoMapper {

	AddCallDtoMapper() {

	}

	/**
	 * This function will convert AddCallDto into optional AddCall Entity. <b>This
	 * function will return null if passed CountryDto is null</b> <br>
	 * <b>Param</b> AddCallDto <br>
	 * <b>Return</b> AddCall
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<AddCallDto, Optional<AddCall>> TO_CALL = e -> evalMapper(e, AddCall.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<AddCallDto>, List<AddCall>> TO_CALLS = e -> e.stream()
			.map(dm -> TO_CALL.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert AddCall Entity into optional AddCallDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> AddCall <br>
	 * <b>Return</b> AddCallDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<AddCall, Optional<AddCallDto>> TO_CALL_DTO = e -> evalMapper(e, AddCallDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<AddCall>, List<AddCallDto>> TO_CALL_DTOS = e -> e.stream()
			.map(dm -> TO_CALL_DTO.apply(dm).get()).collect(Collectors.toList());

}
