package ai.rnt.crm.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.MeetingAttachmentDaoService;
import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.repository.MeetingAttachmentRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 25/11/2023
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MeetingAttachmentDaoServiceImpl implements MeetingAttachmentDaoService {

	private final MeetingAttachmentRepository meetingAttachmentRepository;

	@Override
	public MeetingAttachments addMeetingAttachment(MeetingAttachments meetingAttachments) {
		return meetingAttachmentRepository.save(meetingAttachments);
	}

}
