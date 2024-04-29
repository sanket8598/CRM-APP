package ai.rnt.crm.api.restcontroller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.service.ServiceFallsService;

class LeadsControllerTest {

	@Mock
	private LeadService leadService;

	@Mock
	private ServiceFallsService serviceFallsService;

	@InjectMocks
	private LeadsController leadsController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void saveLeadTest() {
		LeadDto dto = new LeadDto();
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.createLead(any(LeadDto.class))).thenReturn(expectedResponse);
		leadsController.saveLead(dto);
		verify(leadService).createLead(dto);
	}

	@Test
	void getLeadsByStatusTest() {
		String leadsStatus = "Open";
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.getLeadsByStatus(anyString())).thenReturn(expectedResponse);
		leadsController.getLeadsByStatus(leadsStatus);
		verify(leadService).getLeadsByStatus(leadsStatus);
	}

	@Test
	void getAllSerciveFallsTest() {
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(serviceFallsService.getAllSerciveFalls()).thenReturn(expectedResponse);
		leadsController.getAllSerciveFalls();
		verify(serviceFallsService).getAllSerciveFalls();
	}

	@Test
	void getAllLeadSourceTest() {
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.getAllLeadSource()).thenReturn(expectedResponse);
		leadsController.getAllLeadSource();
		verify(leadService).getAllLeadSource();
	}

	@Test
	void getAllDropDownDataTest() {
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.getAllDropDownData()).thenReturn(expectedResponse);
		leadsController.getAllDropDownData();
		verify(leadService).getAllDropDownData();
	}

	@Test
	void getLeadDashboardDataTest() {
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.getLeadDashboardData()).thenReturn(expectedResponse);
		leadsController.getLeadDashboardData();
		verify(leadService).getLeadDashboardData();
	}

	@Test
	void getLeadDashboardDataByStatusTest() {
		String leadsStatus = "Open";
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.getLeadDashboardDataByStatus(leadsStatus)).thenReturn(expectedResponse);
		leadsController.getLeadDashboardDataByStatus(leadsStatus);
		verify(leadService).getLeadDashboardDataByStatus(leadsStatus);
	}

	@Test
	void editLeadTest() {
		Integer leadId = 1;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.editLead(leadId)).thenReturn(expectedResponse);
		leadsController.editLead(leadId);
		verify(leadService).editLead(leadId);
	}

	@Test
	void qualifyLeadTest() {
		Integer leadId = 1;
		QualifyLeadDto dto = new QualifyLeadDto();
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.qualifyLead(leadId, dto)).thenReturn(expectedResponse);
		leadsController.qualifyLead(leadId, dto);
		verify(leadService).qualifyLead(leadId, dto);
	}

	@Test
	void assignLeadTest() {
		Map<String, Integer> map = new HashMap<>();
		map.put("leadId", 1);
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.assignLead(map)).thenReturn(expectedResponse);
		leadsController.assignLead(map);
		verify(leadService).assignLead(map);
	}

	@Test
	void disQualifyLeadTest() {
		Integer leadId = 1;
		LeadDto dto = new LeadDto();
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.disQualifyLead(anyInt(), any(LeadDto.class))).thenReturn(expectedResponse);
		leadsController.disQualifyLead(leadId, dto);
		verify(leadService).disQualifyLead(leadId, dto);
	}

	@Test
	void updateLeadContactTest() {
		Integer leadId = 1;
		UpdateLeadDto dto = new UpdateLeadDto();
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.updateLeadContact(anyInt(), any(UpdateLeadDto.class))).thenReturn(expectedResponse);
		leadsController.updateLeadContact(dto, leadId);
		verify(leadService).updateLeadContact(leadId, dto);
	}

	@Test
	void importantLeadTest() {
		Integer leadId = 1;
		boolean status = true;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.importantLead(anyInt(), anyBoolean())).thenReturn(expectedResponse);
		leadsController.importantLead(leadId, status);
		verify(leadService).importantLead(leadId, status);
	}

	@Test
	void reactiveLeadTest() {
		Integer leadId = 1;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.reactiveLead(anyInt())).thenReturn(expectedResponse);
		leadsController.reactiveLead(leadId);
		verify(leadService).reactiveLead(leadId);
	}

	@Test
	void addSortFilterToLeadsTest() {
		LeadSortFilterDto sortFilterDto = new LeadSortFilterDto();
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.addSortFilterForLeads(any(LeadSortFilterDto.class))).thenReturn(expectedResponse);
		leadsController.addSortFilterToLeads(sortFilterDto);
		verify(leadService).addSortFilterForLeads(sortFilterDto);
	}

	@Test
	void uploadExcelTest() throws IOException {
		MultipartFile mockFile = mock(MultipartFile.class);
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.uploadExcel(mockFile)).thenReturn(expectedResponse);
		leadsController.uploadExcel(mockFile);
		verify(leadService).uploadExcel(mockFile);
	}

	@Test
	void editQualifyLeadTest() {
		Integer leadId = 1;
		EnumMap<ApiResponse, Object> expectedResponseMap = new EnumMap<>(ApiResponse.class);
		ResponseEntity<EnumMap<ApiResponse, Object>> expectedResponse = ResponseEntity.ok(expectedResponseMap);
		when(leadService.getForQualifyLead(leadId)).thenReturn(expectedResponse);
		leadsController.editQualifyLead(leadId);
		verify(leadService).getForQualifyLead(leadId);
	}
}
