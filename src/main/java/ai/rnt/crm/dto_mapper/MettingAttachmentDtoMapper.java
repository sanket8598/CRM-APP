package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.MettingAttachmentsDto;
import ai.rnt.crm.entity.MettingAttachments;

public class MettingAttachmentDtoMapper {

	private MettingAttachmentDtoMapper() {

	}

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
	public static final Function<MettingAttachmentsDto, Optional<MettingAttachments>> TO_METTING_ATTACHMENT = e -> evalMapper(
			e, MettingAttachments.class);
	/**
	 * @since 25-11-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<MettingAttachmentsDto>, Collection<MettingAttachments>> TO_METTING_ATTACHMENTS = e -> e
			.stream().map(dm -> TO_METTING_ATTACHMENT.apply(dm).get()).collect(Collectors.toList());

	public static final Function<MettingAttachments, Optional<MettingAttachmentsDto>> TO_METTING_ATTACHMENT_DTO = e -> evalMapper(
			e, MettingAttachmentsDto.class);
	public static final Function<Collection<MettingAttachments>, List<MettingAttachmentsDto>> TO_METTING_ATTACHMENT_DTOS = e -> e
			.stream().map(dm -> TO_METTING_ATTACHMENT_DTO.apply(dm).get()).collect(Collectors.toList());
}
