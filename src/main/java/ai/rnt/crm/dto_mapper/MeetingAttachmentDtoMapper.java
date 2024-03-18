package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;
import static lombok.AccessLevel.PRIVATE;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.MeetingAttachmentsDto;
import ai.rnt.crm.entity.MeetingAttachments;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class MeetingAttachmentDtoMapper {

	/**
	 * This function will convert MettingAttachmentDto into MettingAttachments
	 * Entity. <b>This function will return null if passed MettingAttachmentDto is
	 * null</b> <br>
	 * <b>Param</b> MettingAttachmentDto <br>
	 * <b>Return</b> MettingAttachments
	 * 
	 * @author Nikhil Gaikwad.
	 * @since version 1.0
	 */
	public static final Function<MeetingAttachmentsDto, Optional<MeetingAttachments>> TO_METTING_ATTACHMENT = e -> evalMapper(
			e, MeetingAttachments.class);
	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<MeetingAttachmentsDto>, Collection<MeetingAttachments>> TO_METTING_ATTACHMENTS = e -> e
			.stream().map(dm -> TO_METTING_ATTACHMENT.apply(dm).get()).collect(Collectors.toList());

	public static final Function<MeetingAttachments, Optional<MeetingAttachmentsDto>> TO_METTING_ATTACHMENT_DTO = e -> evalMapper(
			e, MeetingAttachmentsDto.class);
	public static final Function<Collection<MeetingAttachments>, List<MeetingAttachmentsDto>> TO_METTING_ATTACHMENT_DTOS = e -> e
			.stream().map(dm -> TO_METTING_ATTACHMENT_DTO.apply(dm).get()).collect(Collectors.toList());
}
