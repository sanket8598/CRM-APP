package ai.rnt.crm.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.entity.AddEmail;
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
}
