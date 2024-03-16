package ai.rnt.crm.functional.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Opportunity;

class OpportunityPredicatesTest {

	@Test
    void testInPipelineOpportunities() {
        Opportunity opportunity1 = new Opportunity();
        opportunity1.setStatus("QUALIFY");
        assertTrue(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity1),
                "Predicate should return true for opportunity with status QUALIFY.");

        Opportunity opportunity2 = new Opportunity();
        opportunity2.setStatus("ANALYSIS");
        assertTrue(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity2),
                "Predicate should return true for opportunity with status ANALYSIS.");

        Opportunity opportunity3 = new Opportunity();
        opportunity3.setStatus("PROPOSE");
        assertTrue(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity3),
                "Predicate should return true for opportunity with status PROPOSE.");

        Opportunity opportunity4 = new Opportunity();
        opportunity4.setStatus("CLOSE");
        assertTrue(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity4),
                "Predicate should return true for opportunity with status CLOSE.");

        Opportunity opportunity5 = new Opportunity();
        opportunity5.setStatus("WON");
        assertFalse(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity5),
                "Predicate should return false for opportunity with status WON.");

        Opportunity opportunity6 = new Opportunity();
        opportunity6.setStatus("LOST");
        assertFalse(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity6),
                "Predicate should return false for opportunity with status LOST.");
    }
	@Test
    void testInPipelineWithNullStatusOpportunities() {
		Opportunity opportunity = new Opportunity();
        assertFalse(OpportunityPredicates.IN_PIPELINE_OPPORTUNITIES.test(opportunity),
                "Predicate should return false for opportunity with status LOST.");
	}
    @Test
    void testWonOpportunities() {
        Opportunity opportunity = new Opportunity();
        opportunity.setStatus("Won");
        Opportunity opportunity1 = new Opportunity();
        assertTrue(OpportunityPredicates.WON_OPPORTUNITIES.test(opportunity),
                "Predicate should return true for opportunity with status WON.");
        assertFalse(OpportunityPredicates.WON_OPPORTUNITIES.test(opportunity1),
        		"Predicate should return false for opportunity with status null.");
    }

    @Test
    void testLossOpportunities() {
        Opportunity opportunity = new Opportunity();
        opportunity.setStatus("Lost");
        assertTrue(OpportunityPredicates.LOSS_OPPORTUNITIES.test(opportunity),
                "Predicate should return true for opportunity with status LOST.");
    }
    @Test
    void testNullOpportunities() {
    	Opportunity opportunity = new Opportunity();
    	assertFalse(OpportunityPredicates.LOSS_OPPORTUNITIES.test(opportunity),
    			"Predicate should return true for opportunity with status null.");
    }
    
    @Test
    void testAssignedOpportunities() {
    	EmployeeMaster employeeMaster = new EmployeeMaster();
    	employeeMaster.setStaffId(1);
        Opportunity opportunity1 = new Opportunity();
        opportunity1.setEmployee(employeeMaster); 
        opportunity1.setCreatedBy(1); 
        int loggedInStaffId = 1; 
        assertTrue(OpportunityPredicates.ASSIGNED_OPPORTUNITIES.test(opportunity1, loggedInStaffId),
                "Predicate should return true for opportunity assigned to the logged-in staff.");

        employeeMaster.setStaffId(2);
        Opportunity opportunity2 = new Opportunity();
        opportunity2.setEmployee(employeeMaster); 
        opportunity2.setCreatedBy(2); 
        assertFalse(OpportunityPredicates.ASSIGNED_OPPORTUNITIES.test(opportunity2, loggedInStaffId),
                "Predicate should return false for opportunity not assigned to the logged-in staff.");
        employeeMaster.setStaffId(1);
        Opportunity opportunity3 = new Opportunity();
        opportunity3.setEmployee(employeeMaster); 
        opportunity3.setCreatedBy(2); 
        assertTrue(OpportunityPredicates.ASSIGNED_OPPORTUNITIES.test(opportunity3, loggedInStaffId),
                "Predicate should return true for opportunity created by the logged-in staff.");
        employeeMaster.setStaffId(2);
        Opportunity opportunity4 = new Opportunity();
        opportunity4.setEmployee(employeeMaster); 
        opportunity4.setCreatedBy(1); 
        assertTrue(OpportunityPredicates.ASSIGNED_OPPORTUNITIES.test(opportunity4, loggedInStaffId),
                "Predicate should return true for opportunity assigned to the logged-in staff or created by them.");
    }

}
