package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class OpportunityTest {

	@Test
	void testOpportunityObject() {
		Opportunity opportunity = new Opportunity();
		Leads leads = new Leads();
		leads.setLeadId(1);
		opportunity.setOpportunityId(1);
		opportunity.setStatus("Open");
		opportunity.setTopic("Sample Topic");
		opportunity.setPseudoName("Sample Pseudo Name");
		opportunity.setBudgetAmount("10000");
		leads.setCustomerNeed("Sample Customer Need");
		leads.setProposedSolution("Sample Proposed Solution");
		opportunity.setClosedOn(LocalDate.of(2024, 3, 1));
		opportunity.setLeads(leads);
		opportunity.setTechnicalNeed("Sample Technical Need");
		opportunity.setIntegrationPoint("Sample Integration Point");
		opportunity.setSecAndComp("Sample Security and Compliance");
		opportunity.setRiskMinigation("Sample Risk Minigation");
		opportunity.setInitialTimeline(LocalDate.of(2024, 3, 1));
		opportunity.setLicAndPricDetails("Sample License and Price Details");
		opportunity.setDevPlan("Sample Development Plan");
		opportunity.setPropAcceptCriteria("Sample Proposal Acceptance Criteria");
		opportunity.setPropExpDate(LocalDate.of(2024, 3, 15));
		opportunity.setTermsAndConditions("Sample Terms and Conditions");
		opportunity.setPresentation("Sample Presentation");
		opportunity.setScopeOfWork("Sample Scope of Work");
		opportunity.setProposition("Sample Proposition");
		opportunity.setLostReason("Sample Win-Lose Reason");
		opportunity.setPaymentTerms("Sample Payment Terms");
		opportunity.setCurrentPhase("Sample Current Phase");
		opportunity.setContract("Sample Contract");
		opportunity.setSupportPlan("Sample Support Plan");
		opportunity.setFinalBudget("20000");
		opportunity.setProgressStatus("In Progress");
		opportunity.setFinalCommAndTimeline(true);
		opportunity.setPresentProposal(true);
		opportunity.setComplInternalReview(true);
		opportunity.setDevelopProposal(true);
		opportunity.setIdentifySme(true);
		opportunity.setTimeline("This Quater");
		opportunity.setAnalysisRemarks("test2");
		opportunity.setProposeRemarks("test3");
		opportunity.setFinalisingTeam(true);
		opportunity.setSlaSigned(true);
		opportunity.setSowSigned(true);
		opportunity.setNdaSigned(true);
		opportunity.setProjectKickoff(true);
		opportunity.setOpportunityTasks(new ArrayList<>());
		opportunity.getOpportunityTasks();
		EmployeeMaster employee = new EmployeeMaster();
		employee.setStaffId(1);
		opportunity.setEmployee(employee);
		opportunity.setLeads(leads);
		OpprtAttachment attachment1 = new OpprtAttachment();
		attachment1.setOptAttchId(1);
		opportunity.getOprtAttachment().add(attachment1);
		OpprtAttachment attachment2 = new OpprtAttachment();
		attachment2.setOptAttchId(2);
		opportunity.getOprtAttachment().add(attachment2);
		OpportunityTask task1 = new OpportunityTask();
		task1.setOptyTaskId(1);
		opportunity.getOpportunityTasks().add(task1);
		OpportunityTask task2 = new OpportunityTask();
		task2.setOptyTaskId(2);
		opportunity.getOpportunityTasks().add(task2);
		assertEquals(1, opportunity.getOpportunityId());
		assertEquals("Open", opportunity.getStatus());
		assertEquals("Sample Topic", opportunity.getTopic());
		assertEquals("Sample Pseudo Name", opportunity.getPseudoName());
		assertEquals("10000", opportunity.getBudgetAmount());
		assertEquals("Sample Customer Need", leads.getCustomerNeed());
		assertEquals("Sample Proposed Solution", leads.getProposedSolution());
		assertEquals(true, opportunity.getFinalCommAndTimeline());
		assertEquals(true, opportunity.getPresentProposal());
		assertEquals(true, opportunity.getComplInternalReview());
		assertEquals(true, opportunity.getDevelopProposal());
		assertEquals(true, opportunity.getIdentifySme());
		assertEquals("This Quater", opportunity.getTimeline());
		assertEquals("test2", opportunity.getAnalysisRemarks());
		assertEquals("test3", opportunity.getProposeRemarks());
		assertEquals(true, opportunity.getProjectKickoff());
		assertEquals(true, opportunity.getFinalisingTeam());
		assertEquals(true, opportunity.getSlaSigned());
		assertEquals(true, opportunity.getSowSigned());
		assertEquals(true, opportunity.getNdaSigned());
		assertEquals(LocalDate.of(2024, 3, 1), opportunity.getClosedOn());
		assertEquals(employee, opportunity.getEmployee());
		assertEquals(leads, opportunity.getLeads());
		assertEquals(2, opportunity.getOprtAttachment().size());
		assertEquals(2, opportunity.getOpportunityTasks().size());
	}
}
