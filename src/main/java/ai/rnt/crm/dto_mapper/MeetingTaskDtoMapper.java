package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.GetMeetingTaskDto;
import ai.rnt.crm.dto.MeetingTaskDto;
import ai.rnt.crm.entity.MeetingTask;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MeetingTaskDtoMapper {

	/**
	 * This function will convert MeetingTaskDto into optional MeetingTask Entity.
	 * <b>This function will return null if passed MettingDto is null</b> <br>
	 * <b>Param</b> MeetingTaskDto <br>
	 * <b>Return</b> MeetingTask
	 * 
	 * @author Nikhil Gaikwad
	 * @since 27-11-2023
	 * @version 1.0
	 */
	public static final Function<MeetingTaskDto, Optional<MeetingTask>> TO_MEETING_TASK = e -> evalMapper(e,
			MeetingTask.class);
	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<MeetingTaskDto>, List<MeetingTask>> TO_MEETINGS_TASK = e -> e.stream()
			.map(dm -> TO_MEETING_TASK.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert MeetingTask Entity into optional MeetingTaskDto .
	 * <b>This function will return null if passed MettingDto is null</b> <br>
	 * <b>Param</b> MeetingTask <br>
	 * <b>Return</b> MeetingTaskDto
	 * 
	 * @since 27-11-2023
	 * @Version 1.0
	 */
	public static final Function<MeetingTask, Optional<MeetingTaskDto>> TO_MEETING_TASK_DTO = e -> evalMapper(e,
			MeetingTaskDto.class);

	/**
	 * @since 27-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<MeetingTask>, List<MeetingTaskDto>> TO_MEETING_TASK_DTOS = e -> e.stream()
			.map(dm -> TO_MEETING_TASK_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<MeetingTask, Optional<GetMeetingTaskDto>> TO_GET_MEETING_TASK_DTO = e -> evalMapper(e,
			GetMeetingTaskDto.class);

}
