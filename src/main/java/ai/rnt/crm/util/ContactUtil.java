package ai.rnt.crm.util;

import java.util.List;
import java.util.Objects;

import ai.rnt.crm.entity.Contacts;

public class ContactUtil {
	
	public static boolean isDuplicateContact(List<Contacts> existingContacts,Contacts contact) {
		return existingContacts.stream().filter(Objects::nonNull).anyMatch(cont -> Objects.equals(cont.getFirstName(),contact.getFirstName())
				&& Objects.equals(cont.getLastName(),contact.getLastName())
				&& Objects.equals(cont.getDesignation(),contact.getDesignation())
				&& Objects.equals(cont.getCompanyMaster().getCompanyId(),contact.getCompanyMaster().getCompanyId())
				&& Objects.equals(cont.getLead().getLeadId(),contact.getLead().getLeadId()));
	}

}
