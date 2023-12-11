package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.ContactDto;
import ai.rnt.crm.entity.Contacts;

public interface ContactDaoService extends CrudService<Contacts, ContactDto>{
	
	public Contacts addContact(Contacts contact);

}
