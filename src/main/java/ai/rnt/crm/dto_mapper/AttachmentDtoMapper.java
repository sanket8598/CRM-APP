package ai.rnt.crm.dto_mapper;

import static ai.rnt.crm.util.FunctionUtil.evalMapper;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.entity.Attachment;

public class AttachmentDtoMapper {

	
	/** 
	 * This function will convert AttachmentDto  into Attachment Entity.
	 * <b>This function will return null if passed EmployeeMaster is null</b>
	 * <br><b>Param</b> AttachmentDto 
	 * <br><b>Return</b> Attachment
	 * @since version 1.0 
	 */
	public static final Function<AttachmentDto, Optional<Attachment>> TO_ATTACHMENT = e -> evalMapper(e, Attachment.class);
	/**
	 * @since 21-08-2023
	 * @version 1.0
	 *
	 */
	public static final Function<Collection<AttachmentDto>, Collection<Attachment>> TO_ATTACHMENTS = e ->e.stream().map(dm -> TO_ATTACHMENT.apply(dm).get()).collect(Collectors.toList());
	
	public static final Function<Attachment, Optional<AttachmentDto>> TO_ATTACHMENT_DTO = e -> evalMapper(e, AttachmentDto.class);
}
