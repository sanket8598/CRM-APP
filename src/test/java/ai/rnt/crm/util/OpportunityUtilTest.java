package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OpportunityUtilTest {

	 @Test
	    void testAmountInWords() {
	        assertEquals("1.0K", OpportunityUtil.amountInWords(1000));
	        assertEquals("1.5K", OpportunityUtil.amountInWords(1500));
	        assertEquals("10.0K", OpportunityUtil.amountInWords(10000));
	        assertEquals("15.0L", OpportunityUtil.amountInWords(1500000));
	        assertEquals("1.5Cr", OpportunityUtil.amountInWords(15000000));
	        assertEquals("0.1K", OpportunityUtil.amountInWords(123)); // For small values, return the same value
	    }

	 @Test
	    void testCheckPhase() {
	        assertEquals("Phase 1", OpportunityUtil.checkPhase("Qualify"));
	        assertEquals("Phase 2", OpportunityUtil.checkPhase("Analysis"));
	        assertEquals("Phase 3", OpportunityUtil.checkPhase("Propose"));
	        assertEquals("Phase 4", OpportunityUtil.checkPhase("Close"));
	        assertEquals(null, OpportunityUtil.checkPhase("UNKNOWN"));
	    }
}
