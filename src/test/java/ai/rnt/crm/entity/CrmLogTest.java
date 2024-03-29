package ai.rnt.crm.entity;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CrmLogTest {

	CrmLog crmLog = new CrmLog();

	@Test
	void getterTest() {
		crmLog.getCrmLogId();
		crmLog.getCrmContactId();
		crmLog.getCommType();
		crmLog.getToMessage();
		crmLog.getYourMessage();
		crmLog.getCallType();
		crmLog.getCallWith();
		crmLog.getNote();
		crmLog.getFromMail();
		crmLog.getToMail();
		crmLog.getCcMail();
		crmLog.getSubject();
		crmLog.getYourMail();
		crmLog.getNoteTitle();
		crmLog.getYourNote();
		assertNull(crmLog.getCrmLogId());
	}

	@Test
	void setterTest() {
		crmLog.setCrmLogId(1);
		crmLog.setCrmContactId(1);
		crmLog.setCommType("test");
		crmLog.setToMessage("msg");
		crmLog.setYourMessage("testmsg");
		crmLog.setCallType("phonecall");
		crmLog.setCallWith("client");
		crmLog.setNote("disscuss");
		crmLog.setFromMail("n.gasyh@rnt.ai");
		crmLog.setToMail("m.jwshqrnt@rnt.ai");
		crmLog.setCcMail("p.hgdshj@rnt.ai");
		crmLog.setSubject("test mail");
		crmLog.setYourMail("testdata");
		crmLog.setNoteTitle("note title");
		crmLog.setYourNote("my note");
		assertEquals(1, crmLog.getCrmLogId());
	}
}
