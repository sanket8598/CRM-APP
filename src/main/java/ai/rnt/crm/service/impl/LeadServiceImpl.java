package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.ACTIVITY;
import static ai.rnt.crm.constants.CRMConstants.ALL_TASK;
import static ai.rnt.crm.constants.CRMConstants.ALL_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.ASSIGN_DATA;
import static ai.rnt.crm.constants.CRMConstants.COMPLETED_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.COMPLETED_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.CONTACT;
import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.CRMConstants.COUNT_BY_STATUS;
import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.IN_PROGRESS_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.IN_PROGRESS_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.LEAD_ID;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE_DATA;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE_MASTER;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE_NAME;
import static ai.rnt.crm.constants.CRMConstants.NOT_STARTED_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.NOT_STARTED_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.ON_HOLD_TASK_COUNT;
import static ai.rnt.crm.constants.CRMConstants.ON_HOLD_TASK_KEY;
import static ai.rnt.crm.constants.CRMConstants.OTHER;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALLS_MASTER;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL_DATA;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL_ID;
import static ai.rnt.crm.constants.CRMConstants.SORT_FILTER;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.CRMConstants.TASK;
import static ai.rnt.crm.constants.CRMConstants.TIMELINE;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA_KEY;
import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
import static ai.rnt.crm.constants.ExcelConstants.FLAG;
import static ai.rnt.crm.constants.ExcelConstants.LEAD_DATA;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.TOPIC;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static ai.rnt.crm.constants.MessageConstants.NO_ACTIVITY;
import static ai.rnt.crm.constants.MessageConstants.WAIT_FOR;
import static ai.rnt.crm.constants.StatusConstants.ALL;
import static ai.rnt.crm.constants.StatusConstants.ALL_LEAD;
import static ai.rnt.crm.constants.StatusConstants.CALL;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_DISQUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_LEAD;
import static ai.rnt.crm.constants.StatusConstants.DISQUALIFIED_LEAD;
import static ai.rnt.crm.constants.StatusConstants.EMAIL;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.constants.StatusConstants.MEETING;
import static ai.rnt.crm.constants.StatusConstants.OPEN;
import static ai.rnt.crm.constants.StatusConstants.OPEN_LEAD;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED_LEAD;
import static ai.rnt.crm.constants.StatusConstants.VISIT;
import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employees;
import static ai.rnt.crm.dto_mapper.LeadSortFilterDtoMapper.TO_LEAD_SORT_FILTER;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE_DTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_LEADDTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_EDITLEAD_DTO;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD_DTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_QUALIFY_LEAD;
import static ai.rnt.crm.dto_mapper.MeetingAttachmentDtoMapper.TO_METTING_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_CALL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_EMAIL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_MEETING;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ACTIVITY_VISIT;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ASSIGNED_TO_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.CLOSE_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.DISQUALIFIED_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.OPEN_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.QUALIFIED_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_CALL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_EMAIL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_MEETING;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.TIMELINE_VISIT;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.UPNEXT_CALL;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.UPNEXT_MEETING;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.UPNEXT_VISIT;
import static ai.rnt.crm.functional.predicates.TaskPredicates.COMPLETED_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.IN_PROGRESS_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.NOT_STARTED_TASK;
import static ai.rnt.crm.functional.predicates.TaskPredicates.ON_HOLD_TASK;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDate;
import static ai.rnt.crm.util.ConvertDateFormatUtil.convertDateDateWithTime;
import static ai.rnt.crm.util.LeadsCardUtil.checkDuplicateLead;
import static ai.rnt.crm.util.LeadsCardUtil.shortName;
import static java.lang.Integer.parseInt;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.util.Collections.emptyList;
import static java.util.Comparator.naturalOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.poi.ss.usermodel.WorkbookFactory.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.constants.ApiResponseKeyConstant;
import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.ExcelHeaderDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSortFilterDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.EditEmailDto;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.EditMeetingDto;
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.dto.MainTaskDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.ExcelHeaderMaster;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.functional.custominterface.impl.LeadsCardMapperImpl;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.LeadsCardUtil;
import ai.rnt.crm.util.ReadExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is the implementation of the leadService interface method.<br>
 * {@code All Business Logic Of Lead Module Is Inside This Class}
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class LeadServiceImpl implements LeadService {

	private final LeadDaoService leadDaoService;
	private final ServiceFallsDaoSevice serviceFallsDaoSevice;
	private final LeadSourceDaoService leadSourceDaoService;
	private final CompanyMasterDaoService companyMasterDaoService;
	private final EmployeeService employeeService;
	private final RoleMasterDaoService roleMasterDaoService;
	private final CallDaoService callDaoService;
	private final EmailDaoService emailDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final VisitDaoService visitDaoService;
	private final CityDaoService cityDaoService;
	private final StateDaoService stateDaoService;
	private final CountryDaoService countryDaoService;
	private final LeadSortFilterDaoService leadSortFilterDaoService;
	private final ReadExcelUtil readExcelUtil;
	private final ExcelHeaderDaoService excelHeaderDaoService;
	private final MeetingDaoService meetingDaoService;

	private static final String PRIMFIELD = "PrimaryField";
	private static final String SECNDFIELD = "SecondaryField";

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(LeadDto leadDto) {
		EnumMap<ApiResponse, Object> createMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads leads = TO_LEAD.apply(leadDto).orElseThrow(ResourceNotFoundException::new);
			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(leadDto.getCompanyName());
			setCompanyDetailsToLead(existCompany, leadDto, leads);
			leads.setStatus(OPEN);
			leads.setDisqualifyAs(OPEN);
			leads.setPseudoName(auditAwareUtil.getLoggedInUserName());
			setServiceFallsIntoAndLeadSourceToLead(leadDto.getServiceFallsId(), leadDto.getLeadSourceId(), leads);
			if (nonNull(leadDto.getAssignTo()))
				employeeService.getById(leadDto.getAssignTo()).ifPresent(leads::setEmployee);
			else
				employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(leads::setEmployee);
			createMap.put(SUCCESS, true);
			if (LeadsCardUtil.checkDuplicateLead(leadDaoService.getAllLeads(), leads)) {
				createMap.put(MESSAGE, "Lead Already Exists !!");
				createMap.put(SUCCESS, false);
			} else if (nonNull(leadDaoService.addLead(leads)))
				createMap.put(MESSAGE, "Lead Added Successfully !!");
			else
				createMap.put(MESSAGE, "Lead Not Added !!");
			return new ResponseEntity<>(createMap, CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("Got Exception while creating new lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadsByStatus(String leadsStatus) {
		EnumMap<ApiResponse, Object> getAllLeads = new EnumMap<>(ApiResponse.class);
		getAllLeads.put(SUCCESS, true);
		try {
			if (isNull(leadsStatus)) {
				Map<String, Object> dataMap = new HashMap<>();
				Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
				List<Leads> impLeads = leadDaoService.findLeadByEmployeeStaffId(loggedInStaffId).stream()
						.map(LeadImportant::getLead)
						.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate())).collect(toList());
				List<Leads> allLeads = leadDaoService.getAllLeads();
				allLeads.stream().forEach(lead -> {
					if (impLeads.contains(lead))
						lead.setImportant(true);
				});
				Comparator<Leads> importantLeads = (l1, l2) -> l2.getImportant().compareTo(l1.getImportant());
				allLeads.sort(
						importantLeads.thenComparing((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate())));
				Map<String, Object> filterMap = new HashMap<>();
				filterMap.put(PRIMFIELD, LEAD_NAME);
				filterMap.put(SECNDFIELD, TOPIC);
				leadSortFilterDaoService.findSortFilterByEmployeeStaffId(loggedInStaffId).ifPresent(sortFilter -> {
					filterMap.put(PRIMFIELD, sortFilter.getPrimaryFilter());
					filterMap.put(SECNDFIELD, sortFilter.getSecondaryFilter());
				});
				dataMap.put(SORT_FILTER, filterMap);
				String primaryField = filterMap.get(PRIMFIELD).toString();
				String secondaryField = filterMap.get(SECNDFIELD).toString();
				if (auditAwareUtil.isAdmin()) {
					dataMap.put(ALL_LEAD, allLeads.stream().map(
							lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField, secondaryField))
							.collect(toList()));
					dataMap.put(OPEN_LEAD, allLeads.stream().filter(OPEN_LEAD_FILTER).map(
							lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField, secondaryField))
							.collect(toList()));
					dataMap.put(CLOSE_LEAD, allLeads.stream().filter(CLOSE_LEAD_FILTER).map(
							lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField, secondaryField))
							.collect(toList()));
				} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
					dataMap.put(ALL_LEAD,
							allLeads.stream().filter(l -> ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
									.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
											secondaryField))
									.collect(toList()));
					dataMap.put(OPEN_LEAD, allLeads.stream()
							.filter(l -> OPEN_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
									secondaryField))
							.collect(toList()));
					dataMap.put(CLOSE_LEAD, allLeads.stream()
							.filter(l -> CLOSE_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
									secondaryField))
							.collect(toList()));
				} else
					dataMap.put(ApiResponseKeyConstant.DATA, emptyList());
				getAllLeads.put(DATA, dataMap);
			} else
				getAllLeads.put(DATA, TO_LEAD_DTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus)));
			return new ResponseEntity<>(getAllLeads, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting leads dashboard data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllLeadSource() {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			resultMap.put(SUCCESS, true);
			resultMap.put(DATA, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			return new ResponseEntity<>(resultMap, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting lead source..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllDropDownData() {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			log.info("user looged in..{}",auditAwareUtil.getLoggedInStaffId());
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put(SERVICE_FALL_DATA,
					TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put(LEAD_SOURCE_DATA, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put(ASSIGN_DATA, TO_Employees.apply(roleMasterDaoService.getAdminAndUser()));
			resultMap.put(SUCCESS, true);
			resultMap.put(DATA, dataMap);
			return new ResponseEntity<>(resultMap, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting all dropdown data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardData() {
		EnumMap<ApiResponse, Object> leadsDashboardData = new EnumMap<>(ApiResponse.class);
		try {
			leadsDashboardData.put(SUCCESS, true);
			leadsDashboardData.put(DATA, TO_DASHBOARD_LEADDTOS.apply(leadDaoService.getLeadDashboardData()));
			return new ResponseEntity<>(leadsDashboardData, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting lead DashboardData..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardDataByStatus(String leadsStatus) {
		EnumMap<ApiResponse, Object> leadsDataByStatus = new EnumMap<>(ApiResponse.class);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			List<Leads> leadDashboardData = leadDaoService.getLeadDashboardData();
			Map<String, Object> countMap = new HashMap<>();
			Map<String, Object> dataMap = new HashMap<>();
			if (auditAwareUtil.isAdmin()) {
				countMap.put(ALL_LEAD, leadDashboardData.stream().count());
				countMap.put(OPEN_LEAD, leadDashboardData.stream().filter(OPEN_LEAD_FILTER).count());
				countMap.put(QUALIFIED_LEAD, leadDashboardData.stream().filter(QUALIFIED_LEAD_FILTER).count());
				countMap.put(DISQUALIFIED_LEAD, leadDashboardData.stream().filter(DISQUALIFIED_LEAD_FILTER).count());
				dataMap.put(COUNTDATA, countMap);
				if (nonNull(leadsStatus) && leadsStatus.equalsIgnoreCase(ALL)) {
					dataMap.put(ApiResponseKeyConstant.DATA, TO_DASHBOARD_LEADDTOS.apply(leadDashboardData));
					leadsDataByStatus.put(DATA, dataMap);
				} else {
					dataMap.put(ApiResponseKeyConstant.DATA, TO_DASHBOARD_LEADDTOS
							.apply(leadDaoService.getLeadsByStatus(leadsStatus).stream().collect(Collectors.toList())));
					leadsDataByStatus.put(DATA, dataMap);
				}
			} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
				countMap.put(ALL_LEAD,
						leadDashboardData.stream().filter(d -> ASSIGNED_TO_FILTER.test(d, loggedInStaffId)).count());
				countMap.put(OPEN_LEAD, leadDashboardData.stream()
						.filter(l -> OPEN_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId)).count());
				countMap.put(QUALIFIED_LEAD,
						leadDashboardData.stream().filter(
								l -> QUALIFIED_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
								.count());
				countMap.put(DISQUALIFIED_LEAD,
						leadDashboardData.stream().filter(
								l -> DISQUALIFIED_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
								.count());
				dataMap.put(COUNTDATA, countMap);
				if (nonNull(leadsStatus) && leadsStatus.equalsIgnoreCase(ALL)) {
					dataMap.put(ApiResponseKeyConstant.DATA, TO_DASHBOARD_LEADDTOS.apply(leadDashboardData.stream()
							.filter(d -> ASSIGNED_TO_FILTER.test(d, loggedInStaffId)).collect(toList())));
					leadsDataByStatus.put(DATA, dataMap);
				} else {
					dataMap.put(ApiResponseKeyConstant.DATA,
							TO_DASHBOARD_LEADDTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus).stream()
									.filter(d -> ASSIGNED_TO_FILTER.test(d, loggedInStaffId)).collect(toList())));
					leadsDataByStatus.put(DATA, dataMap);
				}
			} else
				leadsDataByStatus.put(DATA, emptyList());

			leadsDataByStatus.put(SUCCESS, true);
			return new ResponseEntity<>(leadsDataByStatus, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting LeadDashboardDataByStatus..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(Integer leadId) {
		EnumMap<ApiResponse, Object> lead = new EnumMap<>(ApiResponse.class);
		try {
			Map<String, Object> dataMap = new LinkedHashMap<>();

			Leads leadById = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			Optional<EditLeadDto> dto = TO_EDITLEAD_DTO.apply(leadById);
			dto.ifPresent(e -> {
				e.setMessage("Assigned To " + leadById.getEmployee().getFirstName() + " "
						+ leadById.getEmployee().getLastName());
				EmployeeMaster employeeMaster = employeeService.getById(leadById.getCreatedBy())
						.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, leadById.getCreatedBy()));
				e.setGeneratedBy(employeeMaster.getFirstName() + " " + employeeMaster.getLastName());
			});
			List<Call> calls = callDaoService.getCallsByLeadId(leadId);
			List<Visit> visits = visitDaoService.getVisitsByLeadId(leadId);
			List<Email> emails = emailDaoService.getEmailByLeadId(leadId);
			List<Meetings> meetings = meetingDaoService.getMeetingByLeadId(leadId);
			List<TimeLineActivityDto> timeLine = new ArrayList<>();
			List<EditCallDto> list = calls.stream().filter(TIMELINE_CALL).map(call -> {
				EditCallDto callDto = new EditCallDto();
				callDto.setId(call.getCallId());
				callDto.setSubject(call.getSubject());
				callDto.setType(CALL);
				callDto.setBody(call.getComment());
				callDto.setCreatedOn(convertDate(call.getUpdatedDate()));
				callDto.setShortName(shortName(call.getCallTo()));
				TO_EMPLOYEE.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
				return callDto;
			}).collect(toList());
			timeLine.addAll(list);
			timeLine.addAll(emails.stream().filter(TIMELINE_EMAIL).map(email -> {
				EditEmailDto editEmailDto = new EditEmailDto();
				editEmailDto.setId(email.getMailId());
				editEmailDto.setType(EMAIL);
				editEmailDto.setSubject(email.getSubject());
				editEmailDto.setBody(email.getContent());
				editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
				editEmailDto.setCreatedOn(convertDate(email.getUpdatedDate()));
				editEmailDto.setShortName(shortName(email.getMailFrom()));
				return editEmailDto;
			}).collect(toList()));
			timeLine.addAll(visits.stream().filter(TIMELINE_VISIT).map(visit -> {
				EditVisitDto visitDto = new EditVisitDto();
				visitDto.setId(visit.getVisitId());
				visitDto.setLocation(visit.getLocation());
				visitDto.setSubject(visit.getSubject());
				visitDto.setType(VISIT);
				visitDto.setBody(visit.getContent());
				employeeService.getById(visit.getCreatedBy()).ifPresent(
						byId -> visitDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				visitDto.setCreatedOn(convertDate(visit.getUpdatedDate()));
				return visitDto;
			}).collect(toList()));
			timeLine.addAll(meetings.stream().filter(TIMELINE_MEETING).map(meet -> {
				EditMeetingDto meetDto = new EditMeetingDto();
				meetDto.setId(meet.getMeetingId());
				meetDto.setType(MEETING);
				employeeService.getById(meet.getCreatedBy()).ifPresent(
						byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				meetDto.setSubject(meet.getMeetingTitle());
				meetDto.setBody(meet.getDescription());
				meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
				meetDto.setCreatedOn(convertDate(meet.getUpdatedDate()));
				return meetDto;
			}).collect(toList()));
			timeLine.sort((t1, t2) -> parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			List<TimeLineActivityDto> activity = calls.stream().filter(ACTIVITY_CALL).map(call -> {
				EditCallDto callDto = new EditCallDto();
				callDto.setId(call.getCallId());
				callDto.setSubject(call.getSubject());
				callDto.setType(CALL);
				callDto.setBody(call.getComment());
				callDto.setShortName(shortName(call.getCallTo()));
				callDto.setDueDate(convertDateDateWithTime(call.getStartDate(), call.getStartTime()));
				callDto.setCreatedOn(convertDate(call.getCreatedDate()));
				TO_EMPLOYEE.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
				return callDto;
			}).collect(toList());
			activity.addAll(emails.stream().filter(ACTIVITY_EMAIL).map(email -> {
				EditEmailDto editEmailDto = new EditEmailDto();
				editEmailDto.setId(email.getMailId());
				editEmailDto.setType(EMAIL);
				editEmailDto.setSubject(email.getSubject());
				editEmailDto.setBody(email.getContent());
				editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
				editEmailDto.setCreatedOn(convertDate(email.getCreatedDate()));
				editEmailDto.setShortName(shortName(email.getMailFrom()));
				return editEmailDto;
			}).collect(toList()));
			activity.addAll(visits.stream().filter(ACTIVITY_VISIT).map(visit -> {
				EditVisitDto editVisitDto = new EditVisitDto();
				editVisitDto.setId(visit.getVisitId());
				editVisitDto.setLocation(visit.getLocation());
				editVisitDto.setSubject(visit.getSubject());
				editVisitDto.setType(VISIT);
				editVisitDto.setBody(visit.getContent());
				editVisitDto.setDueDate(convertDateDateWithTime(visit.getStartDate(), visit.getStartTime()));
				employeeService.getById(visit.getCreatedBy()).ifPresent(
						byId -> editVisitDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				editVisitDto.setCreatedOn(convertDate(visit.getCreatedDate()));
				return editVisitDto;
			}).collect(toList()));
			activity.addAll(meetings.stream().filter(ACTIVITY_MEETING).map(meet -> {
				EditMeetingDto meetDto = new EditMeetingDto();
				meetDto.setId(meet.getMeetingId());
				meetDto.setType(MEETING);
				employeeService.getById(meet.getCreatedBy()).ifPresent(
						byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				meetDto.setSubject(meet.getMeetingTitle());
				meetDto.setBody(meet.getDescription());
				meetDto.setDueDate(convertDateDateWithTime(meet.getStartDate(), meet.getStartTime()));
				meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
				meetDto.setCreatedOn(convertDate(meet.getCreatedDate()));
				return meetDto;
			}).collect(toList()));
			activity.sort((t1, t2) -> parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			List<TimeLineActivityDto> upNext = calls.stream().filter(UPNEXT_CALL).map(call -> {
				EditCallDto callDto = new EditCallDto();
				callDto.setId(call.getCallId());
				callDto.setSubject(call.getSubject());
				callDto.setType(CALL);
				callDto.setBody(call.getComment());
				callDto.setCreatedOn(convertDateDateWithTime(call.getStartDate(), call.getStartTime()));
				TO_EMPLOYEE.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
				return callDto;
			}).collect(toList());

			upNext.addAll(visits.stream().filter(UPNEXT_VISIT).map(visit -> {
				EditVisitDto editVisitDto = new EditVisitDto();
				editVisitDto.setId(visit.getVisitId());
				editVisitDto.setLocation(visit.getLocation());
				editVisitDto.setSubject(visit.getSubject());
				editVisitDto.setType(VISIT);
				editVisitDto.setBody(visit.getContent());
				employeeService.getById(visit.getCreatedBy()).ifPresent(
						byId -> editVisitDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				editVisitDto.setCreatedOn(convertDateDateWithTime(visit.getStartDate(), visit.getStartTime()));
				return editVisitDto;
			}).collect(toList()));
			upNext.addAll(meetings.stream().filter(UPNEXT_MEETING).map(meet -> {
				EditMeetingDto meetDto = new EditMeetingDto();
				meetDto.setId(meet.getMeetingId());
				meetDto.setType(MEETING);
				employeeService.getById(meet.getCreatedBy()).ifPresent(
						byId -> meetDto.setShortName(shortName(byId.getFirstName() + " " + byId.getLastName())));
				meetDto.setSubject(meet.getMeetingTitle());
				meetDto.setBody(meet.getDescription());
				meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
				meetDto.setCreatedOn(convertDateDateWithTime(meet.getStartDate(), meet.getStartTime()));
				return meetDto;
			}).collect(toList()));
			upNext.sort((t1, t2) -> parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			LinkedHashMap<Long, List<TimeLineActivityDto>> upNextActivities = upNext.stream()
					.collect(groupingBy(e -> DAYS.between(now(), parse(e.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM))))
					.entrySet().stream().sorted(comparingByKey()).collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
							(oldValue, newValue) -> oldValue, LinkedHashMap::new));
			dataMap.put(CONTACT, dto);
			dataMap.put(SERVICE_FALL, TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put(LEAD_SOURCE, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put(TIMELINE, timeLine);
			dataMap.put(ACTIVITY, activity);
			dataMap.put(UPNEXT_DATA, upNextActivities(upNextActivities));
			dataMap.put(TASK, getTaskDataMap(calls, visits, meetings, leadById));
			lead.put(SUCCESS, true);
			lead.put(DATA, dataMap);
			return new ResponseEntity<>(lead, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while editing the lead data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> qualifyLead(Integer leadId, QualifyLeadDto dto) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			result.put(SUCCESS, true);
			result.put(MESSAGE, "Lead Not Qualify");
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setCustomerNeed(dto.getCustomerNeed());
			lead.setProposedSolution(dto.getProposedSolution());
			lead.setStatus(CLOSE_AS_QUALIFIED);
			lead.setDisqualifyAs(QUALIFIED);
			setServiceFallsIntoAndLeadSourceToLead(dto.getServiceFallsMaster().getServiceName(), null, lead);
			if (nonNull(leadDaoService.addLead(lead)))
				result.put(MESSAGE, "Lead Qualified SuccessFully");
			else
				result.put(SUCCESS, false);
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while qualifying the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLead(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign lead staffId: {} LeadId:{}", map.get(LEAD_ID));
		try {
			Leads leads = leadDaoService.getLeadById(map.get(LEAD_ID))
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, map.get(LEAD_ID)));
			EmployeeMaster employee = employeeService.getById(map.get(STAFF_ID))
					.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, map.get(STAFF_ID)));
			leads.setEmployee(employee);
			if (nonNull(leadDaoService.addLead(leads))) {
				resultMap.put(MESSAGE, "Lead Assigned SuccessFully");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Lead Not Assigned");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.info("Got Exception while assigning the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> disQualifyLead(Integer leadId, LeadDto dto) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setDisqualifyAs(dto.getDisqualifyAs());
			lead.setDisqualifyReason(dto.getDisqualifyReason());
			lead.setStatus(CLOSE_AS_DISQUALIFIED);
			if (nonNull(leadDaoService.addLead(lead))) {
				result.put(MESSAGE, "Lead Disqualified SuccessFully");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Lead Not Disqualify");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while disQualifyLead the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadContact(Integer leadId, UpdateLeadDto dto) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		CountryMaster country = new CountryMaster();
		StateMaster state = new StateMaster();
		CityMaster city = new CityMaster();
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setTopic(dto.getTopic());
			lead.setPhoneNumber(dto.getPhoneNumber());
			lead.setEmail(dto.getEmail());
			lead.setBudgetAmount(dto.getBudgetAmount());
			lead.setCustomerNeed(dto.getCustomerNeed());
			lead.setProposedSolution(dto.getProposedSolution());
			lead.setPseudoName(dto.getPseudoName());
			Optional<CityMaster> existCityByName = cityDaoService.existCityByName(dto.getCity());
			Optional<StateMaster> findBystate = stateDaoService.findBystate(dto.getState());
			Optional<CountryMaster> findByCountryName = countryDaoService.findByCountryName(dto.getCountry());
			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(dto.getCompanyName());
			if (existCompany.isPresent()) {
				CompanyMaster companyMaster = TO_COMPANY.apply(existCompany.orElseThrow(ResourceNotFoundException::new))
						.orElseThrow(ResourceNotFoundException::new);
				companyMaster.setCompanyWebsite(dto.getCompanyWebsite());
				if (findByCountryName.isPresent())
					findByCountryName.ifPresent(companyMaster::setCountry);
				else {
					country = new CountryMaster();
					country.setCountry(dto.getCountry());
					Optional<CountryMaster> newFindByCountryName = of(countryDaoService.addCountry(country));
					newFindByCountryName.ifPresent(companyMaster::setCountry);
				}
				if (findBystate.isPresent())
					findBystate.ifPresent(companyMaster::setState);
				else {
					state = new StateMaster();
					state.setState(dto.getState());
					Optional<StateMaster> newFindBystate = of(stateDaoService.addState(state));
					newFindBystate.ifPresent(companyMaster::setState);
				}
				if (existCityByName.isPresent())
					existCityByName.ifPresent(companyMaster::setCity);
				else {
					city = new CityMaster();
					city.setCity(dto.getCity());
					Optional<CityMaster> newExistCityByName = of(cityDaoService.addCity(city));
					newExistCityByName.ifPresent(companyMaster::setCity);
				}
				companyMaster.setZipCode(dto.getZipCode());
				companyMaster.setAddressLineOne(dto.getAddressLineOne());
				TO_COMPANY
						.apply(companyMasterDaoService.save(companyMaster).orElseThrow(ResourceNotFoundException::new))
						.ifPresent(lead::setCompanyMaster);
			} else {
				CompanyMaster companyMaster = TO_COMPANY
						.apply(CompanyDto.builder().companyName(dto.getCompanyName())
								.companyWebsite(dto.getCompanyWebsite()).build())
						.orElseThrow(ResourceNotFoundException::new);
				if (findByCountryName.isPresent())
					findByCountryName.ifPresent(companyMaster::setCountry);
				else {
					country = new CountryMaster();
					country.setCountry(dto.getCountry());
					Optional<CountryMaster> newFindByCountryName = of(countryDaoService.addCountry(country));
					newFindByCountryName.ifPresent(companyMaster::setCountry);
				}
				if (findBystate.isPresent())
					findBystate.ifPresent(companyMaster::setState);
				else {
					state = new StateMaster();
					state.setState(dto.getState());
					Optional<StateMaster> newFindBystate = of(stateDaoService.addState(state));
					newFindBystate.ifPresent(companyMaster::setState);
				}
				if (existCityByName.isPresent())
					existCityByName.ifPresent(companyMaster::setCity);
				else {
					city = new CityMaster();
					city.setCity(dto.getCity());
					Optional<CityMaster> newExistCityByName = of(cityDaoService.addCity(city));
					newExistCityByName.ifPresent(companyMaster::setCity);
				}
				companyMaster.setZipCode(dto.getZipCode());
				companyMaster.setAddressLineOne(dto.getAddressLineOne());
				TO_COMPANY
						.apply(companyMasterDaoService.save(companyMaster).orElseThrow(ResourceNotFoundException::new))
						.ifPresent(lead::setCompanyMaster);
			}
			setServiceFallsIntoAndLeadSourceToLead(dto.getServiceFallsId(), dto.getLeadSourceId(), lead);
			if (nonNull(leadDaoService.addLead(lead)))
				result.put(MESSAGE, "Leads Contact Updated Successfully !!");
			else
				result.put(MESSAGE, "Leads Contact Not Updated !!");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while updateLeadContact..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> importantLead(Integer leadId, boolean status) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		result.put(SUCCESS, true);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			if (status) {
				LeadImportant importantLead = new LeadImportant();
				importantLead.setLead(leadDaoService.getLeadById(leadId)
						.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId)));
				importantLead.setEmployee(employeeService.getById(loggedInStaffId)
						.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, loggedInStaffId)));
				result.put(MESSAGE, "Problem Occurred While Making Lead Important !!");
				if (leadDaoService.addImportantLead(importantLead).isPresent())
					result.put(MESSAGE, "Lead marked as Important !!");
			} else {
				result.put(MESSAGE, "Lead not found as important !!");
				if (leadDaoService.deleteImportantLead(leadId, loggedInStaffId))
					result.put(MESSAGE, "Lead marked as Unimportant !!");
			}
		} catch (Exception e) {
			log.error("error occured while updating the Lead in ImportandLead API... {}", e.getMessage());
			if (e instanceof DataIntegrityViolationException)
				result.put(MESSAGE, "Lead already marked as important !!");
			else
				throw new CRMException(e);
		}
		return new ResponseEntity<>(result, CREATED);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> reactiveLead(Integer leadId) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setDisqualifyAs(OPEN);
			lead.setDisqualifyReason(null);
			lead.setStatus(OPEN);
			if (nonNull(leadDaoService.addLead(lead))) {
				result.put(MESSAGE, "Lead Reactivate SuccessFully");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Lead Not Reactivate");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while reactivating the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addSortFilterForLeads(LeadSortFilterDto sortFilter) {
		try {
			EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			sortFilter.setEmployee(TO_EMPLOYEE
					.apply(employeeService.getById(loggedInStaffId)
							.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, loggedInStaffId)))
					.orElseThrow(null));
			if (nonNull(leadSortFilterDaoService.save(TO_LEAD_SORT_FILTER.apply(sortFilter).orElse(null)))) {
				result.put(MESSAGE, "Lead Sort Filter Added Successfully!!");
				result.put(SUCCESS, true);
			} else {
				result.put(MESSAGE, "Lead Sort Filter Not Added!!");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while adding the sort filter for lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getForQualifyLead(Integer leadId) {
		try {
			EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
			result.put(DATA, TO_QUALIFY_LEAD.apply(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId))));
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, FOUND);
		} catch (Exception e) {
			log.info("Got Exception while getting the Qualified lead for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> uploadExcel(MultipartFile file) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			result.put(SUCCESS, false);
			Workbook workbook = create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			if (isValidExcel(sheet)) {
				Map<String, Object> excelData = readExcelUtil.readExcelFile(workbook, sheet);
				if ((nonNull(excelData) && !excelData.isEmpty())
						&& (excelData.containsKey(FLAG) && (boolean) excelData.get(FLAG))) {
					List<LeadDto> leadDtos = (List<LeadDto>) excelData.get(LEAD_DATA);
					int saveLeadCount = leadDtos.stream().filter(Objects::nonNull).mapToInt(leadDto -> {
						Leads leads = buildLeadObj(leadDto);
						setAssignToNameForTheLead(leads,
								nonNull(leadDto.getPseudoName()) && !leadDto.getPseudoName().isEmpty()
										? leadDto.getPseudoName().split(" ")
										: auditAwareUtil.getLoggedInUserName().split(" "));
						leads.setPseudoName(null);// because we added the assign to person name from excel data object
						if (checkDuplicateLead(leadDaoService.getAllLeads(), leads))
							return 0;
						return nonNull(leadDaoService.addLead(leads)) ? 1 : 0;
					}).sum();
					int duplicateLead = leadDtos.size() - saveLeadCount;
					result.put(SUCCESS, saveLeadCount > 0);
					result.put(MESSAGE, saveLeadCount + " Leads Added And " + duplicateLead + " Duplicate Found!!");
					if (duplicateLead == 0 && saveLeadCount == 0)
						result.put(MESSAGE, "No Lead Found To Add !!");
					return new ResponseEntity<>(result, CREATED);
				} else {
					result.put(MESSAGE, excelData.get(MSG));
					return new ResponseEntity<>(result, BAD_REQUEST);
				}
			} else {
				result.put(MESSAGE, "Invalid Excel Format!!");
				return new ResponseEntity<>(result, BAD_REQUEST);
			}
		} catch (Exception e) {
			log.info("Got Exception while uploading the Excel of lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public boolean isValidExcel(Sheet sheet) throws IOException {
		List<String> dbHeaderNames = excelHeaderDaoService.getExcelHeadersFromDB().stream()
				.map(ExcelHeaderMaster::getHeaderName).filter(Objects::nonNull).map(String::trim).collect(toList());
		List<String> excelHeader = readExcelUtil.readExcelHeaders(sheet).stream().filter(Objects::nonNull)
				.map(String::trim).collect(toList());
		return (nonNull(excelHeader) && !excelHeader.isEmpty()) && (nonNull(dbHeaderNames) && !dbHeaderNames.isEmpty())
				&& excelHeader.stream().allMatch(dbHeaderNames::contains) && excelHeader.containsAll(dbHeaderNames);
	}

	public Leads buildLeadObj(LeadDto leadDto) {
		Leads leads = TO_LEAD.apply(leadDto).orElseThrow(ResourceNotFoundException::new);
		try {
			leads.setStatus(OPEN);
			leads.setDisqualifyAs(OPEN);
			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(leadDto.getCompanyName());
			setCompanyDetailsToLead(existCompany, leadDto, leads);
			if (nonNull(leadDto.getServiceFallsId()) && !leadDto.getServiceFallsId().isEmpty()) {
				Optional<ServiceFallsMaster> serviceFalls = serviceFallsDaoSevice
						.findByName(leadDto.getServiceFallsId());
				if (serviceFalls.isPresent())
					serviceFalls.ifPresent(leads::setServiceFallsMaster);
				else {
					ServiceFallsMaster serviceFall = new ServiceFallsMaster();
					serviceFall.setServiceName(leadDto.getServiceFallsId());
					TO_SERVICE_FALL_MASTER
							.apply(serviceFallsDaoSevice.save(serviceFall).orElseThrow(ResourceNotFoundException::new))
							.ifPresent(leads::setServiceFallsMaster);
				}
			}
			if (nonNull(leadDto.getLeadSourceId()) && !leadDto.getLeadSourceId().isEmpty()) {
				Optional<LeadSourceMaster> leadSource = leadSourceDaoService.getByName(leadDto.getLeadSourceId());
				if (leadSource.isPresent())
					leadSource.ifPresent(leads::setLeadSourceMaster);
				else {
					LeadSourceMaster leadSources = new LeadSourceMaster();
					leadSources.setSourceName(leadDto.getLeadSourceId());
					TO_LEAD_SOURCE
							.apply(leadSourceDaoService.save(leadSources).orElseThrow(ResourceNotFoundException::new))
							.ifPresent(leads::setLeadSourceMaster);
				}
			} else
				leadSourceDaoService.getByName(OTHER).ifPresent(leads::setLeadSourceMaster);
		} catch (Exception e) {
			log.error("error while building the lead Object...{}", e.getMessage());
		}
		return leads;
	}

	public void setAssignToNameForTheLead(Leads leads, String[] split) {
		String firstName;
		String lastName = null;
		if (split.length > 1) {
			firstName = split[0];
			lastName = split[1];
		} else
			firstName = split[0];
		employeeService.findByName(firstName, lastName).ifPresent(leads::setEmployee);
	}

	public List<MainTaskDto> getCallRelatedTasks(List<Call> calls) {
		return calls.stream().flatMap(call -> call.getCallTasks().stream())
				.map(e -> new MainTaskDto(e.getCallTaskId(), e.getSubject(), e.getStatus(), CALL, e.getDueDate(),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getCall().getCallId()))
				.collect(toList());
	}

	public List<MainTaskDto> getVistRelatedTasks(List<Visit> visits) {
		return visits.stream().flatMap(visit -> visit.getVisitTasks().stream())
				.map(e -> new MainTaskDto(e.getVisitTaskId(), e.getSubject(), e.getStatus(), VISIT, e.getDueDate(),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getVisit().getVisitId()))
				.collect(toList());
	}

	public List<MainTaskDto> getMeetingRelatedTasks(List<Meetings> meetings) {
		return meetings.stream().flatMap(meet -> meet.getMeetingTasks().stream())
				.map(e -> new MainTaskDto(e.getMeetingTaskId(), e.getSubject(), e.getStatus(), MEETING, e.getDueDate(),
						TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getMeetings().getMeetingId()))
				.collect(toList());
	}

	public List<MainTaskDto> getLeadRelatedTasks(Leads lead) {
		return lead
				.getLeadTasks().stream().map(e -> new MainTaskDto(e.getLeadTaskId(), e.getSubject(), e.getStatus(),
						LEAD, e.getDueDate(), TO_EMPLOYEE.apply(e.getAssignTo()).orElse(null), e.getLead().getLeadId()))
				.collect(toList());
	}

	public Map<String, Object> getTaskDataMap(List<Call> calls, List<Visit> visits, List<Meetings> meetings,
			Leads lead) {
		Map<String, Object> taskData = new HashMap<>();
		Map<String, Object> taskCount = new HashMap<>();
		List<MainTaskDto> allTask = getCallRelatedTasks(calls);
		allTask.addAll(getVistRelatedTasks(visits));
		allTask.addAll(getMeetingRelatedTasks(meetings));
		allTask.addAll(getLeadRelatedTasks(lead));
		List<MainTaskDto> notStartedTask = allTask.stream().filter(NOT_STARTED_TASK).collect(toList());
		List<MainTaskDto> inProgressTask = allTask.stream().filter(IN_PROGRESS_TASK).collect(toList());
		List<MainTaskDto> onHoldTask = allTask.stream().filter(ON_HOLD_TASK).collect(toList());
		List<MainTaskDto> completedTask = allTask.stream().filter(COMPLETED_TASK).collect(toList());
		taskData.put(ALL_TASK, allTask);
		taskData.put(COMPLETED_TASK_KEY, completedTask);
		taskData.put(IN_PROGRESS_TASK_KEY, inProgressTask);
		taskData.put(ON_HOLD_TASK_KEY, onHoldTask);
		taskData.put(NOT_STARTED_TASK_KEY, notStartedTask);
		taskCount.put(ALL_TASK_COUNT, allTask.stream().count());
		taskCount.put(COMPLETED_TASK_COUNT, completedTask.stream().count());
		taskCount.put(IN_PROGRESS_TASK_COUNT, inProgressTask.stream().count());
		taskCount.put(ON_HOLD_TASK_COUNT, onHoldTask.stream().count());
		taskCount.put(NOT_STARTED_TASK_COUNT, notStartedTask.stream().count());
		taskData.put(COUNT_BY_STATUS, taskCount);
		return taskData;
	}

	public void setCompanyDetailsToLead(Optional<CompanyDto> existCompany, LeadDto leadDto, Leads leads) {
		if (existCompany.isPresent()) {
			existCompany.get().setCompanyWebsite(leadDto.getCompanyWebsite());
			leads.setCompanyMaster(
					TO_COMPANY
							.apply(companyMasterDaoService
									.save(TO_COMPANY.apply(existCompany.orElseThrow(ResourceNotFoundException::new))
											.orElseThrow(ResourceNotFoundException::new))
									.orElseThrow(ResourceNotFoundException::new))
							.orElseThrow(ResourceNotFoundException::new));
		} else
			leads.setCompanyMaster(
					TO_COMPANY
							.apply(companyMasterDaoService
									.save(TO_COMPANY
											.apply(CompanyDto.builder().companyName(leadDto.getCompanyName())
													.companyWebsite(leadDto.getCompanyWebsite()).build())
											.orElseThrow(ResourceNotFoundException::new))
									.orElseThrow(ResourceNotFoundException::new))
							.orElseThrow(ResourceNotFoundException::new));
	}

	private void setServiceFallsIntoAndLeadSourceToLead(String serviceFallsName, String leadSourceName, Leads leads)
			throws Exception {
		Pattern pattern = compile("^\\d+$");
		if (nonNull(serviceFallsName) && pattern.matcher(serviceFallsName).matches())
			serviceFallsDaoSevice.getServiceFallById(parseInt(serviceFallsName))
					.ifPresent(leads::setServiceFallsMaster);
		else {
			ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
			serviceFalls.setServiceName(serviceFallsName);
			TO_SERVICE_FALL_MASTER.apply(serviceFallsDaoSevice.save(serviceFalls).orElseThrow(
					() -> new ResourceNotFoundException(SERVICE_FALLS_MASTER, SERVICE_FALL_ID, serviceFallsName)))
					.ifPresent(leads::setServiceFallsMaster);
		}
		if (nonNull(leadSourceName) && pattern.matcher(leadSourceName).matches())
			leadSourceDaoService.getLeadSourceById(parseInt(leadSourceName)).ifPresent(leads::setLeadSourceMaster);
		else {
			LeadSourceMaster leadSource = new LeadSourceMaster();
			leadSource.setSourceName(leadSourceName);
			TO_LEAD_SOURCE
					.apply(leadSourceDaoService.save(leadSource).orElseThrow(
							() -> new ResourceNotFoundException(LEAD_SOURCE_MASTER, LEAD_SOURCE_NAME, leadSourceName)))
					.ifPresent(leads::setLeadSourceMaster);
		}
	}

	private Map<String, Object> upNextActivities(LinkedHashMap<Long, List<TimeLineActivityDto>> upNextActivities) {
		Map<String, Object> upNextDataMap = new HashMap<>();
		upNextDataMap.put(MSG, NO_ACTIVITY);
		if (nonNull(upNextActivities) && !upNextActivities.isEmpty()) {
			Long lowestDayDiff = upNextActivities.keySet().stream().min(naturalOrder()).orElse(null);
			if (nonNull(lowestDayDiff)) {
				int count = 0;
				List<TimeLineActivityDto> upNextData = new ArrayList<>();
				for (Map.Entry<Long, List<TimeLineActivityDto>> data : upNextActivities.entrySet()) {
					data.getValue().forEach(e -> {
						e.setWaitTwoDays(!lowestDayDiff.equals(data.getKey()));
						upNextData.add(e);
					});
					if (count == 1 && !data.getKey().equals(0L))
						upNextDataMap.put(MSG, String.format(WAIT_FOR, data.getKey()));
					count++;
				}
				upNextDataMap.put(UPNEXT_DATA_KEY, upNextData);
			}
		}
		return upNextDataMap;
	}

}
