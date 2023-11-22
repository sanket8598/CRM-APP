package ai.rnt.crm.util;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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

	private ReadExcelUtil() {

	}

	public static ArrayList<ArrayList<String>> getLeadFromExcelFile(MultipartFile file)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		ArrayList<ArrayList<String>> record = new ArrayList<ArrayList<String>>();
		try {
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			int numOfRows = sheet.getPhysicalNumberOfRows();
			int numOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
			r: for (int i = 1; i < numOfRows; i++) {
				ArrayList<String> tableData = new ArrayList<>();
				Row row = sheet.getRow(i);
				if (row == null) {
					continue r;
				}
				c: for (int j = 0; j <= numOfColumns; j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						String data = cell.toString();
						data = data.trim();
						if (!(data).equals("") && !(data.isEmpty())) {
							tableData.add(data);
						}
					}
				}
				if (!tableData.isEmpty()) {
					record.add(tableData);
				}
			}
			workbook.close();
			return record;
		} catch (Exception e) {
			log.info("Got Exception while getting data from excel file..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
