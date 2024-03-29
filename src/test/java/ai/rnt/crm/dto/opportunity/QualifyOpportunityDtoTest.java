package ai.rnt.crm.dto.opportunity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ContactDto;

class QualifyOpportunityDtoTest {

	@Test
	void testQualifyOpportunityDto() {
		QualifyOpportunityDto qualifyOpportunityDto = new QualifyOpportunityDto();
		qualifyOpportunityDto.setOpportunityId(1);
		qualifyOpportunityDto.setBudgetAmount("1000");
		qualifyOpportunityDto.setProgressStatus("InProgress");
		qualifyOpportunityDto.setCurrentPhase("Phase1");

		ContactDto primaryContact = new ContactDto();
		primaryContact.setName("Primary Contact");
		primaryContact.setPersonalEmail("primary@example.com");

		List<ContactDto> contacts = new ArrayList<>();
		ContactDto contact1 = new ContactDto();
		contact1.setName("Contact 1");
		contact1.setPersonalEmail("contact1@example.com");
		contacts.add(contact1);
		ContactDto contact2 = new ContactDto();
		contact2.setName("Contact 2");
		contact2.setPersonalEmail("contact2@example.com");
		contacts.add(contact2);

		List<OpprtAttachmentDto> attachments = new ArrayList<>();
		OpprtAttachmentDto attachment1 = new OpprtAttachmentDto();
		attachment1.setAttachName("Attachment1.txt");
		attachments.add(attachment1);

		List<ContactDto> clients = new ArrayList<>();
		ContactDto client1 = new ContactDto();
		client1.setName("Client 1");
		client1.setPersonalEmail("client1@example.com");
		clients.add(client1);
		ContactDto client2 = new ContactDto();
		client2.setName("Client 2");
		client2.setPersonalEmail("client2@example.com");
		clients.add(client2);

		Assertions.assertEquals(1, qualifyOpportunityDto.getOpportunityId());
		Assertions.assertEquals("1000", qualifyOpportunityDto.getBudgetAmount());
		Assertions.assertEquals("InProgress", qualifyOpportunityDto.getProgressStatus());
		Assertions.assertEquals("Phase1", qualifyOpportunityDto.getCurrentPhase());
	}
}
