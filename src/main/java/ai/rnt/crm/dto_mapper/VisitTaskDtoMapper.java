package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.VisitTaskDto;
import ai.rnt.crm.entity.VisitTask;

public class VisitTaskDtoMapper {

	private VisitTaskDtoMapper() {

	}

	/**
	 * This function will convert VisitTaskDto into optional VisitTask Entity.
	 * <b>This function will return null if passed VisitTaskDto is null</b> <br>
	 * <b>Param</b> VisitTaskDto <br>
	 * <b>Return</b> VisitTask
	 * 
	 * @author Nikhil Gaikwad
	 * @since 28-11-2023
	 * @version 1.0
	 */
	public static final Function<VisitTaskDto, Optional<VisitTask>> TO_VISIT_TASK = e -> evalMapper(e, VisitTask.class);
	/**
	 * @since 28-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<VisitTaskDto>, List<VisitTask>> TO_VISIT_TASKS = e -> e.stream()
			.map(dm -> TO_VISIT_TASK.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert VisitTask Entity into optional VisitTaskDto .
	 * <b>This function will return null if passed VisitTaskDto is null</b> <br>
	 * <b>Param</b> VisitTask <br>
	 * <b>Return</b> VisitTaskDto
	 * 
	 * @since 28-11-2023
	 * @Version 1.0
	 */
	public static final Function<VisitTask, Optional<VisitTaskDto>> TO_VISIT_TASK_DTO = e -> evalMapper(e,
			VisitTaskDto.class);

	/**
	 * @since 28-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<VisitTask>, List<VisitTaskDto>> TO_VISIT_TASK_DTOS = e -> e.stream()
			.map(dm -> TO_VISIT_TASK_DTO.apply(dm).get()).collect(Collectors.toList());

}
