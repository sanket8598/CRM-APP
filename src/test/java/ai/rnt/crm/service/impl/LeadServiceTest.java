package ai.rnt.crm.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.ExcelHeaderDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.ExcelHeaderMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.ReadExcelUtil;

@ExtendWith(MockitoExtension.class)
public class LeadServiceTest {

	@Mock
    private ReadExcelUtil readExcelUtil;

    @Mock
    private LeadDaoService leadDaoService;
    @Mock
    private ExcelHeaderDaoService excelHeaderDaoService;

    @Mock
    private ContactDaoService contactDaoService;
    @Mock
    private CompanyMasterDaoService companyMasterDaoService;
    @Mock
    private CountryDaoService countryDaoService;
    @Mock
    private AuditAwareUtil auditAwareUtil;
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private LeadServiceImpl leadService;
    
   
    private MultipartFile invalidFile;

    @BeforeEach
    void setUp() throws InvalidFormatException, IOException {
        invalidFile = new MockMultipartFile("data", "filename.xlsx", "text/plain", new byte[0]);
        when(readExcelUtil.readExcelFile(any(), any())).thenReturn(mockValidExcelData());
    }

    
    private Map<String, Object> mockValidExcelData() {
       Map<String, Object> excelData = new HashMap<>();
        excelData.put("flag", true);
        List<LeadDto> leads=new ArrayList<>();
        leads.add(new LeadDto());
        excelData.put("leadData", leads);
        return excelData;
    }
    
    
    @Test
    void testUploadExcel_ValidData1() throws Exception {
        try (Workbook workbook = new XSSFWorkbook()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.createSheet(); 
            workbook.write(bos);
            byte[] bytes = bos.toByteArray();
            try (InputStream is = new ByteArrayInputStream(bytes)) {
                MultipartFile validFile = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet+xml", is);
                List<Leads> leads = new ArrayList<>();
                List<ExcelHeaderMaster> excelHeaders = new ArrayList<>();
                ExcelHeaderMaster ex=new ExcelHeaderMaster();
                ex.setHeaderName("Header1");
                ExcelHeaderMaster ex2=new ExcelHeaderMaster();
                ex2.setHeaderName("Header2");
                excelHeaders.add(ex2);
                excelHeaders.add(ex);
                List<String> headerList=new ArrayList<>();
                headerList.add("Header1");
                headerList.add("Header2");
                CompanyDto dto=new CompanyDto();
                EmployeeMaster emp=new EmployeeMaster();
                when(leadDaoService.getAllLeads()).thenReturn(leads);
                when(readExcelUtil.readExcelHeaders(any(Sheet.class))).thenReturn(headerList);
                when(contactDaoService.addContact(any())).thenReturn(new Contacts());
                when(companyMasterDaoService.findByCompanyName(any())).thenReturn(Optional.of(dto));
                when(companyMasterDaoService.save(any())).thenReturn(Optional.of(dto));
                when(excelHeaderDaoService.getExcelHeadersFromDB()).thenReturn(excelHeaders);
                when(auditAwareUtil.getLoggedInUserName()).thenReturn("sw1375");
                when(employeeService.findByName("sw1375", null)).thenReturn(Optional.of(emp));
                ResponseEntity<EnumMap<ApiResponse, Object>> response = leadService.uploadExcel(validFile);
                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertTrue((Boolean) response.getBody().get(ApiResponse.SUCCESS));
            }
        }
    }
}
