package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.dto.ContactDto;

class QualifyOpportunityDtoTest {

	@Test
	void testObjectCreationAndGetSetData() {
		QualifyOpportunityDto dto = new QualifyOpportunityDto();
		dto.setOpportunityId(1);
		dto.setTopic("Topic");
		dto.setBudgetAmount("1000");
		dto.setAssignTo(123);
		dto.setProposedSolution("Solution");
		dto.setClosedOn(LocalDate.of(2024, 3, 7));
		dto.setUpdatedClosedOn(LocalDate.of(2024, 3, 7));
		dto.setLeadSourceId(456);
		dto.setProgressStatus("Status");
		dto.setCurrentPhase("Phase");
		dto.setPrimaryContact(new ContactDto());

		List<ContactDto> contacts = new ArrayList<>();
		contacts.add(new ContactDto());
		dto.setContacts(contacts);

		List<OpprtAttachmentDto> attachments = new ArrayList<>();
		attachments.add(new OpprtAttachmentDto());
		dto.setAttachments(attachments);

		List<ContactDto> clients = new ArrayList<>();
		clients.add(new ContactDto());
		dto.setClients(clients);
		assertEquals(1, dto.getOpportunityId());
		assertEquals("Topic", dto.getTopic());
		assertEquals("1000", dto.getBudgetAmount());
		assertEquals(123, dto.getAssignTo());
		assertEquals("Solution", dto.getProposedSolution());
		assertEquals(LocalDate.of(2024, 3, 7), dto.getClosedOn());
		assertEquals(LocalDate.of(2024, 3, 7), dto.getUpdatedClosedOn());
		assertEquals(456, dto.getLeadSourceId());
		assertEquals("Status", dto.getProgressStatus());
		assertEquals("Phase", dto.getCurrentPhase());
		assertNotNull(dto.getPrimaryContact());
		assertEquals(1, dto.getContacts().size());
		assertEquals(1, dto.getAttachments().size());
		assertEquals(1, dto.getClients().size());
	}

	@Test
	void testToString() {
		QualifyOpportunityDto dto = new QualifyOpportunityDto();
		assertNotNull(dto.toString());
	}

	@Test
	void testHashCode() {
		QualifyOpportunityDto dto1 = new QualifyOpportunityDto();
		QualifyOpportunityDto dto2 = new QualifyOpportunityDto();
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	void testEquals() {
		QualifyOpportunityDto dto1 = new QualifyOpportunityDto();
		QualifyOpportunityDto dto2 = new QualifyOpportunityDto();
		assertEquals(dto1, dto2);
	}

	@Test
	void testCanEquals() {
		QualifyOpportunityDto dto = new QualifyOpportunityDto();
		assertTrue(dto.canEqual(new QualifyOpportunityDto()));
	}
}
