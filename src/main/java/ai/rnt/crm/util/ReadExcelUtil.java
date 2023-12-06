package ai.rnt.crm.util;

import static ai.rnt.crm.constants.ExcelConstants.COLUMN_SIZE;
import static ai.rnt.crm.constants.ExcelConstants.ERROR;
import static ai.rnt.crm.constants.ExcelConstants.FLAG;
import static ai.rnt.crm.constants.ExcelConstants.LEAD_DATA;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
 * @author sanket wakankar
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
		dataExcel.put(FLAG, true);
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
						if (cell.getCellTypeEnum().equals(CellType.NUMERIC))
							data = cell.getNumericCellValue() + "";
						else
							data = cell.getStringCellValue() + "";
						Map<String, Object> excelMap = createLeadFromExcel(data, j, leadDto, i);
						if (Objects.nonNull(excelMap) && !excelMap.isEmpty() && excelMap.containsKey(ERROR)) {
							String error = (String) excelMap.get(ERROR);
							if (nonNull(error) && !error.isEmpty()) {
								dataExcel.put(FLAG, false);
								dataExcel.put(MSG, error);
								return dataExcel;
							}
						}
					}
				}
				if (nonNull(leadDto))
					excelLeads.add(leadDto);
			}
			dataExcel.put(LEAD_DATA, excelLeads);
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
			if (nonNull(row))
				for (int k = 0; k < row.getPhysicalNumberOfCells(); k++)
					headerList.add(row.getCell(k).getStringCellValue());
		} catch (Exception e) {
			log.error("Exception Occured while fetching the header:" + e);
		}
		return headerList;
	}

	private boolean checkIfRowIsEmpty(Row row) {
		return row == null || row.getLastCellNum() <= 0
				|| IntStream.range(row.getFirstCellNum(), row.getLastCellNum())
						.filter(i -> isNull(row.getCell(i)) || row.getCell(i).getCellTypeEnum() == CellType.BLANK)
						.count() > COLUMN_SIZE;
	}

	private Map<String, Object> createLeadFromExcel(String data, int fieldCount, LeadDto leadDto, int rowNum) {
		StringBuilder errorList = new StringBuilder();
		Map<String, Object> dataMap = new HashMap<>();
		switch (fieldCount) {
		case 0:
			if (nonNull(data) && !data.isEmpty())
				if (ExcelFieldValidationUtil.isValidFnameLname(data))
					leadDto.setFirstName(data);
				else
					errorList.append("Please Enter Valid First Name On Row No: " + rowNum);
			else
				errorList.append("Please Enter The First Name On Row No: " + rowNum);
			break;
		case 1:
			if (nonNull(data) && !data.isEmpty())
				if (ExcelFieldValidationUtil.isValidFnameLname(data))
					leadDto.setLastName(data);
				else
					errorList.append("Please Enter Valid Last Name On Row No: " + rowNum);
			else
				errorList.append("Please Enter The Last Name On Row No: " + rowNum);
			break;
		case 2:
			if (nonNull(data) && !data.isEmpty())
				if (ExcelFieldValidationUtil.isValidEmail(data))
					leadDto.setEmail(data);
				else
					errorList.append("Please Enter Valid Email Address On Row No: " + rowNum);
			else
				errorList.append("Please Enter The Email Address On Row No: " + rowNum);
			break;
		case 3:
			leadDto.setPhoneNumber(nonNull(data) && !data.isEmpty() ? "+" + data : data);
			break;
		case 4:
			if (nonNull(data) && !data.isEmpty())
				if (ExcelFieldValidationUtil.isValidDesignation(data))
					leadDto.setDesignation(data);
				else
					errorList.append("Please Enter Character For the Designation On Row No: " + rowNum);
			else
				errorList.append("Please Enter the Designation On Row No: " + rowNum);
			break;
		case 5:
			if (nonNull(data) && !data.isEmpty())
				leadDto.setTopic(data);
			else
				errorList.append("Please Enter The Topic On Row No: " + rowNum);
			break;
		case 6:
			if (nonNull(data) && !data.isEmpty())
				leadDto.setCompanyName(data);
			else
				errorList.append("Please Enter The Company Name On Row No: " + rowNum);
			break;
		case 7:
			if (nonNull(data) && !data.isEmpty())
				leadDto.setCompanyWebsite(data);
			else
				errorList.append("Please Enter The Company Website On Row No: " + rowNum);
			break;
		case 8:
			if (ExcelFieldValidationUtil.isValidBudgetAmount(data))
				leadDto.setBudgetAmount(CurrencyUtil.commaSepAmount(Double.parseDouble(data)));
			else
				errorList.append("Please Enter The Valid Budget Amount On Row No: " + rowNum);
			break;
		case 9:
			if (nonNull(data) && !data.isEmpty())
				leadDto.setServiceFallsId(data);
			else
				errorList.append("Please Enter The Service Falls Into On Row No: " + rowNum);
			break;
		case 10:
			leadDto.setLeadSourceId(data);
			break;
		case 11:
			leadDto.setPseudoName(data);
			break;
		default:
			dataMap.put(ERROR, errorList.toString());
			break;
		}
		dataMap.put(ERROR, errorList.toString());
		return dataMap;
	}
}
