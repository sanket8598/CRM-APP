package ai.rnt.crm.dao.service;

import java.util.Optional;

import ai.rnt.crm.dto.MeetingAttachmentsDto;
import ai.rnt.crm.entity.MeetingAttachments;

public interface MeetingAttachmentDaoService extends CrudService<MeetingAttachments, MeetingAttachmentsDto> {

	MeetingAttachments addMeetingAttachment(MeetingAttachments meetingAttachments);

	public Optional<MeetingAttachments> findById(Integer meetingAttchId);

	void removeExistingMeetingAttachment(MeetingAttachments data);

}
