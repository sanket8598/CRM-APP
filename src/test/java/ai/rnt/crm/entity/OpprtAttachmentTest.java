package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OpprtAttachmentTest {
	@Test
	void testOpprtAttachmentObject() {
		OpprtAttachment attachment = new OpprtAttachment();
		attachment.setOptAttchId(1);
		attachment.setAttachmentData("dxcfvjhkxhbjefdchs");
		attachment.setAttachType("pdf");
		attachment.setAttachName("myData");
		attachment.setAttachmentOf("Qualify");
		Opportunity opportunity = new Opportunity();
		opportunity.setOpportunityId(1);
		attachment.setOpportunity(opportunity);
		assertEquals(1, attachment.getOptAttchId());
		assertEquals("dxcfvjhkxhbjefdchs", attachment.getAttachmentData());
		assertEquals("pdf", attachment.getAttachType());
		assertEquals("myData", attachment.getAttachName());
		assertEquals("Qualify", attachment.getAttachmentOf());
		assertEquals(opportunity, attachment.getOpportunity());
	}
}
