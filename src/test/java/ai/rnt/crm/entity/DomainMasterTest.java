package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class DomainMasterTest {

	DomainMaster domainMaster = new DomainMaster();

	@Test
	void getterTest() {
		domainMaster.getDomainId();
		domainMaster.getDomainName();
		domainMaster.getLeads();
		assertNull(domainMaster.getDomainId());
	}

	List<Leads> leads = new ArrayList<>();

	@Test
	void setterTest() {
		domainMaster.setDomainId(1);
		domainMaster.setDomainName("Java");
		domainMaster.setLeads(leads);
		assertEquals(1, domainMaster.getDomainId());
	}
}
