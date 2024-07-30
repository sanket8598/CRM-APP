package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DescriptionTest {

	@Test
    void testGettersAndSetters() {
        Description description = new Description();
        
        Integer descId = 1;
        String subject = "Test Subject";
        String status = "Open";
        String desc = "This is a test description.";
        String action = "No action required.";
        LocalDate date = LocalDate.of(2023, 7, 29);
        Boolean isOpportunity = false;
        Leads lead = new Leads();
        
        description.setDescId(descId);
        description.setSubject(subject);
        description.setStatus(status);
        description.setDesc(desc);
        description.setAction(action);
        description.setDate(date);
        description.setIsOpportunity(isOpportunity);
        description.setLead(lead);
        
        assertEquals(descId, description.getDescId());
        assertEquals(subject, description.getSubject());
        assertEquals(status, description.getStatus());
        assertEquals(desc, description.getDesc());
        assertEquals(action, description.getAction());
        assertEquals(date, description.getDate());
        assertEquals(isOpportunity, description.getIsOpportunity());
        assertEquals(lead, description.getLead());
    }
}
