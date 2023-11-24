package ai.rnt.crm.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

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
			int numOfRows = sheet.getPhysicalNumberOfRows();
			int numOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
			row: for (int i = 1; i < numOfRows; i++) {
				ArrayList<String> tableData = new ArrayList<>();
				Row row = sheet.getRow(i);
				if (row == null)
					continue row;
				for (int j = 0; j <= numOfColumns; j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						String data = cell.toString().trim();
						if (!(data).equals("") && !(data.isEmpty()))
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

}
