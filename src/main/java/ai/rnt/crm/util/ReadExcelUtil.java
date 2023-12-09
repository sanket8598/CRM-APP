package ai.rnt.crm.util;

import static ai.rnt.crm.constants.ExcelConstants.COLUMN_SIZE;
import static ai.rnt.crm.constants.ExcelConstants.ERROR;
import static ai.rnt.crm.constants.ExcelConstants.FLAG;
import static ai.rnt.crm.constants.ExcelConstants.LEAD_DATA;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static ai.rnt.crm.util.ExcelFieldValidationUtil.isValidBudgetAmount;
import static ai.rnt.crm.util.ExcelFieldValidationUtil.isValidDesignation;
import static ai.rnt.crm.util.ExcelFieldValidationUtil.isValidEmail;
import static ai.rnt.crm.util.ExcelFieldValidationUtil.isValidFnameLname;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
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

	public Map<String, Object> readExcelFile(Workbook workbook, Sheet sheet)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		Map<String, Object> dataExcel = new HashMap<>();
		List<LeadDto> excelLeads = new ArrayList<>();
		dataExcel.put(FLAG, true);
		try {
			int numOfColumns = sheet.getRow(0).getPhysicalNumberOfCells();
			rangeClosed(1, sheet.getPhysicalNumberOfRows()).filter(i -> !isRowEmpty(sheet.getRow(i))).forEach(i -> {
				Row row = sheet.getRow(i);
				LeadDto leadDto = new LeadDto();
				rangeClosed(0, numOfColumns).filter(j -> nonNull(row.getCell(j))).forEach(j -> {
					Cell cell = row.getCell(j);
					String data = "";
					if (cell.getCellTypeEnum().equals(NUMERIC))
						data = cell.getNumericCellValue() + "";
					else
						data = cell.getStringCellValue() + "";
					Map<String, Object> excelMap = createLeadFromExcelData(data, j, leadDto, i + 1);
					if (nonNull(excelMap) && !excelMap.isEmpty() && excelMap.containsKey(ERROR)) {
						String error = (String) excelMap.get(ERROR);
						if (nonNull(error) && !error.isEmpty()) {
							dataExcel.put(FLAG, false);
							dataExcel.put(MSG, error);
							return;//to break inner as well as outer loop
						}
					}
				});
				if (nonNull(leadDto))
					excelLeads.add(leadDto);
			});
			dataExcel.put(LEAD_DATA, excelLeads);
			return dataExcel;
		} catch (Exception e) {
			log.info("Got Exception while reading data from excel file..{}", e.getMessage());
			throw new CRMException(e);
		} finally {
			workbook.close();
		}
	}

	public List<String> readExcelHeaders(Sheet sheet) throws IOException {
		List<String> headerList = new ArrayList<>();
		try {
			Row row = sheet.getRow(0);
			if (nonNull(row))
				rangeClosed(0, row.getPhysicalNumberOfCells()).filter(i-> nonNull(row.getCell(i)))
						.forEach(r -> headerList.add(row.getCell(r).getStringCellValue()));
		} catch (Exception e) {
			log.error("Exception Occured while fetching the excel header: {}",e.getMessage());
		}
		return headerList;
	}

	private boolean isRowEmpty(Row row) {
		return isNull(row) || row.getLastCellNum() <= 0
				|| range(row.getFirstCellNum(), row.getLastCellNum())
						.filter(i -> isNull(row.getCell(i)) || row.getCell(i).getCellTypeEnum().equals(BLANK))
						.count() > COLUMN_SIZE;
	}

	private Map<String, Object> createLeadFromExcelData(String data, int fieldCount, LeadDto leadDto, int rowNum) {
		StringBuilder errorList = new StringBuilder();
		Map<String, Object> dataMap = new HashMap<>();
		switch (fieldCount) {
		case 0:
			if (nonNull(data) && !data.isEmpty())
				if (isValidFnameLname(data))
					leadDto.setFirstName(data);
				else
					errorList.append("Please Enter Valid First Name On Row No: " + rowNum);
			else
				errorList.append("Please Enter The First Name On Row No: " + rowNum);
			break;
		case 1:
			if (nonNull(data) && !data.isEmpty())
				if (isValidFnameLname(data))
					leadDto.setLastName(data);
				else
					errorList.append("Please Enter Valid Last Name On Row No: " + rowNum);
			else
				errorList.append("Please Enter The Last Name On Row No: " + rowNum);
			break;
		case 2:
			if (nonNull(data) && !data.isEmpty())
				if (isValidEmail(data))
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
				if (isValidDesignation(data))
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
			if (isValidBudgetAmount(data))
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
