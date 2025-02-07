package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.Contacts;

public interface ContactDaoService extends CrudService<Contacts, ContactDto> {

	public Contacts addContact(Contacts contact);

	public List<Contacts> contactsOfLead(Integer leadId);

	public Optional<Contacts> findById(Integer contactId);

	public List<Contacts> findAllPrimaryContacts();

	public List<Contacts> findAllContacts();

	public List<Contacts> findByCompanyId(Integer companyId);

}
