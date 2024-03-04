package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class LeadsTest {

	Leads leads = new Leads();

	@Test
	void getterTest() {
		leads.getLeadId();
		leads.getTopic();
		leads.getStatus();
		leads.getEmployee();
		leads.getBudgetAmount();
		leads.getCustomerNeed();
		leads.getProposedSolution();
		leads.getDisqualifyAs();
		leads.getDisqualifyReason();
		leads.getPseudoName();
		leads.getIsFollowUpRemainder();
		leads.getRemainderVia();
		leads.getRemainderDueAt();
		leads.getRemainderDueOn();
		leads.getLeadSourceMaster();
		leads.getServiceFallsMaster();
		leads.getDomainMaster();
		leads.getContacts();
		leads.getEmails();
		leads.getCalls();
		leads.getVisit();
		leads.getImpLead();
		leads.getImportant();
		leads.getLeadTasks();
		leads.getOpportunity();
	}

	EmployeeMaster employee = new EmployeeMaster();
	LeadSourceMaster leadSourceMaster = new LeadSourceMaster();
	ServiceFallsMaster serviceFallsMaster = new ServiceFallsMaster();
	DomainMaster domainMaster = new DomainMaster();
	List<Contacts> contacts = new ArrayList<>();
	List<Email> emails = new ArrayList<>();
	List<Call> calls = new ArrayList<>();
	List<Visit> visit = new ArrayList<>();
	Set<LeadImportant> impLead;
	List<LeadTask> leadTasks = new ArrayList<>();
	Opportunity opportunity = new Opportunity();

	@Test
	void setterTest() {
		leads.setLeadId(1);
		leads.setTopic("test");
		leads.setStatus("Open");
		leads.setEmployee(employee);
		leads.setBudgetAmount("2,3256");
		leads.setCustomerNeed("test");
		leads.setProposedSolution("data");
		leads.setDisqualifyAs("close");
		leads.setDisqualifyReason("not connect");
		leads.setPseudoName("sanket");
		leads.setIsFollowUpRemainder(true);
		leads.setRemainderVia("Email");
		leads.setRemainderDueAt("10:10");
		LocalDate localDate = LocalDate.of(2024, 1, 1);
		leads.setRemainderDueOn(localDate);
		leads.setLeadSourceMaster(leadSourceMaster);
		leads.setServiceFallsMaster(serviceFallsMaster);
		leads.setDomainMaster(domainMaster);
		leads.setContacts(contacts);
		leads.setEmails(emails);
		leads.setCalls(calls);
		leads.setVisit(visit);
		leads.setImpLead(impLead);
		leads.setImportant(false);
		leads.setLeadTasks(leadTasks);
		leads.setOpportunity(opportunity);
	}

	@Test
	void testGetRemainderDueAt12Hours() throws ParseException {
		leads.setRemainderDueAt("17:30:00");
		assertEquals("05:30 PM", leads.getRemainderDueAt12Hours());
		leads.setRemainderDueAt(null);
		assertNull(leads.getRemainderDueAt12Hours());
		leads.setRemainderDueAt("invalid_time_format");
		assertEquals("invalid_time_format", leads.getRemainderDueAt12Hours());
		leads.setRemainderDueAt("05:30 PM");
		assertEquals("05:30 AM", leads.getRemainderDueAt12Hours());
	}
}
