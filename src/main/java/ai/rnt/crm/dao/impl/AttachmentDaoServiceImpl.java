package ai.rnt.crm.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.AttachmentDaoService;
import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachmentDaoServiceImpl implements AttachmentDaoService {

	private final AttachmentRepository attachmentRepository;

	@Override
	public Attachment addAttachment(Attachment entity) {
		return attachmentRepository.save(entity);
	}

	@Override
	public Optional<Attachment> findById(Integer emailAttchId) {
		return attachmentRepository.findById(emailAttchId);
	}
}
