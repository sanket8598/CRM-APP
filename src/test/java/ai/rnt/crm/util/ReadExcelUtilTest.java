package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.exception.CRMException;

class ReadExcelUtilTest {
	
	private ReadExcelUtil readExcelUtil;

    @Mock
    private Workbook mockWorkbook;
    @Mock
    private Sheet mockSheet;
    @Mock
    private Row mockRow;
    @Mock
    private Cell mockCell;
    @Mock
    private Cell mockCell1;
    @Mock
    private Cell mockCell2;
    @Mock
    private Cell mockCell3;
    @Mock
    private Cell mockCell4;
    @Mock
    private Cell mockCell5;
    @Mock
    private Cell mockCell6;
    @Mock
    private Cell mockCell7;
    @Mock
    private Cell mockCell8;
    @Mock
    private Cell mockCell9;
    @Mock
    private Cell mockCell10;
    @Mock
    private Cell mockCell11;
    @Mock
    private Cell mockCell12;
    @Mock
    private Cell mockCell13;
    @Mock
    private Cell mockCell14;
    @Mock
    private Cell cell;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        readExcelUtil = new ReadExcelUtil();
    }

    @Test
    void readExcelFile_ValidData_ReturnsCorrectMap() throws IOException, InvalidFormatException {
        when(mockWorkbook.getSheetAt(0)).thenReturn(mockSheet);
        when(mockSheet.getPhysicalNumberOfRows()).thenReturn(2); // Header + 1 data row
        when(mockSheet.getRow(0)).thenReturn(mockRow); // Header row
        when(mockSheet.getRow(1)).thenReturn(mockRow); // Data row
        Short f=0;
        Short l=14;
        when(mockRow.getFirstCellNum()).thenReturn(f);
        when(mockRow.getLastCellNum()).thenReturn(l); 
      when(mockRow.getPhysicalNumberOfCells()).thenReturn(14);
        when(mockRow.getCell(0)).thenReturn(mockCell);
        when(mockCell.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell.getStringCellValue()).thenReturn("Test");
        when(mockRow.getCell(1)).thenReturn(mockCell1);
        when(mockCell1.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell1.getStringCellValue()).thenReturn("Data");
        when(mockRow.getCell(2)).thenReturn(mockCell2);
        when(mockCell2.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell2.getStringCellValue()).thenReturn("Sanket@rnt.ai");
        when(mockRow.getCell(3)).thenReturn(mockCell3);
        when(mockCell3.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell3.getStringCellValue()).thenReturn("919923932992");
        when(mockRow.getCell(4)).thenReturn(mockCell4);
        when(mockCell4.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell4.getStringCellValue()).thenReturn("Java Developer");
        when(mockRow.getCell(5)).thenReturn(mockCell5);
        when(mockCell5.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell5.getStringCellValue()).thenReturn("Java Developer");
        when(mockRow.getCell(6)).thenReturn(mockCell6);
        when(mockCell6.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell6.getStringCellValue()).thenReturn("Java Developer");
        when(mockRow.getCell(7)).thenReturn(mockCell7);
        when(mockCell7.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell7.getStringCellValue()).thenReturn("Java Developer");
        when(mockRow.getCell(8)).thenReturn(mockCell8);
        when(mockCell8.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell8.getStringCellValue()).thenReturn("Java Developer");
        when(mockRow.getCell(9)).thenReturn(mockCell9);
        when(mockCell9.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell9.getStringCellValue()).thenReturn("Java Developer");
        when(mockRow.getCell(10)).thenReturn(mockCell10);
        when(mockCell10.getCellTypeEnum()).thenReturn(CellType.NUMERIC);
        when(mockCell10.getStringCellValue()).thenReturn("34459745");
        when(mockRow.getCell(11)).thenReturn(mockCell11);
        when(mockCell11.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell11.getStringCellValue()).thenReturn("Developer");
        when(mockRow.getCell(12)).thenReturn(mockCell12);
        when(mockCell12.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell12.getStringCellValue()).thenReturn("Developer");
        when(mockRow.getCell(13)).thenReturn(mockCell13);
        when(mockCell13.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell13.getStringCellValue()).thenReturn("Developer");
        when(mockRow.getCell(14)).thenReturn(mockCell14);
        when(mockCell14.getCellTypeEnum()).thenReturn(CellType.STRING);
        when(mockCell14.getStringCellValue()).thenReturn("Developer");
        

        Map<String, Object> result = readExcelUtil.readExcelFile(mockWorkbook, mockSheet);

        assertFalse((Boolean) result.get("flag"));
    }
    @Test
    void readExcelFileInValidData() throws IOException, InvalidFormatException {
    	when(mockWorkbook.getSheetAt(0)).thenReturn(mockSheet);
    	when(mockSheet.getPhysicalNumberOfRows()).thenReturn(2); 
    	when(mockSheet.getRow(0)).thenReturn(mockRow); 
    	when(mockSheet.getRow(1)).thenReturn(mockRow); 
    	Short f=0;
    	Short l=14;
    	when(mockRow.getFirstCellNum()).thenReturn(f);
    	when(mockRow.getLastCellNum()).thenReturn(l); 
    	when(mockRow.getPhysicalNumberOfCells()).thenReturn(14);
    	when(mockRow.getCell(0)).thenReturn(mockCell);
    	when(mockCell.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(1)).thenReturn(mockCell1);
    	when(mockCell1.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell1.getStringCellValue()).thenReturn(" ");
    	when(mockRow.getCell(2)).thenReturn(mockCell2);
    	when(mockCell2.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell2.getStringCellValue()).thenReturn(null);
    	when(mockRow.getCell(3)).thenReturn(mockCell3);
    	when(mockCell3.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell3.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(4)).thenReturn(mockCell4);
    	when(mockCell4.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell4.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(5)).thenReturn(mockCell5);
    	when(mockCell5.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell5.getStringCellValue()).thenReturn(" ");
    	when(mockRow.getCell(6)).thenReturn(mockCell6);
    	when(mockCell6.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell6.getStringCellValue()).thenReturn(" ");
    	when(mockRow.getCell(7)).thenReturn(mockCell7);
    	when(mockCell7.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell7.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(8)).thenReturn(mockCell8);
    	when(mockCell8.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell8.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(9)).thenReturn(mockCell9);
    	when(mockCell9.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell9.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(10)).thenReturn(mockCell10);
    	when(mockCell10.getCellTypeEnum()).thenReturn(CellType.NUMERIC);
    	when(mockCell10.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(11)).thenReturn(mockCell11);
    	when(mockCell11.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell11.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(12)).thenReturn(mockCell12);
    	when(mockCell12.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell12.getStringCellValue()).thenReturn("Developer");
    	when(mockRow.getCell(13)).thenReturn(mockCell13);
    	when(mockCell13.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell13.getStringCellValue()).thenReturn("");
    	when(mockRow.getCell(14)).thenReturn(mockCell14);
    	when(mockCell14.getCellTypeEnum()).thenReturn(CellType.STRING);
    	when(mockCell14.getStringCellValue()).thenReturn("");
    	
    	
    	Map<String, Object> result = readExcelUtil.readExcelFile(mockWorkbook, mockSheet);
    	
    	assertFalse((Boolean) result.get("flag"));
    }

    
    @Test
    void readExcelHeadersValidHeadersReturnsCorrectList() throws IOException {
        when(mockSheet.getRow(0)).thenReturn(mockRow);
        when(mockRow.getPhysicalNumberOfCells()).thenReturn(3); 
        when(mockRow.getCell(0)).thenReturn(cell);
        when(mockRow.getCell(1)).thenReturn(cell);
        when(mockRow.getCell(2)).thenReturn(cell);
        List<String> headers = readExcelUtil.readExcelHeaders(mockSheet);
        assertEquals(3, headers.size());
    }
    @Test
    void readExcelHeadersException(){
       try{
    	   when(mockSheet.getRow(0)).thenThrow(CRMException.class);
    	   readExcelUtil.readExcelHeaders(mockSheet);
    	   }catch(Exception e) {
    		   assertNotNull(e.getCause());
    	   };
    	   
    }
    @Test
    void readExcelFileException(){
    		when(mockSheet.getRow(0)).thenThrow(CRMException.class);
    		assertThrows(CRMException.class,()->readExcelUtil.readExcelFile(mockWorkbook, mockSheet));
    }
}
