package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.LEADS;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ContactDaoServiceImpl implements ContactDaoService {

	private final ContactRepository contactRepository;

	@Override
	@CacheEvict(value = LEADS, allEntries = true)
	public Contacts addContact(Contacts contact) {
		log.info("inside the addContact method...");
		return contactRepository.save(contact);
	}

	@Override
	public List<Contacts> contactsOfLead(Integer leadId) {
		log.info("inside the contactsOfLead method...{}", leadId);
		return contactRepository.findByLeadLeadIdOrderByCreatedDate(leadId);
	}

	@Override
	public Optional<Contacts> findById(Integer id) {
		log.info("inside the findById method...{}", id);
		return contactRepository.findById(id);
	}

	@Override
	public List<Contacts> findAllPrimaryContacts() {
		log.info("inside the findAllPrimaryContacts method...");
		return contactRepository.findByPrimaryTrue();
	}
}
