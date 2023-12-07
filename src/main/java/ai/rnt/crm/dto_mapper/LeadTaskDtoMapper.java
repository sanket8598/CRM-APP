package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.GetLeadTaskDto;
import ai.rnt.crm.dto.LeadTaskDto;
import ai.rnt.crm.entity.LeadTask;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class LeadTaskDtoMapper {

	/**
	 * This function will convert LeadTaskDto into optional LeadTask Entity. <b>This
	 * function will return null if passed LeadTaskDto is null</b> <br>
	 * <b>Param</b> LeadTaskDto <br>
	 * <b>Return</b> LeadTask
	 * 
	 * @since 11-09-2023
	 * @version 1.0
	 */
	public static final Function<LeadTaskDto, Optional<LeadTask>> TO_LEAD_TASK = e -> evalMapper(e, LeadTask.class);
	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<LeadTaskDto>, List<LeadTask>> TO_LEAD_TASKS = e -> e.stream()
			.map(dm -> TO_LEAD_TASK.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert LeadTask Entity into optional LeadTaskDto .
	 * <b>This function will return null if passed LeadTask is null</b> <br>
	 * <b>Param</b> LeadTask <br>
	 * <b>Return</b> LeadTaskDto
	 * 
	 * @since 11-09-2023
	 * @Version 1.0
	 */
	public static final Function<LeadTask, Optional<LeadTaskDto>> TO_LEAD_TASK_DTO = e -> evalMapper(e,
			LeadTaskDto.class);

	/**
	 * @since 11-09-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<LeadTask>, List<LeadTaskDto>> TO_LEAD_TASK_DTOS = e -> e.stream()
			.map(dm -> TO_LEAD_TASK_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<LeadTask, Optional<GetLeadTaskDto>> TO_GET_LEAD_TASK_DTO = e -> evalMapper(e,
			GetLeadTaskDto.class);
}
