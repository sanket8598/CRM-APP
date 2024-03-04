package ai.rnt.crm.entity;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class LeadSourceMasterTest {

	LeadSourceMaster leadSourceMaster = new LeadSourceMaster();

	@Test
	void getterTest() {
		leadSourceMaster.getLeadSourceId();
		leadSourceMaster.getSourceName();
		leadSourceMaster.getLeads();
	}

	List<Leads> leads = new ArrayList<>();

	@Test
	void setterTest() {
		leadSourceMaster.setLeadSourceId(1);
		leadSourceMaster.setSourceName("Online");
		leadSourceMaster.setLeads(leads);
	}
}
