package ai.rnt.crm.dto.opportunity.mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.opportunity.GetOpportunityTaskDto;
import ai.rnt.crm.dto.opportunity.OpportunityTaskDto;
import ai.rnt.crm.entity.OpportunityTask;
import lombok.NoArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 19-02-2024
 * @version 1.2
 *
 */
@NoArgsConstructor(access = PRIVATE)
public class OpportunityTaskDtoMapper {

	/**
	 * This function will convert OpportunityTaskDto into optional OpportunityTask
	 * Entity. <b>This function will return null if passed OpportunityTaskDto is
	 * null</b> <br>
	 * <b>Param</b> OpportunityTaskDto <br>
	 * <b>Return</b> OpportunityTask
	 * 
	 * @since 19-02-2024
	 * @version 1.0
	 */
	public static final Function<OpportunityTaskDto, Optional<OpportunityTask>> TO_OPPORTUNITY_TASK = e -> evalMapper(e,
			OpportunityTask.class);
	/**
	 * @since 19-02-2024
	 * @version 1.2
	 *
	 */
	public static final Function<Collection<OpportunityTaskDto>, List<OpportunityTask>> TO_OPPORTUNITY_TASKS = e -> e
			.stream().map(dm -> TO_OPPORTUNITY_TASK.apply(dm).get()).collect(Collectors.toList());

	public static final Function<OpportunityTask, Optional<GetOpportunityTaskDto>> TO_GET_OPPORTUNITY_TASK_DTO = e -> evalMapper(
			e, GetOpportunityTaskDto.class);
}
