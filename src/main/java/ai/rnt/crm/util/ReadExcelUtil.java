package ai.rnt.crm.util;

import static ai.rnt.crm.constants.MessageConstants.MSG;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import ai.rnt.crm.dto.LeadDto;
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

	public Map<String, Object> getLeadFromExcelFile(Workbook workbook, Sheet sheet)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Map<String, Object> dataExcel = new HashMap<>();
		List<LeadDto> excelLeads = new ArrayList<>();
		dataExcel.put("status", true);
		try {
			int noOfRows = sheet.getPhysicalNumberOfRows();
			int numOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
			for (int i = 1; i <= noOfRows; i++) {
				LeadDto leadDto = null;
				Row row = sheet.getRow(i);
				if (checkIfRowIsEmpty(row))
					break;
				else
					leadDto = new LeadDto();
				for (int j = 0; j <= numOfColumns; j++) {
					Cell cell = row.getCell(j);
					if (cell != null) {
						String data = "";
						if (cell.getCellTypeEnum() == CellType.NUMERIC)
							data = cell.getNumericCellValue() + "";
						else
							data = cell.getStringCellValue() + "";
						Map<String, Object> excelMap = createLeadFromExcel(data, j, leadDto);
						if (Objects.nonNull(excelMap) && !excelMap.isEmpty() && excelMap.containsKey("errorList")) {
							dataExcel.put("status", false);
							dataExcel.put(MSG, excelMap.get("errorList"));
							return dataExcel;
						}
					}
				}
				if (leadDto != null)
					excelLeads.add(leadDto);
			}
			excelLeads.forEach(e -> System.out.println(e + "---Excel Leads"));
			dataExcel.put("leadData", excelLeads);
			return dataExcel;
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
		return row == null || row.getLastCellNum() <= 0 || IntStream.range(row.getFirstCellNum(), row.getLastCellNum())
				.filter(i -> row.getCell(i) == null || row.getCell(i).getCellTypeEnum() == CellType.BLANK).count() > 11;
	}

	private Map<String, Object> createLeadFromExcel(String data, int fieldCount, LeadDto leadDto) {
		List<String> errorList = new ArrayList<>();
		Map<String, Object> dataMap = new HashMap<>();
		Optional.ofNullable(data).filter(d -> !d.isEmpty()).ifPresent(d -> {
			switch (fieldCount) {
			case 0:
				if (ExcelFieldValidationUtil.isValidFnameLname(d))
					leadDto.setFirstName(d);
				else
					errorList.add("Please Enter Valid First Name!!");
				break;
			case 1:
				if (ExcelFieldValidationUtil.isValidFnameLname(d))
					leadDto.setLastName(d);
				else
					errorList.add("Please Enter Valid Last Name!!");
				break;
			case 2:
				if (ExcelFieldValidationUtil.isValidEmail(d))
					leadDto.setEmail(d);
				else
					errorList.add("Please Enter Valid Email Address!!");
				break;
			case 3:
				leadDto.setPhoneNumber(d);
				break;
			case 4:
				if (ExcelFieldValidationUtil.isValidDesignation(d))
					leadDto.setDesignation(d);
				else
					errorList.add("Please Enter Character For the Designation!!");
				break;
			case 5:
				if (!d.isEmpty())
					leadDto.setTopic(d);
				else
					errorList.add("Please Enter The Topic!!");
				break;
			case 6:
				if (!d.isEmpty())
					leadDto.setCompanyName(d);
				else
					errorList.add("Please Enter The Company Name!!");
				break;
			case 7:
				if (!d.isEmpty())
					leadDto.setCompanyWebsite(d);
				else
					errorList.add("Please Enter The Company Website!!");
				break;
			case 8:
				if (ExcelFieldValidationUtil.isValidBudgetAmount(d))
					leadDto.setBudgetAmount(CurrencyUtil.commaSepAmount(Double.parseDouble(d)));
				else
					errorList.add("Please Enter The Valid Budget Amount!!");
				break;
			case 9:
				if (!d.isEmpty())
					leadDto.setServiceFallsId(d);
				else
					errorList.add("Please Enter The Service Falls!!");
				break;
			case 10:
				leadDto.setLeadSourceId(d);
				break;
			case 11:
				leadDto.setPseudoName(d);
				break;
			default:
				errorList.add("Invalid field count!");
			}
		});
		dataMap.put("errorList", errorList);
		return dataMap;
	}
}
