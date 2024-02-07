package ai.rnt.crm.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.MeetingAttachmentDaoService;
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

	@Override
	public Optional<MeetingAttachments> findById(Integer meetingAttchId) {
		return meetingAttachmentRepository.findById(meetingAttchId);
	}

	@Override
	public void removeExistingMeetingAttachment(MeetingAttachments data) {
		meetingAttachmentRepository.delete(data);
	}
}
