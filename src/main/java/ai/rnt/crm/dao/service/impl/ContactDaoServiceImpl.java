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

@Service
@RequiredArgsConstructor
@Transactional
public class ContactDaoServiceImpl implements ContactDaoService {

	private final ContactRepository contactRepository;

	@Override
	@CacheEvict(value=LEADS,allEntries = true)
	public Contacts addContact(Contacts contact) {
		return contactRepository.save(contact);
	}

	@Override
	public List<Contacts> contactsOfLead(Integer leadId) {
		return contactRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<Contacts> findById(Integer id){
		return contactRepository.findById(id);
	}
	
	
}
