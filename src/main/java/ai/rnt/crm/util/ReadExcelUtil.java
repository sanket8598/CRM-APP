package ai.rnt.crm.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import ai.rnt.crm.exception.CRMException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 16/11/2023
 *
 */

@Slf4j
@Component
public class ReadExcelUtil {

	public List<List<String>> getLeadFromExcelFile(Workbook workbook, Sheet sheet)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		List<List<String>> records = new ArrayList<>();
		try {
			int noOfRows= sheet.getPhysicalNumberOfRows();
			int numOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
			row: for (int i = 1; i <=noOfRows; i++) {
				ArrayList<String> tableData = new ArrayList<>();
				Row row = sheet.getRow(i);
				if (checkIfRowIsEmpty(row))
					break;
				for (int j = 0; j <= numOfColumns; j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						String data = "";
						if (cell.getCellTypeEnum() == CellType.NUMERIC)
							data = cell.getNumericCellValue() + "";
						else
							data = cell.getStringCellValue() + "";
						tableData.add(data);
					}
				}
				if (!tableData.isEmpty())
					records.add(tableData);
			}
			return records;
		} catch (Exception e) {
			log.info("Got Exception while getting data from excel file..{}", e.getMessage());
			throw new CRMException(e);
		} finally {
			workbook.close();
		}
	}

	public List<String> getAllHeaders(Sheet sheet) throws IOException {
		List<String> headerList = new ArrayList<>();
		try {
			Row row = sheet.getRow(0);
			if (row != null)
				for (int k = 0; k < row.getPhysicalNumberOfCells(); k++)
					headerList.add(row.getCell(k).getStringCellValue());
		} catch (Exception e) {
			log.error("Exception Occured while fetching the header:" + e);
		}
		return headerList;
	}

	private boolean checkIfRowIsEmpty(Row row) {
	    if (row == null) {
	        return true;
	    }
	    if (row.getLastCellNum() <= 0) {
	        return true;
	    }
	    int blankRowCount=0;
	    for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
	        Cell cell = row.getCell(cellNum);
	        if (cell == null || cell.getCellTypeEnum()==CellType.BLANK)
	        	 blankRowCount++;
	        if(blankRowCount>11)
	        	return true;
	    }
	    return false;
	}
}
