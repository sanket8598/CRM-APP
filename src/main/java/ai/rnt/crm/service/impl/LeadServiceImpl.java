package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.ExcelConstants.FLAG;
import static ai.rnt.crm.constants.ExcelConstants.LEAD_DATA;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.TOPIC;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static ai.rnt.crm.constants.StatusConstants.ALL;
import static ai.rnt.crm.constants.StatusConstants.ALL_LEAD;
import static ai.rnt.crm.constants.StatusConstants.CALL;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_DISQUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_LEAD;
import static ai.rnt.crm.constants.StatusConstants.COMPLETE;
import static ai.rnt.crm.constants.StatusConstants.DISQUALIFIED_LEAD;
import static ai.rnt.crm.constants.StatusConstants.EMAIL;
import static ai.rnt.crm.constants.StatusConstants.MEETING;
import static ai.rnt.crm.constants.StatusConstants.OPEN;
import static ai.rnt.crm.constants.StatusConstants.OPEN_LEAD;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED_LEAD;
import static ai.rnt.crm.constants.StatusConstants.SAVE;
import static ai.rnt.crm.constants.StatusConstants.SEND;
import static ai.rnt.crm.constants.StatusConstants.TASK_COMPLETED;
import static ai.rnt.crm.constants.StatusConstants.TASK_IN_PROGRESS;
import static ai.rnt.crm.constants.StatusConstants.TASK_NOT_STARTED;
import static ai.rnt.crm.constants.StatusConstants.TASK_ON_HOLD;
import static ai.rnt.crm.constants.StatusConstants.VISIT;
import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employee;
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
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ASSIGNED_TO_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.DISQUALIFIED_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.OPEN_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.QUALIFIED_LEAD_FILTER;
import static ai.rnt.crm.util.LeadsCardUtil.UPNEXT;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import ai.rnt.crm.util.ConvertDateFormatUtil;
import ai.rnt.crm.util.LeadsCardUtil;
import ai.rnt.crm.util.ReadExcelUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	private static final DateTimeFormatter DATE_TIME_WITH_AM_OR_PM = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(LeadDto leadDto) {
		EnumMap<ApiResponse, Object> createMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads leads = TO_LEAD.apply(leadDto).orElseThrow(null);

			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(leadDto.getCompanyName());
			if (existCompany.isPresent()) {
				existCompany.get().setCompanyWebsite(leadDto.getCompanyWebsite());
				leads.setCompanyMaster(TO_COMPANY
						.apply(companyMasterDaoService
								.save(TO_COMPANY.apply(existCompany.orElseThrow(ResourceNotFoundException::new))
										.orElseThrow(ResourceNotFoundException::new))
								.orElseThrow(ResourceNotFoundException::new))
						.orElseThrow(ResourceNotFoundException::new));
			} else
				leads.setCompanyMaster(TO_COMPANY
						.apply(companyMasterDaoService
								.save(TO_COMPANY
										.apply(CompanyDto.builder().companyName(leadDto.getCompanyName())
												.companyWebsite(leadDto.getCompanyWebsite()).build())
										.orElseThrow(ResourceNotFoundException::new))
								.orElseThrow(ResourceNotFoundException::new))
						.orElseThrow(ResourceNotFoundException::new));
			leads.setStatus(OPEN);
			leads.setDisqualifyAs(OPEN);
			leads.setPseudoName(auditAwareUtil.getLoggedInUserName());
			if (nonNull(leadDto.getServiceFallsId())
					&& Pattern.compile("^\\d+$").matcher(leadDto.getServiceFallsId()).matches())
				serviceFallsDaoSevice.getServiceFallById(Integer.parseInt(leadDto.getServiceFallsId()))
				.ifPresent(leads::setServiceFallsMaster);
			else {
				ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
				serviceFalls.setServiceName(leadDto.getServiceFallsId());
				TO_SERVICE_FALL_MASTER.apply(serviceFallsDaoSevice.save(serviceFalls).get())
				.ifPresent(leads::setServiceFallsMaster);
			}
			if (nonNull(leadDto.getLeadSourceId())
					&& Pattern.compile("^\\d+$").matcher(leadDto.getLeadSourceId()).matches())
				leadSourceDaoService.getLeadSourceById(Integer.parseInt(leadDto.getLeadSourceId()))
				.ifPresent(leads::setLeadSourceMaster);
			else {
				LeadSourceMaster leadSource = new LeadSourceMaster();
				leadSource.setSourceName(leadDto.getLeadSourceId());
				TO_LEAD_SOURCE.apply(leadSourceDaoService.save(leadSource).get()).ifPresent(leads::setLeadSourceMaster);
			}

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
						.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate()))
						.collect(Collectors.toList());
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
				dataMap.put("sortFilter", filterMap);
				if (auditAwareUtil.isAdmin()) {
					dataMap.put(ALL_LEAD,
							allLeads.stream()
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
									filterMap.get(PRIMFIELD).toString(), filterMap.get(SECNDFIELD).toString()))
							.collect(Collectors.toList()));
					dataMap.put(OPEN_LEAD,
							allLeads.stream().filter(l -> nonNull(l.getStatus())
									&& (l.getStatus().equalsIgnoreCase("new") || l.getStatus().equalsIgnoreCase(OPEN)))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
									filterMap.get(PRIMFIELD).toString(), filterMap.get(SECNDFIELD).toString()))
							.collect(Collectors.toList()));
					dataMap.put(CLOSE_LEAD,
							allLeads.stream().filter(l -> !l.getStatus().equalsIgnoreCase(OPEN))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
									filterMap.get(PRIMFIELD).toString(), filterMap.get(SECNDFIELD).toString()))
							.collect(Collectors.toList()));
				} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
					dataMap.put(ALL_LEAD,
							allLeads.stream().filter(l -> ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
									filterMap.get(PRIMFIELD).toString(), filterMap.get(SECNDFIELD).toString()))
							.collect(Collectors.toList()));
					dataMap.put(OPEN_LEAD,
							allLeads.stream().filter(l -> (nonNull(l.getStatus())
									&& (l.getStatus().equalsIgnoreCase("new") || l.getStatus().equalsIgnoreCase(OPEN)))
									&& ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
									filterMap.get(PRIMFIELD).toString(), filterMap.get(SECNDFIELD).toString()))
							.collect(Collectors.toList()));
					dataMap.put(CLOSE_LEAD,
							allLeads.stream()
							.filter(l -> !l.getStatus().equalsIgnoreCase(OPEN)
									&& ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
									filterMap.get(PRIMFIELD).toString(), filterMap.get(SECNDFIELD).toString()))
							.collect(Collectors.toList()));
				} else
					dataMap.put("DATA", Collections.emptyList());
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
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("serviceFallData",
					TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put("leadSourceData", TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put("assignToData", TO_Employees.apply(roleMasterDaoService.getAdminAndUser()));
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
				dataMap.put("COUNTDATA", countMap);
				if (nonNull(leadsStatus) && leadsStatus.equalsIgnoreCase(ALL)) {
					dataMap.put("DATA", TO_DASHBOARD_LEADDTOS.apply(leadDashboardData));
					leadsDataByStatus.put(DATA, dataMap);
				} else {
					dataMap.put("DATA", TO_DASHBOARD_LEADDTOS
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
				dataMap.put("COUNTDATA", countMap);
				if (nonNull(leadsStatus) && leadsStatus.equalsIgnoreCase(ALL)) {
					dataMap.put("DATA", TO_DASHBOARD_LEADDTOS.apply(leadDashboardData.stream()
							.filter(d -> ASSIGNED_TO_FILTER.test(d, loggedInStaffId)).collect(Collectors.toList())));
					leadsDataByStatus.put(DATA, dataMap);
				} else {
					dataMap.put("DATA",
							TO_DASHBOARD_LEADDTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus).stream()
									.filter(d -> ASSIGNED_TO_FILTER.test(d, loggedInStaffId))
									.collect(Collectors.toList())));
					leadsDataByStatus.put(DATA, dataMap);
				}
			} else
				leadsDataByStatus.put(DATA, Collections.emptyList());

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
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadId));
			Optional<EditLeadDto> dto = TO_EDITLEAD_DTO.apply(leadById);
			dto.ifPresent(e -> {
				e.setMessage("Assigned To " + leadById.getEmployee().getFirstName() + " "
						+ leadById.getEmployee().getLastName());
				EmployeeMaster employeeMaster = employeeService.getById(leadById.getCreatedBy()).orElseThrow(
						() -> new ResourceNotFoundException("Employee", "staffId", leadById.getCreatedBy()));
				e.setGeneratedBy(employeeMaster.getFirstName() + " " + employeeMaster.getLastName());
			});
			List<Call> calls = callDaoService.getCallsByLeadId(leadId);
			List<Visit> visits = visitDaoService.getVisitsByLeadId(leadId);
			List<Email> emails = emailDaoService.getEmailByLeadId(leadId);
			List<Meetings> meetings = meetingDaoService.getMeetingByLeadId(leadId);
			List<TimeLineActivityDto> timeLine = new ArrayList<>();
			List<EditCallDto> list = calls.stream()
					.filter(call -> nonNull(call.getStatus()) && call.getStatus().equalsIgnoreCase(COMPLETE))
					.map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType(CALL);
						callDto.setBody(call.getComment());
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDate(call.getUpdatedDate()));
						callDto.setShortName(LeadsCardUtil.shortName(call.getCallTo()));
						TO_Employee.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());
			timeLine.addAll(list);
			timeLine.addAll(emails.stream()
					.filter(email -> nonNull(email.getStatus()) && email.getStatus().equalsIgnoreCase(SEND))
					.map(email -> {
						EditEmailDto editEmailDto = new EditEmailDto();
						editEmailDto.setId(email.getMailId());
						editEmailDto.setType(EMAIL);
						editEmailDto.setSubject(email.getSubject());
						editEmailDto.setBody(email.getContent());
						editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
						editEmailDto.setCreatedOn(ConvertDateFormatUtil.convertDate(email.getUpdatedDate()));
						editEmailDto.setShortName(LeadsCardUtil.shortName(email.getMailFrom()));
						return editEmailDto;
					}).collect(Collectors.toList()));
			timeLine.addAll(visits.stream()
					.filter(visit -> nonNull(visit.getStatus()) && visit.getStatus().equalsIgnoreCase(COMPLETE))
					.map(visit -> {
						EditVisitDto visitDto = new EditVisitDto();
						visitDto.setId(visit.getVisitId());
						visitDto.setLocation(visit.getLocation());
						visitDto.setSubject(visit.getSubject());
						visitDto.setType(VISIT);
						visitDto.setBody(visit.getContent());
						employeeService.getById(visit.getCreatedBy()).ifPresent(byId -> visitDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						visitDto.setCreatedOn(ConvertDateFormatUtil.convertDate(visit.getUpdatedDate()));
						return visitDto;
					}).collect(Collectors.toList()));
			timeLine.addAll(meetings.stream().filter(meeting -> nonNull(meeting.getMeetingStatus())
					&& meeting.getMeetingStatus().equalsIgnoreCase(COMPLETE)).map(meet -> {
						EditMeetingDto meetDto = new EditMeetingDto();
						meetDto.setId(meet.getMeetingId());
						meetDto.setType(MEETING);
						employeeService.getById(meet.getCreatedBy()).ifPresent(byId -> meetDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						meetDto.setSubject(meet.getMeetingTitle());
						meetDto.setBody(meet.getDescription());
						meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
						meetDto.setCreatedOn(ConvertDateFormatUtil.convertDate(meet.getUpdatedDate()));
						return meetDto;
					}).collect(Collectors.toList()));
			timeLine.sort((t1, t2) -> LocalDateTime.parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(LocalDateTime.parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			List<TimeLineActivityDto> activity = calls.stream()
					.filter(call -> (isNull(call.getStatus()) || call.getStatus().equalsIgnoreCase(SAVE))
							&& !UPNEXT.test(call.getEndDate()))
					.map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType(CALL);
						callDto.setBody(call.getComment());
						callDto.setShortName(LeadsCardUtil.shortName(call.getCallTo()));
						callDto.setDueDate(ConvertDateFormatUtil.convertDateDateWithTime(call.getStartDate(),
								call.getStartTime()));
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDate(call.getCreatedDate()));
						TO_Employee.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());
			activity.addAll(emails.stream()
					.filter(email -> isNull(email.getStatus()) || email.getStatus().equalsIgnoreCase(SAVE))
					.map(email -> {
						EditEmailDto editEmailDto = new EditEmailDto();
						editEmailDto.setId(email.getMailId());
						editEmailDto.setType(EMAIL);
						editEmailDto.setSubject(email.getSubject());
						editEmailDto.setBody(email.getContent());
						editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
						editEmailDto.setCreatedOn(ConvertDateFormatUtil.convertDate(email.getCreatedDate()));
						editEmailDto.setShortName(LeadsCardUtil.shortName(email.getMailFrom()));
						return editEmailDto;
					}).collect(Collectors.toList()));
			activity.addAll(visits.stream()
					.filter(visit -> (isNull(visit.getStatus()) || visit.getStatus().equalsIgnoreCase(SAVE))
							&& !UPNEXT.test(visit.getEndDate()))
					.map(visit -> {
						EditVisitDto editVisitDto = new EditVisitDto();
						editVisitDto.setId(visit.getVisitId());
						editVisitDto.setLocation(visit.getLocation());
						editVisitDto.setSubject(visit.getSubject());
						editVisitDto.setType(VISIT);
						editVisitDto.setBody(visit.getContent());
						editVisitDto.setDueDate(ConvertDateFormatUtil.convertDateDateWithTime(visit.getStartDate(),
								visit.getStartTime()));
						employeeService.getById(visit.getCreatedBy()).ifPresent(byId -> editVisitDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						editVisitDto.setCreatedOn(ConvertDateFormatUtil.convertDate(visit.getCreatedDate()));
						return editVisitDto;
					}).collect(Collectors.toList()));
			activity.addAll(meetings.stream()
					.filter(meeting -> (isNull(meeting.getMeetingStatus())
							|| meeting.getMeetingStatus().equalsIgnoreCase(SAVE)) && !UPNEXT.test(meeting.getEndDate()))
					.map(meet -> {
						EditMeetingDto meetDto = new EditMeetingDto();
						meetDto.setId(meet.getMeetingId());
						meetDto.setType(MEETING);
						employeeService.getById(meet.getCreatedBy()).ifPresent(byId -> meetDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						meetDto.setSubject(meet.getMeetingTitle());
						meetDto.setBody(meet.getDescription());
						meetDto.setDueDate(ConvertDateFormatUtil.convertDateDateWithTime(meet.getStartDate(),
								meet.getStartTime()));
						meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
						meetDto.setCreatedOn(ConvertDateFormatUtil.convertDate(meet.getCreatedDate()));
						return meetDto;
					}).collect(Collectors.toList()));
			activity.sort((t1, t2) -> LocalDateTime.parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(LocalDateTime.parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));
			List<TimeLineActivityDto> upNext = calls.stream()
					.filter(call -> (isNull(call.getStatus()) || call.getStatus().equalsIgnoreCase(SAVE))
							&& UPNEXT.test(call.getEndDate()))
					.map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType(CALL);
						callDto.setBody(call.getComment());
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDateDateWithTime(call.getStartDate(),
								call.getStartTime()));
						TO_Employee.apply(call.getCallFrom())
						.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());

			upNext.addAll(visits.stream()
					.filter(visit -> (isNull(visit.getStatus()) || visit.getStatus().equalsIgnoreCase(SAVE))
							&& UPNEXT.test(visit.getEndDate()))
					.map(visit -> {
						EditVisitDto editVisitDto = new EditVisitDto();
						editVisitDto.setId(visit.getVisitId());
						editVisitDto.setLocation(visit.getLocation());
						editVisitDto.setSubject(visit.getSubject());
						editVisitDto.setType(VISIT);
						editVisitDto.setBody(visit.getContent());
						employeeService.getById(visit.getCreatedBy()).ifPresent(byId -> editVisitDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						editVisitDto.setCreatedOn(ConvertDateFormatUtil.convertDateDateWithTime(visit.getStartDate(),
								visit.getStartTime()));
						return editVisitDto;
					}).collect(Collectors.toList()));
			upNext.addAll(meetings.stream()
					.filter(meeting -> (isNull(meeting.getMeetingStatus())
							|| meeting.getMeetingStatus().equalsIgnoreCase(SAVE)) && UPNEXT.test(meeting.getEndDate()))
					.map(meet -> {
						EditMeetingDto meetDto = new EditMeetingDto();
						meetDto.setId(meet.getMeetingId());
						meetDto.setType(MEETING);
						employeeService.getById(meet.getCreatedBy()).ifPresent(byId -> meetDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						meetDto.setSubject(meet.getMeetingTitle());
						meetDto.setBody(meet.getDescription());
						meetDto.setAttachments(TO_METTING_ATTACHMENT_DTOS.apply(meet.getMeetingAttachments()));
						meetDto.setCreatedOn(ConvertDateFormatUtil.convertDateDateWithTime(meet.getStartDate(),
								meet.getStartTime()));
						return meetDto;
					}).collect(Collectors.toList()));
			upNext.sort((t1, t2) -> LocalDateTime.parse(t2.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)
					.compareTo(LocalDateTime.parse(t1.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)));

			dataMap.put("Contact", dto);
			dataMap.put("serviceFalls", TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put("leadSource", TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put("Timeline", timeLine);
			dataMap.put("Activity", activity);
			dataMap.put("UpNext", upNext.stream().map(e -> {
				e.setWaitTwoDays(false);
				if (ChronoUnit.DAYS.between(LocalDateTime.now(),
						LocalDateTime.parse(e.getCreatedOn(), DATE_TIME_WITH_AM_OR_PM)) > 2)
					e.setWaitTwoDays(true);
				return e;
			}).collect(Collectors.toList()));
			dataMap.put("TaskData", getTaskDataMap(calls, visits, meetings));
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
			Optional<Leads> lead = leadDaoService.getLeadById(leadId);
			if (lead.isPresent()) {
				lead.get().setCustomerNeed(dto.getCustomerNeed());
				lead.get().setProposedSolution(dto.getProposedSolution());
				lead.get().setStatus(CLOSE_AS_QUALIFIED);
				lead.get().setDisqualifyAs(QUALIFIED);
				if (nonNull(dto.getServiceFallsMaster().getServiceName())
						&& Pattern.compile("^\\d+$").matcher(dto.getServiceFallsMaster().getServiceName()).matches())
					lead.get()
					.setServiceFallsMaster(serviceFallsDaoSevice
							.getServiceFallById(Integer.parseInt(dto.getServiceFallsMaster().getServiceName()))
							.orElseThrow(() -> new ResourceNotFoundException("ServiceFallMaster",
									"serviceFallId", dto.getServiceFallsMaster().getServiceName())));
				else {
					ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
					serviceFalls.setServiceName(dto.getServiceFallsMaster().getServiceName());
					lead.get().setServiceFallsMaster(
							TO_SERVICE_FALL_MASTER.apply(serviceFallsDaoSevice.save(serviceFalls).get())
							.orElseThrow(() -> new ResourceNotFoundException("ServiceFallMaster", "serviceName",
									dto.getServiceFallsMaster().getServiceName())));
				}

				if (nonNull(leadDaoService.addLead(lead.get()))) {
					result.put(MESSAGE, "Lead Qualified SuccessFully");
					result.put(SUCCESS, true);
				} else {
					result.put(MESSAGE, "Lead Not Qualify");
					result.put(SUCCESS, false);
				}
			} else {
				result.put(MESSAGE, "Lead Not Qualify");
				result.put(SUCCESS, false);
			}
			return new ResponseEntity<>(result, OK);
		} catch (Exception e) {
			log.info("Got Exception while qualifying the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> assignLead(Map<String, Integer> map) {
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		log.info("inside assign lead staffId: {} LeadId:{}", map.get("leadId"));
		try {
			Leads leads = leadDaoService.getLeadById(map.get("leadId"))
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", map.get("leadId")));
			EmployeeMaster employee = employeeService.getById(map.get("staffId"))
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", map.get("staffId")));
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
			Optional<Leads> lead = leadDaoService.getLeadById(leadId);
			if (lead.isPresent()) {
				lead.get().setDisqualifyAs(dto.getDisqualifyAs());
				lead.get().setDisqualifyReason(dto.getDisqualifyReason());
				lead.get().setStatus(CLOSE_AS_DISQUALIFIED);
				if (nonNull(leadDaoService.addLead(lead.get()))) {
					result.put(MESSAGE, "Lead Disqualified SuccessFully");
					result.put(SUCCESS, true);
				} else {
					result.put(MESSAGE, "Lead Not Disqualify");
					result.put(SUCCESS, false);
				}
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
			Leads lead = leadDaoService.getLeadById(leadId).orElseThrow(null);
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
				CompanyMaster companyMaster = TO_COMPANY.apply(existCompany.orElseThrow(null)).orElseThrow(null);
				companyMaster.setCompanyWebsite(dto.getCompanyWebsite());
				if (findByCountryName.isPresent())
					findByCountryName.ifPresent(companyMaster::setCountry);
				else {
					country = new CountryMaster();
					country.setCountry(dto.getCountry());
					Optional<CountryMaster> newFindByCountryName = Optional.of(countryDaoService.addCountry(country));
					newFindByCountryName.ifPresent(companyMaster::setCountry);
				}
				if (findBystate.isPresent())
					findBystate.ifPresent(companyMaster::setState);
				else {
					state = new StateMaster();
					state.setState(dto.getState());
					Optional<StateMaster> newFindBystate = Optional.of(stateDaoService.addState(state));
					newFindBystate.ifPresent(companyMaster::setState);
				}
				if (existCityByName.isPresent())
					existCityByName.ifPresent(companyMaster::setCity);
				else {
					city = new CityMaster();
					city.setCity(dto.getCity());
					Optional<CityMaster> newExistCityByName = Optional.of(cityDaoService.addCity(city));
					newExistCityByName.ifPresent(companyMaster::setCity);
				}
				companyMaster.setZipCode(dto.getZipCode());
				companyMaster.setAddressLineOne(dto.getAddressLineOne());
				TO_COMPANY.apply(companyMasterDaoService.save(companyMaster).orElseThrow(null))
				.ifPresent(lead::setCompanyMaster);
			} else {
				CompanyMaster companyMaster = TO_COMPANY.apply(CompanyDto.builder().companyName(dto.getCompanyName())
						.companyWebsite(dto.getCompanyWebsite()).build()).orElseThrow(null);
				if (findByCountryName.isPresent())
					findByCountryName.ifPresent(companyMaster::setCountry);
				else {
					country = new CountryMaster();
					country.setCountry(dto.getCountry());
					Optional<CountryMaster> newFindByCountryName = Optional.of(countryDaoService.addCountry(country));
					newFindByCountryName.ifPresent(companyMaster::setCountry);
				}
				if (findBystate.isPresent())
					findBystate.ifPresent(companyMaster::setState);
				else {
					state = new StateMaster();
					state.setState(dto.getState());
					Optional<StateMaster> newFindBystate = Optional.of(stateDaoService.addState(state));
					newFindBystate.ifPresent(companyMaster::setState);
				}
				if (existCityByName.isPresent())
					existCityByName.ifPresent(companyMaster::setCity);
				else {
					city = new CityMaster();
					city.setCity(dto.getCity());
					Optional<CityMaster> newExistCityByName = Optional.of(cityDaoService.addCity(city));
					newExistCityByName.ifPresent(companyMaster::setCity);
				}
				companyMaster.setZipCode(dto.getZipCode());
				companyMaster.setAddressLineOne(dto.getAddressLineOne());
				TO_COMPANY.apply(companyMasterDaoService.save(companyMaster).orElseThrow(null))
				.ifPresent(lead::setCompanyMaster);
			}
			if (nonNull(dto.getServiceFallsId())
					&& Pattern.compile("^\\d+$").matcher(dto.getServiceFallsId()).matches())
				serviceFallsDaoSevice.getServiceFallById(Integer.parseInt(dto.getServiceFallsId()))
				.ifPresent(lead::setServiceFallsMaster);
			else {
				ServiceFallsMaster serviceFalls = new ServiceFallsMaster();
				serviceFalls.setServiceName(dto.getServiceFallsId());
				TO_SERVICE_FALL_MASTER.apply(serviceFallsDaoSevice.save(serviceFalls).get())
				.ifPresent(lead::setServiceFallsMaster);
			}
			if (nonNull(dto.getLeadSourceId()) && Pattern.compile("^\\d+$").matcher(dto.getLeadSourceId()).matches())
				leadSourceDaoService.getLeadSourceById(Integer.parseInt(dto.getLeadSourceId()))
				.ifPresent(lead::setLeadSourceMaster);
			else {
				LeadSourceMaster leadSource = new LeadSourceMaster();
				leadSource.setSourceName(dto.getLeadSourceId());
				TO_LEAD_SOURCE.apply(leadSourceDaoService.save(leadSource).get()).ifPresent(lead::setLeadSourceMaster);
			}
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
						.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadId)));
				importantLead.setEmployee(employeeService.getById(loggedInStaffId)
						.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", loggedInStaffId)));
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
			Optional<Leads> lead = leadDaoService.getLeadById(leadId);
			if (lead.isPresent()) {
				lead.get().setDisqualifyAs(OPEN);
				lead.get().setDisqualifyReason(null);
				lead.get().setStatus(OPEN);
				if (nonNull(leadDaoService.addLead(lead.get()))) {
					result.put(MESSAGE, "Lead Reactivate SuccessFully");
					result.put(SUCCESS, true);
				} else {
					result.put(MESSAGE, "Lead Not Reactivate");
					result.put(SUCCESS, false);
				}
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
			sortFilter.setEmployee(TO_Employee
					.apply(employeeService.getById(loggedInStaffId)
							.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", loggedInStaffId)))
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
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadId))));
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
			Workbook workbook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workbook.getSheetAt(0);
			if (isValidExcel(sheet)) {
				Map<String, Object> excelData = readExcelUtil.getLeadFromExcelFile(workbook, sheet);
				int saveLeadCount = 0;
				int duplicateLead = 0;
				if ((nonNull(excelData) && !excelData.isEmpty())
						&& (excelData.containsKey(FLAG) && (boolean) excelData.get(FLAG))) {
					for (LeadDto leadDto : (List<LeadDto>) excelData.get(LEAD_DATA)) {
						if (nonNull(leadDto)) {
							Leads leads = buildLeadObj(leadDto);
							if (nonNull(leadDto.getPseudoName()) && !leadDto.getPseudoName().isEmpty())
								setAssignToNameForTheLead(leads, leadDto.getPseudoName().split(" "));
							else
								setAssignToNameForTheLead(leads, auditAwareUtil.getLoggedInUserName().split(" "));
							leads.setPseudoName(null);
							if (LeadsCardUtil.checkDuplicateLead(leadDaoService.getAllLeads(), leads))
								duplicateLead++;
							else if (nonNull(leadDaoService.addLead(leads)))
								saveLeadCount++;
						}
					}
					if (duplicateLead == 0 && saveLeadCount == 0)
						result.put(MESSAGE, "Leads Not Added !!");
					else {
						result.put(SUCCESS, true);
						result.put(MESSAGE, saveLeadCount + " Leads Added And " + duplicateLead + " Duplicate Found!!");
					}
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
		List<String> dbHeaderNames = excelHeaderDaoService.getAllExcelHeaders().stream()
				.map(ExcelHeaderMaster::getHeaderName).map(String::trim).collect(Collectors.toList());
		List<String> excelHeader = readExcelUtil.getAllHeaders(sheet).stream().map(String::trim)
				.collect(Collectors.toList());
		return (nonNull(excelHeader) && !excelHeader.isEmpty()) && (nonNull(dbHeaderNames) && !dbHeaderNames.isEmpty())
				&& excelHeader.stream().allMatch(dbHeaderNames::contains) && excelHeader.containsAll(dbHeaderNames);
	}

	public Leads buildLeadObj(LeadDto leadDto) throws Exception {
		leadDto.setStatus(OPEN);
		leadDto.setDisqualifyAs(OPEN);
		Leads leads = TO_LEAD.apply(leadDto).orElseThrow(ResourceNotFoundException::new);
		Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(leadDto.getCompanyName());
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
		if (nonNull(leadDto.getServiceFallsId()) && !leadDto.getServiceFallsId().isEmpty()) {
			Optional<ServiceFallsMaster> serviceFalls = serviceFallsDaoSevice.findByName(leadDto.getServiceFallsId());
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
				TO_LEAD_SOURCE.apply(leadSourceDaoService.save(leadSources).orElseThrow(ResourceNotFoundException::new))
				.ifPresent(leads::setLeadSourceMaster);
			}
		} else
			leadSourceDaoService.getByName("Other").ifPresent(leads::setLeadSourceMaster);
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
		return calls.stream().flatMap(call -> call.getCallTasks().stream()).map(e -> new MainTaskDto(e.getCallTaskId(),
				e.getSubject(), e.getStatus(), CALL, e.getDueDate(), TO_Employee.apply(e.getAssignTo()).orElse(null)))
				.collect(Collectors.toList());
	}

	public List<MainTaskDto> getVistRelatedTasks(List<Visit> visits) {
		return visits.stream().flatMap(visit -> visit.getVisitTasks().stream())
				.map(e -> new MainTaskDto(e.getVisitTaskId(), e.getSubject(), e.getStatus(), VISIT, e.getDueDate(),
						TO_Employee.apply(e.getAssignTo()).orElse(null)))
				.collect(Collectors.toList());
	}

	public List<MainTaskDto> getMeetingRelatedTasks(List<Meetings> meetings) {
		return meetings.stream().flatMap(meet -> meet.getMeetingTasks().stream())
				.map(e -> new MainTaskDto(e.getMeetingTaskId(), e.getSubject(), e.getStatus(), MEETING, e.getDueDate(),
						TO_Employee.apply(e.getAssignTo()).orElse(null)))
				.collect(Collectors.toList());
	}

	public Map<String, Object> getTaskDataMap(List<Call> calls, List<Visit> visits, List<Meetings> meetings) {
		Map<String, Object> taskData = new HashMap<>();
		Map<String, Object> taskCount = new HashMap<>();
		List<MainTaskDto> allTask = getCallRelatedTasks(calls);
		allTask.addAll(getVistRelatedTasks(visits));
		allTask.addAll(getMeetingRelatedTasks(meetings));
		List<MainTaskDto> notStartedTask = allTask.stream()
				.filter(task -> nonNull(task.getStatus()) && task.getStatus().equalsIgnoreCase(TASK_NOT_STARTED))
				.collect(Collectors.toList());
		List<MainTaskDto> inProgressTask = allTask.stream()
				.filter(task -> nonNull(task.getStatus()) && task.getStatus().equalsIgnoreCase(TASK_IN_PROGRESS))
				.collect(Collectors.toList());
		List<MainTaskDto> onHoldTask = allTask.stream()
				.filter(task -> nonNull(task.getStatus()) && task.getStatus().equalsIgnoreCase(TASK_ON_HOLD))
				.collect(Collectors.toList());
		List<MainTaskDto> completedTask = allTask.stream()
				.filter(task -> nonNull(task.getStatus()) && task.getStatus().equalsIgnoreCase(TASK_COMPLETED))
				.collect(Collectors.toList());
		taskData.put("allTask", allTask);
		taskData.put("completedTask", completedTask);
		taskData.put("inProgressTask", inProgressTask);
		taskData.put("onHoldTask", onHoldTask);
		taskData.put("notStartedTask", notStartedTask);
		taskCount.put("allTaskCount", allTask.stream().count());
		taskCount.put("completedTaskCount", completedTask.stream().count());
		taskCount.put("inProgressTaskCount", inProgressTask.stream().count());
		taskCount.put("onHoldTaskCount", onHoldTask.stream().count());
		taskCount.put("notStartedTaskCount", notStartedTask.stream().count());
		taskData.put("countByStatus", taskCount);
		return taskData;
	}

}
