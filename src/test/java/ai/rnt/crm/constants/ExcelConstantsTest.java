package ai.rnt.crm.constants;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExcelConstantsTest {

	 @Test
	    void testFlagConstant() {
	        assertEquals("flag", ExcelConstants.FLAG, "The FLAG constant does not match the expected value.");
	    }

	    @Test
	    void testErrorConstant() {
	        assertEquals("error", ExcelConstants.ERROR, "The ERROR constant does not match the expected value.");
	    }

	    @Test
	    void testLeadDataConstant() {
	        assertEquals("leadData", ExcelConstants.LEAD_DATA, "The LEAD_DATA constant does not match the expected value.");
	    }

	    @Test
	    void testColumnSizeConstant() {
	        assertEquals(Integer.valueOf(11), ExcelConstants.COLUMN_SIZE, "The COLUMN_SIZE constant does not match the expected value.");
	    }

}
