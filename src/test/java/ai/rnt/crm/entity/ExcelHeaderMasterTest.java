package ai.rnt.crm.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ExcelHeaderMasterTest {

	@Test
	void getterTest() {
		ExcelHeaderMaster excelHeaderMaster = new ExcelHeaderMaster();
		String headerName = excelHeaderMaster.getHeaderName();
		assertNull(headerName);
	}

	@Test
	void setterTest() {
		ExcelHeaderMaster excelHeaderMaster = new ExcelHeaderMaster();
		excelHeaderMaster.setHeaderId(1);
		excelHeaderMaster.setHeaderName("FirstName");
		assertEquals(1, excelHeaderMaster.getHeaderId());
	}
}
