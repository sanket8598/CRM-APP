package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.entity.AddEmail;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.repository.EmailRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EmailDaoServiceImpl implements EmailDaoService {

	private final EmailRepository emailRepository;

	@Override
	public AddEmail addEmail(AddEmail addEmail) {
		return emailRepository.save(addEmail);
	}

	@Override
	public List<AddEmail> getEmailByLeadId(Integer leadId) {
		return emailRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public AddEmail findById(Integer addMailId) {
		return emailRepository.findById(addMailId)
				.orElseThrow(() -> new ResourceNotFoundException("AddEmail", "addMailId", addMailId));
	}

	@Override
	public Boolean emailPresentForLeadLeadId(Integer addMailId, Integer leadId) {
		return emailRepository.existsByAddMailIdAndLeadLeadId(addMailId, leadId);
	}
}
