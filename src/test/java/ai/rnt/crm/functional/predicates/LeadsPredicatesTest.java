package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Visit;

class LeadsPredicatesTest {

	@Test
	void activeVisit_withNullStatus_shouldReturnTrue() {
		Visit mockVisit = mock(Visit.class);
		when(mockVisit.getStatus()).thenReturn(null);
		assertTrue(LeadsPredicates.ACTIVE_VISIT.test(mockVisit), "Visit with null status should be considered active.");
		Meetings mockMeetings = mock(Meetings.class);
		when(mockMeetings.getMeetingStatus()).thenReturn(null);
		assertTrue(LeadsPredicates.ACTIVE_MEETING.test(mockMeetings),
				"Meeting with null status should be considered active.");
		Call mockCall = mock(Call.class);
		when(mockCall.getStatus()).thenReturn(null);
		assertTrue(LeadsPredicates.ACTIVE_CALL.test(mockCall), "Call with null status should be considered active.");
	}

	@Test
	void activeVisit_withSaveStatus_shouldReturnTrue() {
		Visit mockVisit = mock(Visit.class);
		when(mockVisit.getStatus()).thenReturn(SAVE);
		assertTrue(LeadsPredicates.ACTIVE_VISIT.test(mockVisit),
				"Visit with 'save' status should be considered active.");
		Meetings mockMeetings = mock(Meetings.class);
		when(mockMeetings.getMeetingStatus()).thenReturn(SAVE);
		assertTrue(LeadsPredicates.ACTIVE_MEETING.test(mockMeetings),
				"Meeting with 'save' status should be considered active.");
		Call mockCall = mock(Call.class);
		when(mockCall.getStatus()).thenReturn(SAVE);
		assertTrue(LeadsPredicates.ACTIVE_CALL.test(mockCall), "Call with 'save' status should be considered active.");
	}

	@Test
	@DisplayName("Active visit should return false when status is any value other than 'SAVE'")
	void activeVisitShouldReturnFalseForOtherStatuses() {
		Visit mockVisit = mock(Visit.class);
		when(mockVisit.getStatus()).thenReturn(COMPLETE);
		assertFalse(LeadsPredicates.ACTIVE_VISIT.test(mockVisit),
				"ACTIVE_VISIT should return false when the visit status is not 'save' or null.");
		Meetings mockMeetings = mock(Meetings.class);
		when(mockMeetings.getMeetingStatus()).thenReturn(COMPLETE);
		assertFalse(LeadsPredicates.ACTIVE_MEETING.test(mockMeetings),
				"Meeting should return false when the meeting status is not 'save' or null.");
		Call mockCall = mock(Call.class);
		when(mockCall.getStatus()).thenReturn(COMPLETE);
		assertFalse(LeadsPredicates.ACTIVE_CALL.test(mockCall),
				"Call should return false when the call status is not 'save' or null.");
	}

	@Test
	@DisplayName("Should return false for null status or status not equal to CLOSE_AS_DISQUALIFIED")
	void testDisqualifiedLeadFilterForInvalidOrMissingStatus() {
		Leads mockLeadsWithNullStatus = mock(Leads.class);
		when(mockLeadsWithNullStatus.getStatus()).thenReturn(null);
		assertFalse(LeadsPredicates.DISQUALIFIED_LEAD_FILTER.test(mockLeadsWithNullStatus),
				"Predicate should return false for null status.");

		Leads mockLeadsWithDifferentStatus = mock(Leads.class);
		when(mockLeadsWithDifferentStatus.getStatus()).thenReturn("OPEN");
		assertFalse(LeadsPredicates.DISQUALIFIED_LEAD_FILTER.test(mockLeadsWithDifferentStatus),
				"Predicate should return false for a status not equal to CLOSE_AS_DISQUALIFIED.");
	}

	@Test
	@DisplayName("Should return true for CLOSE_AS_DISQUALIFIED status and valid disqualifyAs values")
	void testDisqualifiedLeadFilterForValidScenarios() {
		String[] validDisqualifyAsValues = { "Lost", "Non-Contactable", "No Longer Interested", "Canceled",
				"Non-Workable" };

		for (String value : validDisqualifyAsValues) {
			Leads mockLeads = mock(Leads.class);
			when(mockLeads.getStatus()).thenReturn("CloseAsDisqualified");
			when(mockLeads.getDisqualifyAs()).thenReturn(value);

			assertTrue(LeadsPredicates.DISQUALIFIED_LEAD_FILTER.test(mockLeads),
					"Predicate should return true for status CLOSE_AS_DISQUALIFIED and disqualifyAs " + value + ".");
		}
	}

	@Test
	@DisplayName("Should return false for CLOSE_AS_DISQUALIFIED status but invalid disqualifyAs value")
	void testDisqualifiedLeadFilterForInvalidDisqualifyAsValue() {
		Leads mockLeads = mock(Leads.class);
		when(mockLeads.getStatus()).thenReturn("CloseAsDisqualified");
		when(mockLeads.getDisqualifyAs()).thenReturn("INTERESTED"); // An invalid disqualifyAs value

		assertFalse(LeadsPredicates.DISQUALIFIED_LEAD_FILTER.test(mockLeads),
				"Predicate should return false for status CLOSE_AS_DISQUALIFIED and an invalid disqualifyAs value.");
	}

