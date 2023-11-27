package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.MettingAttachmentsDto;
import ai.rnt.crm.entity.MeetingAttachments;

public interface MettingAttachmentDaoService extends CrudService<MeetingAttachments, MettingAttachmentsDto> {

	MeetingAttachments addMettingAttachment(MeetingAttachments meetingAttachments);

}
