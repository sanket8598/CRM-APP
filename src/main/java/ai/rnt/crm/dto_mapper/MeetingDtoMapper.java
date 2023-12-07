package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ai.rnt.crm.dto.GetMeetingDto;
import ai.rnt.crm.dto.MeetingDto;
import ai.rnt.crm.entity.Meetings;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MeetingDtoMapper {

	/**
	 * This function will convert MettingDto into optional Mettings Entity. <b>This
	 * function will return null if passed MettingDto is null</b> <br>
	 * <b>Param</b> MettingDto <br>
	 * <b>Return</b> Metting
	 * 
	 * @author Nikhil Gaikwad
	 * @since 25-11-2023
	 * @version 1.0
	 */
	public static final Function<MeetingDto, Optional<Meetings>> TO_MEETING = e -> evalMapper(e, Meetings.class);
	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<MeetingDto>, List<Meetings>> TO_MEETINGS = e -> e.stream()
			.map(dm -> TO_MEETING.apply(dm).get()).collect(Collectors.toList());

	/**
	 * This function will convert Mettings Entity into optional MettingDto . <b>This
	 * function will return null if passed MettingDto is null</b> <br>
	 * <b>Param</b> Mettings <br>
	 * <b>Return</b> MettingDto
	 * 
	 * @since 25-11-2023
	 * @Version 1.0
	 */
	public static final Function<Meetings, Optional<MeetingDto>> TO_MEETING_DTO = e -> evalMapper(e, MeetingDto.class);

	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<Meetings>, List<MeetingDto>> TO_MEETING_DTOS = e -> e.stream()
			.map(dm -> TO_MEETING_DTO.apply(dm).get()).collect(Collectors.toList());

	public static final Function<Meetings, Optional<GetMeetingDto>> TO_GET_MEETING_DTO = e -> {
		Optional<GetMeetingDto> getMeetingDto = evalMapper(e, GetMeetingDto.class);
		getMeetingDto.ifPresent(l -> l.setParticipants(nonNull(e.getParticipates()) && !e.getParticipates().isEmpty()
				? Stream.of(e.getParticipates().split(",")).map(String::trim).collect(toList())
				: emptyList()));
		return getMeetingDto;
	};
}
