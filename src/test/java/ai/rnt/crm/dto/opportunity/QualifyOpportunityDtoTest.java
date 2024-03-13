package ai.rnt.crm.dto.opportunity;

import java.time.LocalDate;
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
		qualifyOpportunityDto.setTopic("Sample Topic");
		qualifyOpportunityDto.setBudgetAmount("1000");
		qualifyOpportunityDto.setAssignTo(101);
		qualifyOpportunityDto.setProposedSolution("Sample Solution");
		qualifyOpportunityDto.setClosedOn(LocalDate.of(2024, 3, 13));
		qualifyOpportunityDto.setUpdatedClosedOn(LocalDate.of(2024, 3, 13));
		qualifyOpportunityDto.setLeadSourceId(201);
		qualifyOpportunityDto.setProgressStatus("InProgress");
		qualifyOpportunityDto.setCurrentPhase("Phase1");

		ContactDto primaryContact = new ContactDto();
		primaryContact.setName("Primary Contact");
		primaryContact.setPersonalEmail("primary@example.com");
		qualifyOpportunityDto.setPrimaryContact(primaryContact);

		List<ContactDto> contacts = new ArrayList<>();
		ContactDto contact1 = new ContactDto();
		contact1.setName("Contact 1");
		contact1.setPersonalEmail("contact1@example.com");
		contacts.add(contact1);
		ContactDto contact2 = new ContactDto();
		contact2.setName("Contact 2");
		contact2.setPersonalEmail("contact2@example.com");
		contacts.add(contact2);
		qualifyOpportunityDto.setContacts(contacts);

		List<OpprtAttachmentDto> attachments = new ArrayList<>();
		OpprtAttachmentDto attachment1 = new OpprtAttachmentDto();
		attachment1.setAttachName("Attachment1.txt");
		attachments.add(attachment1);
		qualifyOpportunityDto.setAttachments(attachments);

		List<ContactDto> clients = new ArrayList<>();
		ContactDto client1 = new ContactDto();
		client1.setName("Client 1");
		client1.setPersonalEmail("client1@example.com");
		clients.add(client1);
		ContactDto client2 = new ContactDto();
		client2.setName("Client 2");
		client2.setPersonalEmail("client2@example.com");
		clients.add(client2);
		qualifyOpportunityDto.setClients(clients);

		// Perform assertions
		Assertions.assertEquals(1, qualifyOpportunityDto.getOpportunityId());
		Assertions.assertEquals("Sample Topic", qualifyOpportunityDto.getTopic());
		Assertions.assertEquals("1000", qualifyOpportunityDto.getBudgetAmount());
		Assertions.assertEquals(101, qualifyOpportunityDto.getAssignTo());
		Assertions.assertEquals("Sample Solution", qualifyOpportunityDto.getProposedSolution());
		Assertions.assertEquals(LocalDate.of(2024, 3, 13), qualifyOpportunityDto.getClosedOn());
		Assertions.assertEquals(LocalDate.of(2024, 3, 13), qualifyOpportunityDto.getUpdatedClosedOn());
		Assertions.assertEquals(201, qualifyOpportunityDto.getLeadSourceId());
		Assertions.assertEquals("InProgress", qualifyOpportunityDto.getProgressStatus());
		Assertions.assertEquals("Phase1", qualifyOpportunityDto.getCurrentPhase());

		Assertions.assertEquals("Primary Contact", qualifyOpportunityDto.getPrimaryContact().getName());
		Assertions.assertEquals("primary@example.com", qualifyOpportunityDto.getPrimaryContact().getPersonalEmail());

		Assertions.assertEquals(2, qualifyOpportunityDto.getContacts().size());
		Assertions.assertEquals("Contact 1", qualifyOpportunityDto.getContacts().get(0).getName());
		Assertions.assertEquals("contact1@example.com", qualifyOpportunityDto.getContacts().get(0).getPersonalEmail());
		Assertions.assertEquals("Contact 2", qualifyOpportunityDto.getContacts().get(1).getName());
		Assertions.assertEquals("contact2@example.com", qualifyOpportunityDto.getContacts().get(1).getPersonalEmail());

		Assertions.assertEquals(1, qualifyOpportunityDto.getAttachments().size());
		Assertions.assertEquals("Attachment1.txt", qualifyOpportunityDto.getAttachments().get(0).getAttachName());

		Assertions.assertEquals(2, qualifyOpportunityDto.getClients().size());
		Assertions.assertEquals("Client 1", qualifyOpportunityDto.getClients().get(0).getName());
		Assertions.assertEquals("client1@example.com", qualifyOpportunityDto.getClients().get(0).getPersonalEmail());
		Assertions.assertEquals("Client 2", qualifyOpportunityDto.getClients().get(1).getName());
		Assertions.assertEquals("client2@example.com", qualifyOpportunityDto.getClients().get(1).getPersonalEmail());
	}
}
