package ai.rnt.crm.dto.opportunity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OpprtAttachmentDtoTest {

	@Test
	void testOpprtAttachmentDto() {
		OpprtAttachmentDto opprtAttachmentDto = new OpprtAttachmentDto();
		opprtAttachmentDto.setOptAttchId(1);
		opprtAttachmentDto.setAttachmentData("Test Data");
		opprtAttachmentDto.setAttachType("Type");
		opprtAttachmentDto.setAttachName("Name");
		opprtAttachmentDto.setAttachmentOf("Opportunity");
		assertEquals(1, opprtAttachmentDto.getOptAttchId());
		assertEquals("Test Data", opprtAttachmentDto.getAttachmentData());
		assertEquals("Type", opprtAttachmentDto.getAttachType());
		assertEquals("Name", opprtAttachmentDto.getAttachName());
		assertEquals("Opportunity", opprtAttachmentDto.getAttachmentOf());
	}
}
