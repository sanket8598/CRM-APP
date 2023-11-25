package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.MettingAttachmentsDto;
import ai.rnt.crm.entity.MettingAttachments;

public interface MettingAttachmentDaoService extends CrudService<MettingAttachments, MettingAttachmentsDto> {

	MettingAttachments addMettingAttachment(MettingAttachments mettingAttachments);

}