	@Test
	@DisplayName("Should return false for null status or status not equal to CLOSE_AS_QUALIFIED")
	void testQualifiedLeadFilterForInvalidOrMissingStatus() {
		Leads mockLeadsWithNullStatus = mock(Leads.class);
		when(mockLeadsWithNullStatus.getStatus()).thenReturn(null);
		assertFalse(LeadsPredicates.QUALIFIED_LEAD_FILTER.test(mockLeadsWithNullStatus),
				"Predicate should return false for null status.");

		Leads mockLeadsWithDifferentStatus = mock(Leads.class);
		when(mockLeadsWithDifferentStatus.getStatus()).thenReturn("OPEN");
		assertFalse(LeadsPredicates.QUALIFIED_LEAD_FILTER.test(mockLeadsWithDifferentStatus),
				"Predicate should return false for a status not equal to CLOSE_AS_QUALIFIED.");
	}

	@Test
	@DisplayName("Should return true for CLOSE_AS_QUALIFIED status and valid disqualifyAs values")
	void testQualifiedLeadFilterForValidScenarios() {
		String[] validDisqualifyAsValues = { "Qualified", "Follow-Up" };

		for (String value : validDisqualifyAsValues) {
			Leads mockLeads = mock(Leads.class);
			when(mockLeads.getStatus()).thenReturn("CloseAsQualified");
			when(mockLeads.getDisqualifyAs()).thenReturn(value);

			assertTrue(LeadsPredicates.QUALIFIED_LEAD_FILTER.test(mockLeads),
					"Predicate should return true for status CLOSE_AS_QUALIFIED and disqualifyAs " + value + ".");
		}
	}

	@Test
	@DisplayName("Should return false for CLOSE_AS_QUALIFIED status but invalid disqualifyAs value")
	void testQualifiedLeadFilterForInvalidDisqualifyAsValue() {
		Leads mockLeads = mock(Leads.class);
		when(mockLeads.getStatus()).thenReturn("CloseAsQualified");
		when(mockLeads.getDisqualifyAs()).thenReturn("INTERESTED"); // An invalid disqualifyAs value

		assertFalse(LeadsPredicates.QUALIFIED_LEAD_FILTER.test(mockLeads),
				"Predicate should return false for status CLOSE_AS_QUALIFIED and an invalid disqualifyAs value.");
	}

	@Test
	@DisplayName("Should return false for null status")
	void testOpenLeadFilterForNullStatus() {
		Leads mockLeadsWithNullStatus = mock(Leads.class);
		when(mockLeadsWithNullStatus.getStatus()).thenReturn(null);

		assertFalse(LeadsPredicates.OPEN_LEAD_FILTER.test(mockLeadsWithNullStatus),
				"Predicate should return false for leads with null status.");
	}

	@Test
	@DisplayName("Should return false for leads with a status not equal to OPEN")
	void testOpenLeadFilterForNonOpenStatus() {
		Leads mockLeadsWithDifferentStatus = mock(Leads.class);
		when(mockLeadsWithDifferentStatus.getStatus()).thenReturn("CLOSED");

		assertFalse(LeadsPredicates.OPEN_LEAD_FILTER.test(mockLeadsWithDifferentStatus),
				"Predicate should return false for leads with a status not equal to OPEN.");
	}

	@Test
	@DisplayName("Should return true for leads with a status of OPEN")
	void testOpenLeadFilterForOpenStatus() {
		Leads mockLeadsWithOpenStatus = mock(Leads.class);
		when(mockLeadsWithOpenStatus.getStatus()).thenReturn("Open");

		assertTrue(LeadsPredicates.OPEN_LEAD_FILTER.test(mockLeadsWithOpenStatus),
				"Predicate should return true for leads with a status of OPEN.");
	}

	@Test
	@DisplayName("Should return false for null status")
	void testCloseLeadFilterForNullStatus() {
		Leads mockLeadsWithNullStatus = mock(Leads.class);
		when(mockLeadsWithNullStatus.getStatus()).thenReturn(null);
		assertFalse(LeadsPredicates.CLOSE_LEAD_FILTER.test(mockLeadsWithNullStatus),
				"Predicate should return false for leads with null status.");
	}

	@Test
	@DisplayName("Should return false for leads with a status of OPEN")
	void testCloseLeadFilterForOpenStatus() {
		Leads mockLeadsWithOpenStatus = mock(Leads.class);
		when(mockLeadsWithOpenStatus.getStatus()).thenReturn("OPEN");
		assertFalse(LeadsPredicates.CLOSE_LEAD_FILTER.test(mockLeadsWithOpenStatus),
				"Predicate should return false for leads with a status of OPEN.");
	}

