package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.StateDto;
import ai.rnt.crm.entity.StateMaster;

public class StateDtoMapper {

	StateDtoMapper() {

	}

	/**
	 * This function will convert StateDto into optional State Entity. <b>This
	 * function will return null if passed CountryDto is null</b> <br>
	 * <b>Param</b> StateDto <br>
	 * <b>Return</b> State
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<StateDto, Optional<StateMaster>> TO_STATE = e -> evalMapper(e, StateMaster.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<StateDto>, List<StateMaster>> TO_STATES = e -> e.stream()
			.map(dm -> TO_STATE.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert State Entity into optional StateDto . <b>This
	 * function will return null if passed Company is null</b> <br>
	 * <b>Param</b> State <br>
	 * <b>Return</b> StateDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<StateMaster, Optional<StateDto>> TO_STATE_DTO = e -> evalMapper(e, StateDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<StateMaster>, List<StateDto>> TO_STATE_DTOS = e -> e.stream()
			.map(dm -> TO_STATE_DTO.apply(dm).get()).collect(Collectors.toList());

}
