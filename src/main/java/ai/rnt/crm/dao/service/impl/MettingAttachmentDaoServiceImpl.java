package ai.rnt.crm.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.MettingAttachmentDaoService;
import ai.rnt.crm.entity.MeetingAttachments;
import ai.rnt.crm.repository.MettingAttachmentRepository;
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
public class MettingAttachmentDaoServiceImpl implements MettingAttachmentDaoService {

	private final MettingAttachmentRepository mettingAttachmentRepository;

	@Override
	public MeetingAttachments addMettingAttachment(MeetingAttachments meetingAttachments) {
		return mettingAttachmentRepository.save(meetingAttachments);
	}

}
