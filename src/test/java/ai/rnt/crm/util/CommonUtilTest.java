package ai.rnt.crm.util;

import static java.util.Optional.empty;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.LeadSourceDto;
import ai.rnt.crm.dto.MainTaskDto;
import ai.rnt.crm.dto.ServiceFallsDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.CurrencyMaster;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.MeetingTask;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.OpportunityTask;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.repository.CallRepository;
import ai.rnt.crm.repository.LeadsRepository;
import ai.rnt.crm.repository.MeetingRepository;
import ai.rnt.crm.repository.VisitRepository;
import ai.rnt.crm.service.EmployeeService;

class CommonUtilTest {

	@Mock
	private CallRepository callRepository;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private VisitRepository visitRepository;

	@Mock
	private MeetingRepository meetingsRepository;

	@Mock
	private LeadsRepository leadsRepository;

	@Mock
	private CurrencyDaoService currencyDaoService;

	@Mock
	private List<Call> mockCalls;

	@Mock
	private List<Visit> mockVisits;

	@Mock
	private List<Meetings> mockMeetings;

	@Mock
	private Leads mockLead;

	@InjectMocks
	private CommonUtil commonUtil;

	@SuppressWarnings("deprecation")
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}

	private static final List<MainTaskDto> ALL_TASK_LIST = new ArrayList<>();
	private static final List<MainTaskDto> COMPLETED_TASK_LIST = new ArrayList<>();
	private static final List<MainTaskDto> IN_PROGRESS_TASK_LIST = new ArrayList<>();
	private static final List<MainTaskDto> ON_HOLD_TASK_LIST = new ArrayList<>();
	private static final List<MainTaskDto> NOT_STARTED_TASK_LIST = new ArrayList<>();
	private static final long ALL_TASK_COUNT = 0;
	private static final long COMPLETED_TASK_COUNT = 0;
	private static final long IN_PROGRESS_TASK_COUNT = 0;
	private static final long ON_HOLD_TASK_COUNT = 0;
	private static final long NOT_STARTED_TASK_COUNT = 0;

	private static final Map<String, Object> expectedTaskData = new HashMap<>();
	static {
		expectedTaskData.put("allTask", ALL_TASK_LIST);
		expectedTaskData.put("completedTask", COMPLETED_TASK_LIST);
		expectedTaskData.put("inProgressTask", IN_PROGRESS_TASK_LIST);
		expectedTaskData.put("onHoldTask", ON_HOLD_TASK_LIST);
		expectedTaskData.put("notStartedTask", NOT_STARTED_TASK_LIST);

		Map<String, Long> taskCount = new HashMap<>();
		taskCount.put("allTaskCount", ALL_TASK_COUNT);
		taskCount.put("completedTaskCount", COMPLETED_TASK_COUNT);
		taskCount.put("inProgressTaskCount", IN_PROGRESS_TASK_COUNT);
		taskCount.put("onHoldTaskCount", ON_HOLD_TASK_COUNT);
		taskCount.put("notStartedTaskCount", NOT_STARTED_TASK_COUNT);
		expectedTaskData.put("countByStatus", taskCount);
	}

	@Test
	void testGetTaskDataMap() {
		List<Call> calls = new ArrayList<>();
		List<Visit> visits = new ArrayList<>();
		List<Meetings> meetings = new ArrayList<>();
		Leads lead = new Leads();
		Opportunity oppt = new Opportunity();
		Map<String, Object> taskData = CommonUtil.getTaskDataMap(calls, visits, meetings, lead, oppt);
		assertEquals(expectedTaskData, taskData);
	}

	@Test
	void testGetCallRelatedTasksWithValidData() {
		PhoneCallTask callTask1 = new PhoneCallTask();
		PhoneCallTask callTask2 = new PhoneCallTask();
		EmployeeMaster master = new EmployeeMaster();
		master.setStaffId(1477);
		callTask1.setAssignTo(master);
		callTask2.setAssignTo(master);
		Call call = mock(Call.class);
		call.setCallFrom(master);
		call.setCallId(1);
		callTask1.setCall(call);
		callTask2.setCall(call);
		when(call.getCallTasks()).thenReturn(Arrays.asList(callTask1, callTask2));
		List<Call> calls = Collections.singletonList(call);
		List<MainTaskDto> result = CommonUtil.getCallRelatedTasks(calls);
		assertEquals(2, result.size());
	}

	@Test
	void testGetCallRelatedTasksWithEmptyList() {
		List<Call> calls = Collections.emptyList();
		List<MainTaskDto> result = CommonUtil.getCallRelatedTasks(calls);
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetVistRelatedTasksWithValidData() {
		VisitTask visitTask1 = new VisitTask();
		VisitTask visitTask2 = new VisitTask();
		EmployeeMaster master = new EmployeeMaster();
		master.setStaffId(1477);
		visitTask1.setAssignTo(master);
		visitTask2.setAssignTo(master);
		Visit visit = mock(Visit.class);
		visit.setVisitBy(master);
		visit.setVisitId(1);
		visitTask1.setVisit(visit);
		visitTask2.setVisit(visit);
		when(visit.getVisitTasks()).thenReturn(Arrays.asList(visitTask1, visitTask2));
		List<Visit> visits = Collections.singletonList(visit);
		List<MainTaskDto> result = CommonUtil.getVistRelatedTasks(visits);
		assertEquals(2, result.size());
	}

	@Test
	void testGetVistRelatedTasksWithEmptyList() {
		List<Visit> visits = Collections.emptyList();
		List<MainTaskDto> result = CommonUtil.getVistRelatedTasks(visits);
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetMeetingRelatedTasksWithValidData() {
		MeetingTask meetingTask1 = new MeetingTask();
		MeetingTask meetingTask2 = new MeetingTask();
		EmployeeMaster master = new EmployeeMaster();
		master.setStaffId(1477);
		meetingTask1.setAssignTo(master);
		meetingTask2.setAssignTo(master);
		Meetings meetings = mock(Meetings.class);
		meetings.setAssignTo(master);
		meetings.setMeetingId(1);
		meetingTask1.setMeetings(meetings);
		meetingTask2.setMeetings(meetings);
		when(meetings.getMeetingTasks()).thenReturn(Arrays.asList(meetingTask1, meetingTask2));
		List<Meetings> meeting = Collections.singletonList(meetings);
		List<MainTaskDto> result = CommonUtil.getMeetingRelatedTasks(meeting);
		assertEquals(2, result.size());
	}

	@Test
	void testGetMeetingRelatedTasksWithEmptyList() {
		List<Meetings> meetings = Collections.emptyList();
		List<MainTaskDto> result = CommonUtil.getMeetingRelatedTasks(meetings);
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetLeadRelatedTasksWithValidData() {
		LeadTask leadTask1 = new LeadTask();
		LeadTask leadTask2 = new LeadTask();
		EmployeeMaster master = new EmployeeMaster();
		master.setStaffId(1477);
		leadTask1.setAssignTo(master);
		leadTask2.setAssignTo(master);
		Leads leads = mock(Leads.class);
		leads.setEmployee(master);
		leads.setLeadId(1);
		leadTask1.setLead(leads);
		leadTask2.setLead(leads);
		when(leads.getLeadTasks()).thenReturn(Arrays.asList(leadTask1, leadTask2));
		List<MainTaskDto> result = CommonUtil.getLeadRelatedTasks(leads);
		assertEquals(2, result.size());
	}

	@Test
	void testGetLeadRelatedTasksWithEmptyList() {
		Leads leads = mock(Leads.class);
		List<MainTaskDto> result = CommonUtil.getLeadRelatedTasks(leads);
		assertTrue(result.isEmpty());
	}

	@Test
	void testGetOpportunityRelatedTasksWithValidData() {
		OpportunityTask leadTask1 = new OpportunityTask();
		OpportunityTask leadTask2 = new OpportunityTask();
		EmployeeMaster master = new EmployeeMaster();
		master.setStaffId(1477);
		leadTask1.setAssignTo(master);
		leadTask2.setAssignTo(master);
		Opportunity oppt = mock(Opportunity.class);
		oppt.setEmployee(master);
		oppt.setOpportunityId(1);
		leadTask1.setOpportunity(oppt);
		leadTask2.setOpportunity(oppt);
		when(oppt.getOpportunityTasks()).thenReturn(Arrays.asList(leadTask1, leadTask2));
		List<MainTaskDto> result = CommonUtil.getOpportunityRelatedTasks(oppt);
		assertEquals(2, result.size());
	}

	@Test
	void testGetOpportunityRelatedTasksWithEmptyList() {
		Opportunity oppt = mock(Opportunity.class);
		List<MainTaskDto> result = CommonUtil.getOpportunityRelatedTasks(oppt);
		assertTrue(result.isEmpty());
	}

	@Test
	void testSetServiceFallToLeadWithNumericServiceFallName() {
		ServiceFallsDaoSevice serviceFallsDaoSevice = mock(ServiceFallsDaoSevice.class);
		when(serviceFallsDaoSevice.getServiceFallById(anyInt())).thenReturn(Optional.of(new ServiceFallsMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setServiceFallToLead("123", leads, serviceFallsDaoSevice));
		assertNotNull(leads.getServiceFallsMaster()); // Ensure service falls master is set
	}

	@Test
	void testSetServiceFallToLeadWithEmptyServiceFallName() {
		ServiceFallsDaoSevice serviceFallsDaoSevice = mock(ServiceFallsDaoSevice.class);
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setServiceFallToLead("", leads, serviceFallsDaoSevice));
		assertNull(leads.getServiceFallsMaster()); // Ensure service falls master is set
	}

	@Test
	void testSetServiceFallToLeadWithOtherServiceFallName() {
		ServiceFallsDaoSevice serviceFallsDaoSevice = mock(ServiceFallsDaoSevice.class);
		when(serviceFallsDaoSevice.findByName(anyString())).thenReturn(Optional.of(new ServiceFallsMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setServiceFallToLead("Other", leads, serviceFallsDaoSevice));
		assertNotNull(leads.getServiceFallsMaster()); // Ensure service falls master is set
	}

	@Test
	void testSetServiceFallToLeadWithValidServiceFallName() throws Exception {
		ServiceFallsDaoSevice serviceFallsDaoSevice = mock(ServiceFallsDaoSevice.class);
		when(serviceFallsDaoSevice.save(any())).thenReturn(Optional.of(new ServiceFallsDto()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setServiceFallToLead("ValidServiceFallName", leads, serviceFallsDaoSevice));
		assertNotNull(leads.getServiceFallsMaster()); // Ensure service falls master is set
	}

	@Test
	void testSetLeadSourceToLeadWithNumericLeadSourceName() {
		LeadSourceDaoService leadSourceDaoService = mock(LeadSourceDaoService.class);
		when(leadSourceDaoService.getLeadSourceById(anyInt())).thenReturn(Optional.of(new LeadSourceMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setLeadSourceToLead("123", leads, leadSourceDaoService));
		assertNotNull(leads.getLeadSourceMaster());
	}

	@Test
	void testSetLeadSourceToLeadWithEmptyLeadSourceName() {
		LeadSourceDaoService leadSourceDaoService = mock(LeadSourceDaoService.class);
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setLeadSourceToLead("", leads, leadSourceDaoService));
		assertNull(leads.getLeadSourceMaster());
	}

	@Test
	void testSetLeadSourceToLeadWithOtherLeadSourceName() {
		LeadSourceDaoService leadSourceDaoService = mock(LeadSourceDaoService.class);
		when(leadSourceDaoService.getByName(anyString())).thenReturn(Optional.of(new LeadSourceMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setLeadSourceToLead("Other", leads, leadSourceDaoService));
		assertNotNull(leads.getLeadSourceMaster());
	}

	@Test
	void testSetLeadSourceToLeadWithValidLeadSourceName() throws Exception {
		LeadSourceDaoService leadSourceDaoService = mock(LeadSourceDaoService.class);
		when(leadSourceDaoService.save(any())).thenReturn(Optional.of(new LeadSourceDto()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setLeadSourceToLead("ValidLeadSourceName", leads, leadSourceDaoService));
		assertNotNull(leads.getLeadSourceMaster());
	}

	@Test
	void testSetDomainToLeadWithNumericDomainName() {
		DomainMasterDaoService domainMasterDaoService = mock(DomainMasterDaoService.class);
		when(domainMasterDaoService.findById(anyInt())).thenReturn(Optional.of(new DomainMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setDomainToLead("123", leads, domainMasterDaoService));
		assertNotNull(leads.getDomainMaster());
	}

	@Test
	void testSetDomainToLeadWithEmptyDomainName() {
		DomainMasterDaoService domainMasterDaoService = mock(DomainMasterDaoService.class);
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setDomainToLead("", leads, domainMasterDaoService));
		assertNull(leads.getDomainMaster());
	}

	@Test
	void testSetDomainToLeadWithOtherDomainName() {
		DomainMasterDaoService domainMasterDaoService = mock(DomainMasterDaoService.class);
		when(domainMasterDaoService.findByName(anyString())).thenReturn(Optional.of(new DomainMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setDomainToLead("Other", leads, domainMasterDaoService));
		assertNotNull(leads.getDomainMaster());
	}

	@Test
	void testSetDomainToLeadWithValidDomainName() {
		DomainMasterDaoService domainMasterDaoService = mock(DomainMasterDaoService.class);
		when(domainMasterDaoService.addDomain(any())).thenReturn(Optional.of(new DomainMaster()));
		Leads leads = new Leads();
		assertDoesNotThrow(() -> CommonUtil.setDomainToLead("ValidDomainName", leads, domainMasterDaoService));
		assertNotNull(leads.getDomainMaster());
	}

	@Test
	void testUpNextActivitiesNullInput() {
		Map<String, Object> result = CommonUtil.upNextActivities(null);
		assertEquals(null, result.get("MSG"));
		assertFalse(result.containsKey("UPNEXT_DATA_KEY"));
	}

	@Test
	void testUpNextActivitiesEmptyInput() {
		Map<String, Object> result = CommonUtil.upNextActivities(new LinkedHashMap<>());
		assertEquals(null, result.get("MSG"));
		assertFalse(result.containsKey("UPNEXT_DATA_KEY"));
	}

	@Test
	void testUpNextActivitiesOneEntry() {
		LinkedHashMap<Long, List<TimeLineActivityDto>> input = new LinkedHashMap<>();
		input.put(1L, Arrays.asList());
		Map<String, Object> result = CommonUtil.upNextActivities(input);
		assertEquals(null, result.get("MSG"));
		assertFalse(result.containsKey("UPNEXT_DATA_KEY"));
	}

	@Test
	void testUpNextActivitiesMultipleEntries() {
		LinkedHashMap<Long, List<TimeLineActivityDto>> input = new LinkedHashMap<>();
		input.put(1L, Arrays.asList());
		input.put(2L, Arrays.asList());
		Map<String, Object> result = CommonUtil.upNextActivities(input);
		assertEquals(null, result.get("MSG"));
		assertFalse(result.containsKey("UPNEXT_DATA_KEY"));
	}

	@Test
	void testGetDescData() {
		Description desc1 = new Description();
		desc1.setDescId(1);
		desc1.setSubject("Subject 1");
		desc1.setDesc("Description 1");
		desc1.setStatus("Status 1");
		desc1.setDate(LocalDate.now());
		desc1.setAction("Action 1");
		Description desc2 = new Description();
		desc2.setDescId(2);
		desc2.setSubject("Subject 2");
		desc2.setDesc("Description 2");
		desc2.setStatus("Status 2");
		desc2.setDate(LocalDate.now());
		desc2.setAction("Action 2");
		List<Description> descriptions = Arrays.asList(desc1, desc2);
		DescriptionDto dto1 = new DescriptionDto();
		dto1.setDescId(1);
		dto1.setSubject("Subject 1");
		dto1.setType("Description");
		dto1.setDesc("Description 1");
		dto1.setStatus("Status 1");
		dto1.setGetDate(LocalDate.now());
		dto1.setAction("Action 1");
		DescriptionDto dto2 = new DescriptionDto();
		dto2.setDescId(2);
		dto2.setSubject("Subject 2");
		dto2.setType("Description");
		dto2.setDesc("Description 2");
		dto2.setStatus("Status 2");
		dto2.setGetDate(LocalDate.now());
		dto2.setAction("Action 2");
		List<DescriptionDto> expected = Arrays.asList(dto1, dto2);
		List<DescriptionDto> actual = commonUtil.getDescData(descriptions);

		assertEquals(expected.size(), actual.size());
		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).getDescId(), actual.get(i).getDescId());
			assertEquals(expected.get(i).getSubject(), actual.get(i).getSubject());
			assertEquals(expected.get(i).getType(), actual.get(i).getType());
			assertEquals(expected.get(i).getDesc(), actual.get(i).getDesc());
			assertEquals(expected.get(i).getStatus(), actual.get(i).getStatus());
			assertEquals(expected.get(i).getGetDate(), actual.get(i).getGetDate());
			assertEquals(expected.get(i).getAction(), actual.get(i).getAction());
		}
	}

	@Test
	void testAddCurrencyDetailsWithCurrencyId() {
		Integer currencyId = 1;
		String currencyName = "USD";
		CurrencyMaster currencyMaster = new CurrencyMaster();
		when(currencyDaoService.findCurrency(currencyId)).thenReturn(Optional.of(currencyMaster));
		when(currencyDaoService.findCurrencyByName(currencyName)).thenReturn(Optional.of(currencyMaster));
		Optional<CurrencyMaster> result = commonUtil.addCurrencyDetails(currencyDaoService, null, null, currencyId);
		assertEquals(Optional.of(currencyMaster), result);
		verify(currencyDaoService, times(1)).findCurrency(currencyId);
		verifyNoMoreInteractions(currencyDaoService);
	}

	@Test
	void testAddCurrencyDetailsWithCurrencySymbolAndNameExisting() {
		String currencySymbol = "$";
		String currencyName = "USD";
		CurrencyMaster currencyMaster = new CurrencyMaster();
		when(currencyDaoService.findCurrencyBySymbol(currencySymbol)).thenReturn(Optional.of(currencyMaster));
		assertEquals(Optional.of(currencyMaster),
				commonUtil.addCurrencyDetails(currencyDaoService, currencySymbol, currencyName, null));
	}

	@Test
	void testAddCurrencyDetailsWithCurrencySymbolAndNameNotExisting() {
		String currencySymbol = "$";
		String currencyName = "USD";
		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyName(currencyName);
		currencyMaster.setCurrencyCode(currencyName);
		currencyMaster.setCurrencySymbol(currencySymbol);
		when(currencyDaoService.findCurrencyByName(currencyName)).thenReturn(empty());
		when(currencyDaoService.findCurrencyBySymbol(currencySymbol)).thenReturn(Optional.of(currencyMaster));
		when(currencyDaoService.addCurrency(new CurrencyMaster())).thenReturn(currencyMaster);
		commonUtil.addCurrencyDetails(currencyDaoService, currencySymbol, currencyName, null);
		verify(currencyDaoService, times(1)).findCurrencyBySymbol(currencySymbol);
	}

	@Test
	void testAddCurrencyDetails_currencySymbolNull_currencyNamePresent() {
		String currencySymbol = null;
		String currencyName = "USD";
		Integer currencyId = null;
		CurrencyMaster expectedCurrencyMaster = new CurrencyMaster();
		expectedCurrencyMaster.setCurrencyName(currencyName);
		Optional<CurrencyMaster> expectedOptional = Optional.of(expectedCurrencyMaster);
		when(currencyDaoService.findCurrencyByName(anyString())).thenReturn(expectedOptional);
		Optional<CurrencyMaster> result = commonUtil.addCurrencyDetails(currencyDaoService, currencySymbol,
				currencyName, currencyId);
		assertTrue(result.isPresent());
		assertTrue(result.get().getCurrencyName().equals(currencyName));
	}

	@Test
	void testAddCurrencyDetailsWithNullInputs() {
		assertEquals(empty(), commonUtil.addCurrencyDetails(currencyDaoService, null, null, null));
	}
}
