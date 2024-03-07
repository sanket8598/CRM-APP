package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class OpprtAttachmentDtoTest {

	@Test
	void testObjectCreation() {
		OpprtAttachmentDto dto = new OpprtAttachmentDto();
		assertNotNull(dto);
	}

	@Test
	void testSetterGetter() {
		OpprtAttachmentDto dto = new OpprtAttachmentDto();
		dto.setOptAttchId(1);
		dto.setAttachmentData("Sample data");
		dto.setAttachType("Type");
		dto.setAttachName("Name");
		dto.setAttachmentOf("Attachment of");

		assertEquals(1, dto.getOptAttchId());
		assertEquals("Sample data", dto.getAttachmentData());
		assertEquals("Type", dto.getAttachType());
		assertEquals("Name", dto.getAttachName());
		assertEquals("Attachment of", dto.getAttachmentOf());
	}

	@Test
	void testHashCode() {
		OpprtAttachmentDto dto1 = new OpprtAttachmentDto();
		OpprtAttachmentDto dto2 = new OpprtAttachmentDto();
		assertNotNull(dto1.toString());
		assertEquals(dto1.hashCode(), dto2.hashCode());
	}

	@Test
	void testEquals() {
		OpprtAttachmentDto dto1 = new OpprtAttachmentDto();
		OpprtAttachmentDto dto2 = new OpprtAttachmentDto();
		assertEquals(dto1, dto2);
	}

	@Test
	void testCanEquals() {
		OpprtAttachmentDto dto1 = new OpprtAttachmentDto();
		OpprtAttachmentDto dto2 = new OpprtAttachmentDto();
		assertTrue(dto1.canEqual(dto2));
	}

}