	@Test
	@DisplayName("Should return true for leads with a status other than OPEN")
	void testCloseLeadFilterForNonOpenStatus() {
		Leads mockLeadsWithClosedStatus = mock(Leads.class);
		when(mockLeadsWithClosedStatus.getStatus()).thenReturn("CLOSED");
		assertTrue(LeadsPredicates.CLOSE_LEAD_FILTER.test(mockLeadsWithClosedStatus),
				"Predicate should return true for leads with a status other than OPEN.");
	}

	@Test
	@DisplayName("Should return true when lead is assigned to the staff member as an employee")
	void testAssignedToFilterAsEmployee() {
		Leads lead = mock(Leads.class);
		EmployeeMaster employee = mock(EmployeeMaster.class);
		Integer staffId = 100;
		when(employee.getStaffId()).thenReturn(staffId);
		when(lead.getEmployee()).thenReturn(employee);
		assertTrue(LeadsPredicates.ASSIGNED_TO_FILTER.test(lead, staffId),
				"Predicate should return true when lead is assigned to the staff member as an employee.");
	}

	@Test
	@DisplayName("Should return true when lead is created by the staff member")
	void testAssignedToFilterAsCreator() {
		Leads lead = mock(Leads.class);
		Integer createdBy = 200;
		Integer staffId = createdBy;
		EmployeeMaster employee = mock(EmployeeMaster.class);
		when(employee.getStaffId()).thenReturn(100);
		when(lead.getEmployee()).thenReturn(employee);
		when(lead.getCreatedBy()).thenReturn(createdBy);
		assertTrue(LeadsPredicates.ASSIGNED_TO_FILTER.test(lead, staffId),
				"Predicate should return true when lead is created by the staff member.");
	}

	@Test
	@DisplayName("Should return false when lead is neither assigned to nor created by the staff member")
	void testAssignedToFilterNotAssignedNorCreated() {
		Leads lead = mock(Leads.class);
		EmployeeMaster employee = mock(EmployeeMaster.class);
		Integer staffId = 300;
		Integer differentId = 400;
		when(employee.getStaffId()).thenReturn(differentId);
		when(lead.getEmployee()).thenReturn(employee);
		when(lead.getCreatedBy()).thenReturn(differentId);
		assertFalse(LeadsPredicates.ASSIGNED_TO_FILTER.test(lead, staffId),
				"Predicate should return false when lead is neither assigned to nor created by the staff member.");
	}

	@Test
	void testTimelineCallWithNonNullStatus() {
		Call completeCall = new Call();
		completeCall.setStatus("COMPLETE");
		assertTrue(LeadsPredicates.TIMELINE_CALL.test(completeCall),
				"Predicate should return true for calls with status 'COMPLETE'.");
	}

	@Test
	void testTimelineCallWithNullStatus() {
		Call nullStatusCall = new Call();
		nullStatusCall.setStatus(null);
		assertFalse(LeadsPredicates.TIMELINE_CALL.test(nullStatusCall),
				"Predicate should return false for calls with null status.");
	}

	@Test
	void testTimelineCallWithDifferentStatus() {
		Call incompleteCall = new Call();
		incompleteCall.setStatus("INCOMPLETE");
		assertFalse(LeadsPredicates.TIMELINE_CALL.test(incompleteCall),
				"Predicate should return false for calls with status other than 'COMPLETE'.");
	}

	@Test
	void testTimelineEmailWithSentStatus() {
		Email sentEmail = new Email();
		sentEmail.setStatus("SEND");

		assertTrue(LeadsPredicates.TIMELINE_EMAIL.test(sentEmail),
				"Predicate should return true for emails with status 'SEND'.");
	}

	@Test
	void testTimelineEmailWithNullStatus() {
		Email emailWithNullStatus = new Email();
		assertFalse(LeadsPredicates.TIMELINE_EMAIL.test(emailWithNullStatus),
				"Predicate should return false for emails with null status.");
	}

	@Test
	void testTimelineEmailWithOtherStatus() {
		Email emailWithOtherStatus = new Email();
		emailWithOtherStatus.setStatus("DRAFT");
		assertFalse(LeadsPredicates.TIMELINE_EMAIL.test(emailWithOtherStatus),
				"Predicate should return false for emails with status other than 'SEND'.");
	}

	@Test
	void testTimelineVisitWithCompleteStatus() {
		Visit completedVisit = new Visit();
		completedVisit.setStatus("COMPLETE");

		assertTrue(LeadsPredicates.TIMELINE_VISIT.test(completedVisit),
				"Predicate should return true for visits with status 'COMPLETE'.");
	}

	@Test
	void testTimelineVisitWithNullStatus() {
		Visit visitWithNullStatus = new Visit();

		assertFalse(LeadsPredicates.TIMELINE_VISIT.test(visitWithNullStatus),
				"Predicate should return false for visits with null status.");
	}

	@Test
	void testTimelineVisitWithOtherStatus() {
		Visit visitWithOtherStatus = new Visit();
		visitWithOtherStatus.setStatus("IN_PROGRESS");

		assertFalse(LeadsPredicates.TIMELINE_VISIT.test(visitWithOtherStatus),
				"Predicate should return false for visits with status other than 'COMPLETE'.");
	}
}
