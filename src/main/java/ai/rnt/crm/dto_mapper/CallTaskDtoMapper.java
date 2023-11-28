package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.CallTaskDto;
import ai.rnt.crm.entity.PhoneCallTask;

public class CallTaskDtoMapper {

	private CallTaskDtoMapper() {

	}

	/**
	 * This function will convert CallTaskDto into optional PhoneCallTask Entity.
	 * <b>This function will return null if passed CallTaskDto is null</b> <br>
	 * <b>Param</b> CallTaskDto <br>
	 * <b>Return</b> PhoneCallTask
	 * 
	 * @author Nikhil Gaikwad
	 * @since 28-11-2023
	 * @version 1.0
	 */
	public static final Function<CallTaskDto, Optional<PhoneCallTask>> TO_CALL_TASK = e -> evalMapper(e,
			PhoneCallTask.class);
	/**
	 * @since 28-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<CallTaskDto>, List<PhoneCallTask>> TO_CALL_TASKS = e -> e.stream()
			.map(dm -> TO_CALL_TASK.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert PhoneCallTask Entity into optional CallTaskDto .
	 * <b>This function will return null if passed CallTaskDto is null</b> <br>
	 * <b>Param</b> PhoneCallTask <br>
	 * <b>Return</b> CallTaskDto
	 * 
	 * @since 28-11-2023
	 * @Version 1.0
	 */
	public static final Function<PhoneCallTask, Optional<CallTaskDto>> TO_CALL_TASK_DTO = e -> evalMapper(e,
			CallTaskDto.class);

	/**
	 * @since 28-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<PhoneCallTask>, List<CallTaskDto>> TO_CALL_TASK_DTOS = e -> e.stream()
			.map(dm -> TO_CALL_TASK_DTO.apply(dm).get()).collect(Collectors.toList());

}
