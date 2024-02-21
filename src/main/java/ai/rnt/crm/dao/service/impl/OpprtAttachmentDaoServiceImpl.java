package ai.rnt.crm.dao.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.OpprtAttachmentDaoService;
import ai.rnt.crm.entity.OpprtAttachment;
import ai.rnt.crm.repository.OpprtAttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OpprtAttachmentDaoServiceImpl implements OpprtAttachmentDaoService {

	private final OpprtAttachmentRepository opprtAttachmentRespoitory;

	@Override
	public Optional<OpprtAttachment> findById(Integer optAttchId) {
		log.info("inside the find opportunity attachment by id method...{}", optAttchId);
		return opprtAttachmentRespoitory.findById(optAttchId);
	}

	@Override
	public OpprtAttachment addOpprtAttachment(OpprtAttachment data) {
		log.info("inside the add opportunity attachment method...");
		return opprtAttachmentRespoitory.save(data);
	}
}
