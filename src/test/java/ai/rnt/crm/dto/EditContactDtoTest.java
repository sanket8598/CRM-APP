package ai.rnt.crm.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EditContactDtoTest {

	@Test
	void testGetShortNameWithPrimaryContact() {
		ContactDto primaryContact = new ContactDto();
		primaryContact.setFirstName("John");
		primaryContact.setLastName("Doe");

		EditContactDto editContactDto = new EditContactDto();
		editContactDto.setPrimaryContact(primaryContact);
		String shortName = editContactDto.getShortName();
		assertEquals("JD", shortName);
	}

	@Test
	void testGetShortNameWithoutPrimaryContact() {
		EditContactDto editContactDto = new EditContactDto();
		String shortName = editContactDto.getShortName();
		assertNull(shortName);
	}
}
