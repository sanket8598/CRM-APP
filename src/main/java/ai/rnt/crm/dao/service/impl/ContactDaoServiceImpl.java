package ai.rnt.crm.dao.service.impl;


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
	public Contacts addContact(Contacts contact) {
		return contactRepository.save(contact);
	}
}
