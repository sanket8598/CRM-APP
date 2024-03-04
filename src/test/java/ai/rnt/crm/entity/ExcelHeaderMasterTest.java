package ai.rnt.crm.entity;

import org.junit.jupiter.api.Test;

class ExcelHeaderMasterTest {

	ExcelHeaderMaster excelHeaderMaster = new ExcelHeaderMaster();

	@Test
	void getterTest() {
		excelHeaderMaster.getHeaderId();
		excelHeaderMaster.getHeaderName();
	}

	@Test
	void setterTest() {
		excelHeaderMaster.setHeaderId(1);
		excelHeaderMaster.setHeaderName("FirstName");
	}
}
