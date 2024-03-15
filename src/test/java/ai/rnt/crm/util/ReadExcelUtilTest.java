package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ReadExcelUtilTest {
	
	@Autowired 
	MockMvc mockMvc;
	
	@InjectMocks 
	ReadExcelUtil readExcelUtil;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		MockMvcBuilders.standaloneSetup(readExcelUtil).build();
	}

	//@Test
    void testReadExcelFile_ValidData() throws IOException, InvalidFormatException {
        Workbook workbook =mock(Workbook.class);// WorkbookFactory.create(getClass().getResourceAsStream("valid_excel_file.xlsx"));
        Sheet sheet = workbook.getSheetAt(0);
        ReadExcelUtil readExcelUtil = new ReadExcelUtil();
      //  when(sheet.getRow(0).getPhysicalNumberOfCells()).thenReturn(1);
        Map<String, Object> result = readExcelUtil.readExcelFile(workbook, sheet);
        assertNotNull(result);
        assertTrue((boolean) result.get("FLAG"));
        assertNotNull(result.get("LEAD_DATA"));
    }

}
