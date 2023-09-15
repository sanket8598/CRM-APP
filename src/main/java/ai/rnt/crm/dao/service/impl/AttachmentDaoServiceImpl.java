package ai.rnt.crm.dao.service.impl;
import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT_DTO;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.AttachmentDaoService;
import ai.rnt.crm.dto.AttachmentDto;
import ai.rnt.crm.entity.Attachment;
import ai.rnt.crm.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttachmentDaoServiceImpl implements AttachmentDaoService {
	
	private final AttachmentRepository attachmentRepository;

	@Override
	public Optional<AttachmentDto> save(Attachment entity) throws Exception {
		return TO_ATTACHMENT_DTO.apply(attachmentRepository.save(entity));
	}
	
	

}
