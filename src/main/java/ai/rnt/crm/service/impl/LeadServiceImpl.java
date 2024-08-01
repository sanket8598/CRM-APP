package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.CRMConstants.ACTIVITY;
import static ai.rnt.crm.constants.CRMConstants.ASSIGN_DATA;
import static ai.rnt.crm.constants.CRMConstants.COUNTDATA;
import static ai.rnt.crm.constants.CRMConstants.DOMAINS;
import static ai.rnt.crm.constants.CRMConstants.DOMAIN_MASTER_DATA;
import static ai.rnt.crm.constants.CRMConstants.EMPLOYEE;
import static ai.rnt.crm.constants.CRMConstants.LEAD_ID;
import static ai.rnt.crm.constants.CRMConstants.LEAD_INFO;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE;
import static ai.rnt.crm.constants.CRMConstants.LEAD_SOURCE_DATA;
import static ai.rnt.crm.constants.CRMConstants.OTHER;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL;
import static ai.rnt.crm.constants.CRMConstants.SERVICE_FALL_DATA;
import static ai.rnt.crm.constants.CRMConstants.SORT_FILTER;
import static ai.rnt.crm.constants.CRMConstants.STAFF_ID;
import static ai.rnt.crm.constants.CRMConstants.TASK;
import static ai.rnt.crm.constants.CRMConstants.TIMELINE;
import static ai.rnt.crm.constants.CRMConstants.UPNEXT_DATA;
import static ai.rnt.crm.constants.ExcelConstants.FLAG;
import static ai.rnt.crm.constants.ExcelConstants.LEAD_DATA;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.LEAD_NAME;
import static ai.rnt.crm.constants.LeadEntityFieldConstant.TOPIC;
import static ai.rnt.crm.constants.MessageConstants.MSG;
import static ai.rnt.crm.constants.StatusConstants.ALL;
import static ai.rnt.crm.constants.StatusConstants.ALL_LEAD;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_DISQUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_AS_QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.CLOSE_LEAD;
import static ai.rnt.crm.constants.StatusConstants.DISQUALIFIED_LEAD;
import static ai.rnt.crm.constants.StatusConstants.LEAD;
import static ai.rnt.crm.constants.StatusConstants.OPEN;
import static ai.rnt.crm.constants.StatusConstants.OPEN_LEAD;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED;
import static ai.rnt.crm.constants.StatusConstants.QUALIFIED_LEAD;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTO;
import static ai.rnt.crm.dto_mapper.ContactDtoMapper.TO_CONTACT_DTOS;
import static ai.rnt.crm.dto_mapper.DomainMasterDtoMapper.TO_DOMAIN_DTOS;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employees;
import static ai.rnt.crm.dto_mapper.LeadSortFilterDtoMapper.TO_LEAD_SORT_FILTER;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE_DTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_LEADDTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DESCRIPTION;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_EDITLEAD_DTO;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD_DTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_QUALIFY_LEAD;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICE_FALL_MASTER_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.ASSIGNED_TO_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.CLOSE_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.DISQUALIFIED_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.OPEN_LEAD_FILTER;
import static ai.rnt.crm.functional.predicates.LeadsPredicates.QUALIFIED_LEAD_FILTER;
import static ai.rnt.crm.util.CommonUtil.addCurrencyDetails;
import static ai.rnt.crm.util.CommonUtil.getActivityData;
import static ai.rnt.crm.util.CommonUtil.getDescData;
import static ai.rnt.crm.util.CommonUtil.getTaskDataMap;
import static ai.rnt.crm.util.CommonUtil.getTimelineData;
import static ai.rnt.crm.util.CommonUtil.getUpnextData;
import static ai.rnt.crm.util.CommonUtil.setDomainToLead;
import static ai.rnt.crm.util.CommonUtil.setLeadSourceToLead;
import static ai.rnt.crm.util.CommonUtil.setServiceFallToLead;
import static ai.rnt.crm.util.CommonUtil.upNextActivities;
import static ai.rnt.crm.util.CompanyUtil.addUpdateCompanyDetails;
import static ai.rnt.crm.util.LeadsCardUtil.checkDuplicateLead;
import static ai.rnt.crm.util.XSSUtil.sanitize;
import static java.lang.Boolean.TRUE;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static org.apache.poi.ss.usermodel.WorkbookFactory.create;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.constants.ApiResponseKeyConstant;
import ai.rnt.crm.constants.OppurtunityStatus;
import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.dao.service.DomainMasterDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.ExcelHeaderDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSortFilterDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.MeetingDaoService;
import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.DescriptionDto;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.LeadSortFilterDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.Contacts;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.DomainMaster;
import ai.rnt.crm.entity.Email;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.ExcelHeaderMaster;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.LeadSourceMaster;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.Meetings;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.entity.ServiceFallsMaster;
import ai.rnt.crm.entity.TaskNotifications;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.functional.custominterface.impl.LeadsCardMapperImpl;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.EmailUtil;
import ai.rnt.crm.util.ReadExcelUtil;
import ai.rnt.crm.util.SignatureUtil;
import ai.rnt.crm.util.TaskNotificationsUtil;
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
@PropertySource("classpath:confidential.properties")
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
	private final ContactDaoService contactDaoService;
	private final DomainMasterDaoService domainMasterDaoService;
	private final OpportunityDaoService opportunityDaoService;
	private final EmailUtil emailUtil;
	private final SignatureUtil signatureUtil;
	private final TaskNotificationsUtil taskNotificationsUtil;
	private final CurrencyDaoService currencyDaoService;

	private static final String PRIMFIELD = "PrimaryField";
	private static final String SECNDFIELD = "SecondaryField";

	@Value("${secretkey}")
	private String secretKey;

	@Value("${cbcalgo}")
	private String cbcAlgo;

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(LeadDto leadDto) {
		log.info("inside the createLead method...");
		EnumMap<ApiResponse, Object> createMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads leads = TO_LEAD.apply(leadDto).orElseThrow(ResourceNotFoundException::new);
			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(leadDto.getCompanyName());
			leads.setStatus(OPEN);
			if (nonNull(leadDto.getBudgetAmount()) && !leadDto.getBudgetAmount().isEmpty())
				leads.setBudgetAmount(signatureUtil.decryptAmount(leadDto.getBudgetAmount(), secretKey, cbcAlgo));
			else
				leads.setBudgetAmount(leadDto.getBudgetAmount());

			leads.setDisqualifyAs(OPEN);
			leads.setPseudoName(auditAwareUtil.getLoggedInUserName());
			setServiceFallToLead(leadDto.getServiceFallsId(), leads, serviceFallsDaoSevice);
			setLeadSourceToLead(leadDto.getLeadSourceId(), leads, leadSourceDaoService);
			setDomainToLead(leadDto.getDomainId(), leads, domainMasterDaoService);
			employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(leads::setAssignBy);
			leads.setAssignDate(LocalDate.now());
			if (nonNull(leadDto.getAssignTo()))
				employeeService.getById(leadDto.getAssignTo()).ifPresent(leads::setEmployee);
			else
				employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(leads::setEmployee);
			createMap.put(SUCCESS, true);
			Contacts contacts = setContactDetailsToLead(existCompany, leadDto, leads);
			if (checkDuplicateLead(leadDaoService.getAllLeads(), leads)) {
				createMap.put(MESSAGE, "Lead Already Exists !!");
				createMap.put(SUCCESS, false);
			} else if (nonNull(contactDaoService.addContact(contacts)))
				createMap.put(MESSAGE, "Lead Added Successfully !!");
			else
				createMap.put(MESSAGE, "Lead Not Added !!");
			return new ResponseEntity<>(createMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while creating new lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadsByStatus(String leadsStatus) {
		log.info("inside the open view dashboard data getLeadsByStatus method... ");
		EnumMap<ApiResponse, Object> getAllLeads = new EnumMap<>(ApiResponse.class);
		getAllLeads.put(SUCCESS, true);
		try {
			if (isNull(leadsStatus)) {
				Map<String, Object> dataMap = new HashMap<>();
				List<Leads> leads = leadDaoService.getAllLeads();
				Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
				List<LeadImportant> impLead = leadDaoService.findLeadByEmployeeStaffId(loggedInStaffId);
				List<Leads> allLeads = leads.stream()
						.sorted((l1, l2) -> l2.getCreatedDate().compareTo(l1.getCreatedDate())).collect(toList());
				allLeads.stream().forEach(lead -> lead.setImportant(impLead.stream().map(LeadImportant::getLead)
						.anyMatch(l -> l.getLeadId().equals(lead.getLeadId()))));

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
					dataMap.put(
							ALL_LEAD, leads
									.stream().map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead,
											primaryField, secondaryField, lead.getContacts().stream()
													.filter(Contacts::getPrimary).findFirst().orElse(null)))
									.collect(toList()));
					dataMap.put(
							OPEN_LEAD, allLeads
									.stream().filter(OPEN_LEAD_FILTER).map(
											lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
													secondaryField, lead.getContacts().stream()
															.filter(Contacts::getPrimary).findFirst().orElse(null)))
									.collect(toList()));
					dataMap.put(
							CLOSE_LEAD, leads
									.stream().filter(CLOSE_LEAD_FILTER).map(
											lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
													secondaryField, lead.getContacts().stream()
															.filter(Contacts::getPrimary).findFirst().orElse(null)))
									.collect(toList()));
				} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
					dataMap.put(
							ALL_LEAD, leads
									.stream().filter(l -> ASSIGNED_TO_FILTER.test(l, loggedInStaffId)).map(
											lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
													secondaryField, lead.getContacts().stream()
															.filter(Contacts::getPrimary).findFirst().orElse(null)))
									.collect(toList()));
					dataMap.put(OPEN_LEAD, allLeads.stream()
							.filter(l -> OPEN_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
									secondaryField,
									lead.getContacts().stream().filter(Contacts::getPrimary).findFirst().orElse(null)))
							.collect(toList()));
					dataMap.put(CLOSE_LEAD, leads.stream()
							.filter(l -> CLOSE_LEAD_FILTER.test(l) && ASSIGNED_TO_FILTER.test(l, loggedInStaffId))
							.map(lead -> new LeadsCardMapperImpl().mapLeadToLeadsCardDto(lead, primaryField,
									secondaryField,
									lead.getContacts().stream().filter(Contacts::getPrimary).findFirst().orElse(null)))
							.collect(toList()));
				} else
					dataMap.put(ApiResponseKeyConstant.DATA, emptyList());
				getAllLeads.put(DATA, dataMap);
			} else
				getAllLeads.put(DATA, TO_LEAD_DTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus)));
			return new ResponseEntity<>(getAllLeads, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting open view leads data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllLeadSource() {
		log.info("inside the getAllLeadSource method...");
		EnumMap<ApiResponse, Object> resultMap = new EnumMap<>(ApiResponse.class);
		try {
			resultMap.put(SUCCESS, true);
			resultMap.put(DATA, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting lead source..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllDropDownData() {
		log.info("inside the getAllDropDownData method...");
		EnumMap<ApiResponse, Object> allDataMap = new EnumMap<>(ApiResponse.class);
		try {
			allDataMap.put(SUCCESS, true);
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put(SERVICE_FALL_DATA,
					TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put(LEAD_SOURCE_DATA, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put(DOMAIN_MASTER_DATA, TO_DOMAIN_DTOS.apply(domainMasterDaoService.getAllDomains()));
			dataMap.put(ASSIGN_DATA, TO_Employees.apply(roleMasterDaoService.getAdminAndUser()));
			allDataMap.put(DATA, dataMap);
			return new ResponseEntity<>(allDataMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting all dropdown data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardData() {
		log.info("inside the getLeadDashboardData method...");
		EnumMap<ApiResponse, Object> leadsDashboardData = new EnumMap<>(ApiResponse.class);
		try {
			leadsDashboardData.put(SUCCESS, true);
			leadsDashboardData.put(DATA, TO_DASHBOARD_LEADDTOS.apply(leadDaoService.getLeadDashboardData()));
			return new ResponseEntity<>(leadsDashboardData, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting lead DashboardData..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getLeadDashboardDataByStatus(String leadsStatus) {
		log.info("inside the getLeadDashboardDataByStatus method...{}", leadsStatus);
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
			return new ResponseEntity<>(leadsDataByStatus, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting LeadDashboardDataByStatus..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(Integer leadId) {
		log.info("inside the editLead method...{}", leadId);
		EnumMap<ApiResponse, Object> lead = new EnumMap<>(ApiResponse.class);
		try {
			Map<String, Object> dataMap = new LinkedHashMap<>();

			Leads leadById = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			Optional<EditLeadDto> dto = TO_EDITLEAD_DTO.apply(leadById);
			dto.ifPresent(e -> {
				e.setPrimaryContact(TO_CONTACT_DTO
						.apply(leadById.getContacts().stream().filter(Contacts::getPrimary).findFirst()
								.orElseThrow(() -> new ResourceNotFoundException("Priamry Contact")))
						.orElseThrow(ResourceNotFoundException::new));
				e.setDropDownAssignTo(leadById.getEmployee().getStaffId());
				e.setMessage("Assigned To " + leadById.getEmployee().getFirstName() + " "
						+ leadById.getEmployee().getLastName());
				e.setAssignBy(leadById.getAssignBy().getFirstName() + " " + leadById.getAssignBy().getLastName());
				e.setAssignDate(leadById.getAssignDate());
				e.setCreatedDate(leadById.getCreatedDate());
				EmployeeMaster employeeMaster = employeeService.getById(leadById.getCreatedBy())
						.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, leadById.getCreatedBy()));
				e.setGeneratedBy(employeeMaster.getFirstName() + " " + employeeMaster.getLastName());
			});
			List<Call> calls = callDaoService.getCallsByLeadIdAndIsOpportunity(leadId);
			List<Visit> visits = visitDaoService.getVisitsByLeadIdAndIsOpportunity(leadId);
			List<Email> emails = emailDaoService.getEmailByLeadIdAndIsOpportunity(leadId);
			List<Meetings> meetings = meetingDaoService.getMeetingByLeadIdAndIsOpportunity(leadId);
			List<Description> descriptions = leadDaoService.getDescriptionByLeadIdAndIsOpportunity(leadId);
			dataMap.put(LEAD_INFO, dto);
			dataMap.put(SERVICE_FALL, TO_SERVICE_FALL_MASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put(LEAD_SOURCE, TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put(DOMAINS, TO_DOMAIN_DTOS.apply(domainMasterDaoService.getAllDomains()));
			dataMap.put(TIMELINE, getTimelineData(calls, visits, emails, meetings, employeeService));
			dataMap.put("descriptionData", getDescData(descriptions));
			dataMap.put(ACTIVITY, getActivityData(calls, visits, emails, meetings, employeeService));
			dataMap.put(UPNEXT_DATA, upNextActivities(getUpnextData(calls, visits, emails, meetings, employeeService)));
			dataMap.put(TASK, getTaskDataMap(calls, visits, meetings, leadById, null));
			lead.put(SUCCESS, true);
			lead.put(DATA, dataMap);
			return new ResponseEntity<>(lead, OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Got Exception while editing the lead data..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> qualifyLead(Integer leadId, QualifyLeadDto dto) {
		log.info("inside the qualifyLead method...{}", leadId);
		EnumMap<ApiResponse, Object> qualifyLeadMap = new EnumMap<>(ApiResponse.class);
		try {
			Opportunity opportunity = null;
			Opportunity optyData = null;
			qualifyLeadMap.put(SUCCESS, true);
			qualifyLeadMap.put(MESSAGE, "Lead Not Qualify");
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			if (nonNull(lead.getOpportunity()))
				optyData = lead.getOpportunity();

			lead.setRequirementShared(dto.getRequirementShared());
			lead.setIdentifyDecisionMaker(dto.getIdentifyDecisionMaker());
			lead.setFirstMeetingDone(dto.getFirstMeetingDone());
			lead.setCustomerReadiness(dto.getCustomerReadiness());
			lead.setQualifyRemarks(dto.getQualifyRemarks());
			lead.setCurrentPhase(dto.getCurrentPhase());
			lead.setProgressStatus(dto.getProgressStatus());
			if (nonNull(dto.getBudgetAmount()) && !dto.getBudgetAmount().isEmpty()) {
				lead.setBudgetAmount(signatureUtil.decryptAmount(dto.getBudgetAmount(), secretKey, cbcAlgo));
				if (nonNull(optyData)) {
					optyData.setBudgetAmount(signatureUtil.decryptAmount(dto.getBudgetAmount(), secretKey, cbcAlgo));
					optyData.setClosedOn(dto.getClosedOn());
				}
			} else {
				lead.setBudgetAmount(dto.getBudgetAmount());
				if (nonNull(optyData))
					optyData.setBudgetAmount(dto.getBudgetAmount());
			}
			lead.setDisqualifyAs(QUALIFIED);
			lead.setStatus(CLOSE_AS_QUALIFIED);
			if (nonNull(dto.getCurrency()))
				addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
						dto.getCurrency().getCurrencyName(), dto.getCurrency().getCurrencyId())
						.ifPresent(lead::setCurrency);

			if (nonNull(dto.getQualify()) && TRUE.equals(dto.getQualify())) {
				opportunity = addToOpputunity(lead, dto.getClosedOn());
				if (nonNull(leadDaoService.addLead(lead)) && nonNull(opportunity)) {
					qualifyLeadMap.put(MESSAGE, "Lead Qualified Successfully");
					qualifyLeadMap.put(DATA, opportunity.getOpportunityId());
				} else
					qualifyLeadMap.put(SUCCESS, false);
			} else {
				if (nonNull(leadDaoService.addLead(lead))) {
					opportunityDaoService.addOpportunity(optyData);
					qualifyLeadMap.put(MESSAGE, "Save Successfully");
				} else
					qualifyLeadMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(qualifyLeadMap, OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Got Exception while qualifying the lead..{}", e.getMessage());
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
			employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(leads::setAssignBy);
			leads.setAssignDate(LocalDate.now());
			if (nonNull(leadDaoService.addLead(leads))) {
				boolean sendLeadAssignMail = emailUtil.sendLeadAssignMail(leads);
				assignLeadNotification(map.get(LEAD_ID));
				if (sendLeadAssignMail)
					resultMap.put(MESSAGE, "Lead Assigned Successfully !!");
				else
					resultMap.put(MESSAGE, "Lead Assigned but problem while sending email !!");
				resultMap.put(SUCCESS, true);
			} else {
				resultMap.put(MESSAGE, "Lead Not Assign !!");
				resultMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(resultMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while assigning the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> disQualifyLead(Integer leadId, LeadDto dto) {
		log.info("inside the disQualifyLead method...{}", leadId);
		EnumMap<ApiResponse, Object> disQualifiedMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setDisqualifyAs(dto.getDisqualifyAs());
			lead.setDisqualifyReason(dto.getDisqualifyReason());
			lead.setStatus(CLOSE_AS_DISQUALIFIED);
			if (nonNull(leadDaoService.addLead(lead))) {
				disQualifiedMap.put(MESSAGE, "Lead Disqualified Successfully");
				disQualifiedMap.put(SUCCESS, true);
			} else {
				disQualifiedMap.put(MESSAGE, "Lead Not Disqualify");
				disQualifiedMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(disQualifiedMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while disQualifyLead the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadContact(Integer leadId, UpdateLeadDto dto) {
		log.info("inside the updateLeadContact method...{}", leadId);
		EnumMap<ApiResponse, Object> updateMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setTopic(dto.getTopic());
			if (nonNull(dto.getBudgetAmount()) && !dto.getBudgetAmount().isEmpty())
				lead.setBudgetAmount(signatureUtil.decryptAmount(dto.getBudgetAmount(), secretKey, cbcAlgo));
			else
				lead.setBudgetAmount(dto.getBudgetAmount());
			lead.setCustomerNeed(dto.getCustomerNeed());
			lead.setProposedSolution(dto.getProposedSolution());
			lead.setPseudoName(dto.getPseudoName());

			if (nonNull(dto.getCurrency()))
				addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
						dto.getCurrency().getCurrencyName(), dto.getCurrency().getCurrencyId())
						.ifPresent(lead::setCurrency);

			Contacts contact = lead.getContacts().stream().filter(Contacts::getPrimary).findFirst()
					.orElseThrow(() -> new ResourceNotFoundException("Primary Contact"));
			addUpdateCompanyDetails(cityDaoService, stateDaoService, countryDaoService, companyMasterDaoService,
					currencyDaoService, dto, contact);
			setServiceFallToLead(dto.getServiceFallsId(), lead, serviceFallsDaoSevice);
			setLeadSourceToLead(dto.getLeadSourceId(), lead, leadSourceDaoService);
			setDomainToLead(dto.getDomainId(), lead, domainMasterDaoService);
			contact.setLinkedinId(dto.getLinkedinId());
			contact.setContactNumberPrimary(dto.getPhoneNumber());
			if (nonNull(contactDaoService.addContact(contact)) && nonNull(leadDaoService.addLead(lead)))
				updateMap.put(MESSAGE, "Lead Details Updated Successfully !!");
			else
				updateMap.put(MESSAGE, "Lead Details Not Updated !!");
			updateMap.put(SUCCESS, true);
			return new ResponseEntity<>(updateMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while updateLeadContact..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> importantLead(Integer leadId, boolean status) {
		log.info("inside the importantLead method...{} {}", leadId, status);
		EnumMap<ApiResponse, Object> impLeadMap = new EnumMap<>(ApiResponse.class);
		impLeadMap.put(SUCCESS, true);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			if (status) {
				LeadImportant importantLead = new LeadImportant();
				importantLead.setLead(leadDaoService.getLeadById(leadId)
						.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId)));
				importantLead.setEmployee(employeeService.getById(loggedInStaffId)
						.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, loggedInStaffId)));
				impLeadMap.put(MESSAGE, "Problem Occurred While Making Lead Important !!");
				if (leadDaoService.addImportantLead(importantLead).isPresent())
					impLeadMap.put(MESSAGE, "Lead marked as Important !!");
			} else {
				impLeadMap.put(MESSAGE, "Lead not found as important !!");
				if (leadDaoService.deleteImportantLead(leadId, loggedInStaffId))
					impLeadMap.put(MESSAGE, "Lead marked as Unimportant !!");
			}
		} catch (Exception e) {
			log.error("error occured while updating the Lead in ImportandLead API... {}", e.getMessage());
			if (e instanceof DataIntegrityViolationException)
				impLeadMap.put(MESSAGE, "Lead already marked as important !!");
			else
				throw new CRMException(e);
		}
		return new ResponseEntity<>(impLeadMap, CREATED);
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> reactiveLead(Integer leadId) {
		log.info("inside the reactiveLead method...{}", leadId);
		EnumMap<ApiResponse, Object> reactiveLeadMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setDisqualifyAs(OPEN);
			lead.setDisqualifyReason(null);
			lead.setStatus(OPEN);
			if (nonNull(leadDaoService.addLead(lead))) {
				reactiveLeadMap.put(MESSAGE, "Lead Reactivate Successfully");
				reactiveLeadMap.put(SUCCESS, true);
			} else {
				reactiveLeadMap.put(MESSAGE, "Lead Not Reactivate");
				reactiveLeadMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(reactiveLeadMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while reactivating the lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addSortFilterForLeads(LeadSortFilterDto sortFilter) {
		log.info("inside the addSortFilterForLeads method...");
		try {
			EnumMap<ApiResponse, Object> sortFilterMap = new EnumMap<>(ApiResponse.class);
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			sortFilter.setEmployee(TO_EMPLOYEE
					.apply(employeeService.getById(loggedInStaffId)
							.orElseThrow(() -> new ResourceNotFoundException(EMPLOYEE, STAFF_ID, loggedInStaffId)))
					.orElseThrow(null));
			if (nonNull(leadSortFilterDaoService.save(TO_LEAD_SORT_FILTER.apply(sortFilter).orElse(null)))) {
				sortFilterMap.put(MESSAGE, "Lead Sort Filter Added Successfully!!");
				sortFilterMap.put(SUCCESS, true);
			} else {
				sortFilterMap.put(MESSAGE, "Lead Sort Filter Not Added!!");
				sortFilterMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(sortFilterMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the sort filter for lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getForQualifyLead(Integer leadId) {
		log.info("inside the getForQualifyLead method...{}", leadId);
		try {
			EnumMap<ApiResponse, Object> qualifiedLeadMap = new EnumMap<>(ApiResponse.class);
			qualifiedLeadMap.put(DATA, TO_QUALIFY_LEAD.apply(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId))));
			qualifiedLeadMap.put(SUCCESS, true);
			return new ResponseEntity<>(qualifiedLeadMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while getting the Qualified lead for edit..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> uploadExcel(MultipartFile file) {
		log.info("inside the uploadExcel method...");
		EnumMap<ApiResponse, Object> excelMap = new EnumMap<>(ApiResponse.class);
		try (Workbook workbook = create(file.getInputStream())) {
			excelMap.put(SUCCESS, false);
			Sheet sheet = workbook.getSheetAt(0);
			if (isValidExcel(sheet)) {
				Map<String, Object> excelData = readExcelUtil.readExcelFile(workbook, sheet);
				if (isNull(excelData) || excelData.isEmpty()) {
					excelMap.put(MESSAGE, "Excel Contains No Data!!");
					return new ResponseEntity<>(excelMap, BAD_REQUEST);
				} else {
					if ((nonNull(excelData) && !excelData.isEmpty() && excelData.containsKey(FLAG)
							&& (boolean) excelData.get(FLAG))) {
						List<LeadDto> leadDtos = (List<LeadDto>) excelData.get(LEAD_DATA);
						int saveLeadCount = leadDtos.stream().filter(Objects::nonNull).mapToInt(leadDto -> {
							Contacts contact = buildLeadObj(leadDto);
							Leads leads = contact.getLead();
							setAssignToNameForTheLead(leads,
									nonNull(leadDto.getPseudoName()) && !leadDto.getPseudoName().isEmpty()
											? leadDto.getPseudoName().split(" ")
											: auditAwareUtil.getLoggedInUserName().split(" "));
							leads.setPseudoName(null);// because we added the assign to person name from excel data
														// object
							if (checkDuplicateLead(leadDaoService.getAllLeads(), leads))
								return 0;
							return nonNull(contactDaoService.addContact(contact)) ? 1 : 0;
						}).sum();
						int duplicateLead = leadDtos.size() - saveLeadCount;
						excelMap.put(SUCCESS, saveLeadCount > 0);
						excelMap.put(MESSAGE,
								saveLeadCount + " Leads Added And " + duplicateLead + " Duplicate Found!!");
						if (duplicateLead == 0 && saveLeadCount == 0)
							excelMap.put(MESSAGE, "No Lead Found To Add !!");
						return new ResponseEntity<>(excelMap, CREATED);
					} else {
						excelMap.put(MESSAGE, excelData.get(MSG));
						return new ResponseEntity<>(excelMap, BAD_REQUEST);
					}
				}
			} else {
				excelMap.put(MESSAGE, "Invalid Excel Format!!");
				return new ResponseEntity<>(excelMap, BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("Got Exception while uploading the Excel of lead..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public boolean isValidExcel(Sheet sheet) throws IOException {
		log.info("inside the isValidExcel method...");
		List<String> dbHeaderNames = excelHeaderDaoService.getExcelHeadersFromDB().stream()
				.map(ExcelHeaderMaster::getHeaderName).filter(Objects::nonNull).map(String::trim).collect(toList());
		List<String> excelHeader = readExcelUtil.readExcelHeaders(sheet).stream().filter(Objects::nonNull)
				.map(String::trim).collect(toList());
		return (nonNull(excelHeader) && !excelHeader.isEmpty()) && (nonNull(dbHeaderNames) && !dbHeaderNames.isEmpty())
				&& excelHeader.stream().allMatch(dbHeaderNames::contains) && excelHeader.containsAll(dbHeaderNames);
	}

	public Contacts buildLeadObj(LeadDto leadDto) {
		log.info("inside the buildLeadObj method...");
		Leads leads = TO_LEAD.apply(leadDto).orElseThrow(ResourceNotFoundException::new);
		Contacts contact = null;
		try {
			leads.setStatus(OPEN);
			leads.setDisqualifyAs(OPEN);
			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(leadDto.getCompanyName());
			contact = setContactDetailsToLead(existCompany, leadDto, leads);
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
			if (nonNull(leadDto.getDomainId()) && !leadDto.getDomainId().isEmpty()) {
				Optional<DomainMaster> domain = domainMasterDaoService.findByName(leadDto.getDomainId());
				if (domain.isPresent())
					domain.ifPresent(leads::setDomainMaster);
				else {
					DomainMaster newDomain = new DomainMaster();
					newDomain.setDomainName(leadDto.getDomainId());
					domainMasterDaoService.addDomain(newDomain).ifPresent(leads::setDomainMaster);
				}
			} else
				domainMasterDaoService.findByName(OTHER).ifPresent(leads::setDomainMaster);
		} catch (Exception e) {
			log.error("error while building the lead Object...{}", e.getMessage());
		}
		return contact;
	}

	public void setAssignToNameForTheLead(Leads leads, String[] split) {
		log.info("inside the setAssignToNameForTheLead method...");
		String firstName;
		String lastName = null;
		if (split.length > 1) {
			firstName = split[0];
			lastName = split[1];
		} else
			firstName = split[0];
		employeeService.findByName(firstName, lastName).ifPresent(leads::setEmployee);
	}

	public Contacts setContactDetailsToLead(Optional<CompanyDto> existCompany, LeadDto leadDto, Leads leads) throws ResourceNotFoundException, Exception {
		log.info("inside the setContactDetailsToLead method...");
		Contacts contact = new Contacts();
		contact.setFirstName(leadDto.getFirstName());
		contact.setLastName(leadDto.getLastName());
		contact.setWorkEmail(leadDto.getEmail());
		contact.setContactNumberPrimary(leadDto.getPhoneNumber());
		contact.setDesignation(leadDto.getDesignation());
		contact.setBusinessCard(leadDto.getBusinessCard());
		contact.setBusinessCardName(leadDto.getBusinessCardName());
		contact.setBusinessCardType(leadDto.getBusinessCardType());
		contact.setLinkedinId(leadDto.getLinkedinId());
		contact.setPrimary(true);
		setCompanyDetailsToContact(existCompany, leadDto, contact, leads);
		contact.setLead(leads);
		leads.setContacts(asList(contact));
		return contact;
	}

	public void setCompanyDetailsToContact(Optional<CompanyDto> existCompany, LeadDto leadDto, Contacts contact,
			Leads leads) throws ResourceNotFoundException, Exception {
		log.info("inside the setCompanyDetailsToContact method...");
		if (existCompany.isPresent()) {
			existCompany.get().setCompanyWebsite(leadDto.getCompanyWebsite());
			CompanyMaster company = TO_COMPANY.apply(existCompany.orElseThrow(ResourceNotFoundException::new))
					.orElseThrow(ResourceNotFoundException::new);
			setLocationToCompany(leadDto.getLocation(), company, leadDto, leads);
			contact.setCompanyMaster(
					TO_COMPANY.apply(companyMasterDaoService.save(company).orElseThrow(ResourceNotFoundException::new))
							.orElseThrow(ResourceNotFoundException::new));
		} else {
			CompanyMaster company = TO_COMPANY
					.apply(CompanyDto.builder().companyName(leadDto.getCompanyName())
							.companyWebsite(leadDto.getCompanyWebsite()).build())
					.orElseThrow(ResourceNotFoundException::new);
			setLocationToCompany(leadDto.getLocation(), company, leadDto, leads);
			companyMasterDaoService.save(company)
					.ifPresent(e -> TO_COMPANY.apply(e).ifPresent(contact::setCompanyMaster));
		}

	}

	public void setLocationToCompany(String location, CompanyMaster company, LeadDto leadDto, Leads leads) {
		log.info("inside the setLocationToCompany method...{} ", location);
		if (nonNull(location) && !location.isEmpty()) {
			Optional<CountryMaster> country = countryDaoService.findByCountryName(location);
			if (country.isPresent()) {
				if (isNull(country.get().getCurrency())) {
					addCurrencyDetails(currencyDaoService, leadDto.getCurrencySymbol(), leadDto.getCurrencyName(), null)
							.ifPresent(country.get()::setCurrency);
					company.setCountry(countryDaoService.addCountry(country.get()));
					leads.setCurrency(company.getCountry().getCurrency());
				} else {
					country.ifPresent(company::setCountry);
					addCurrencyDetails(currencyDaoService, leadDto.getCurrencySymbol(), leadDto.getCurrencyName(), null)
							.ifPresent(leads::setCurrency);
				}
			} else {
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountry(sanitize(location));
				addCurrencyDetails(currencyDaoService, leadDto.getCurrencySymbol(), leadDto.getCurrencyName(), null)
						.ifPresent(countryMaster::setCurrency);
				company.setCountry(countryDaoService.addCountry(countryMaster));
				leads.setCurrency(company.getCountry().getCurrency());
			}
		} else
			addCurrencyDetails(currencyDaoService, leadDto.getCurrencySymbol(), leadDto.getCurrencyName(), null)
					.ifPresent(leads::setCurrency);
	}

	@Override
	public void updateLeadsStatus(Integer leadId) {
		log.info("inside the updateLeadsStatus method..{}", leadId);
		try {
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId));
			lead.setDisqualifyAs(QUALIFIED);
			leadDaoService.addLead(lead);
		} catch (Exception e) {
			log.error("Got Exception while updating the lead status..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	public Opportunity addToOpputunity(Leads leads, LocalDate closedDate) {
		Opportunity opportunity = new Opportunity();
		opportunity.setStatus(OppurtunityStatus.QUALIFY);
		opportunity.setBudgetAmount(leads.getBudgetAmount());
		opportunity.setTopic(leads.getTopic());
		opportunity.setPseudoName(leads.getPseudoName());
		opportunity.setCurrentPhase(leads.getCurrentPhase());
		opportunity.setProgressStatus(leads.getProgressStatus());
		opportunity.setEmployee(leads.getEmployee());
		opportunity.setLeads(leads);
		opportunity.setCurrency(leads.getCurrency());
		opportunity.setClosedOn(closedDate);
		employeeService.getById(auditAwareUtil.getLoggedInStaffId()).ifPresent(opportunity::setAssignBy);
		opportunity.setAssignDate(now());
		return opportunityDaoService.addOpportunity(opportunity);
	}

	public void assignLeadNotification(Integer leadId) {
		log.info("inside assignLeadNotification method...{}", leadId);
		try {
			TaskNotifications taskNotifications = new TaskNotifications();
			taskNotifications.setLeads(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException("Leads", "leadId", leadId)));
			taskNotifications.setCreatedBy(1375);
			taskNotifications.setNotifTo(taskNotifications.getLeads().getEmployee());
			taskNotifications.setNotifStatus(true);
			taskNotificationsUtil.sendFollowUpLeadNotification(taskNotifications);
		} catch (Exception e) {
			log.error("Got Exception while sending assign lead notification..{}", e);
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addDescription(DescriptionDto dto, Integer leadId) {
		log.info("inside the addDescription method...{}", leadId);
		EnumMap<ApiResponse, Object> dataMap = new EnumMap<>(ApiResponse.class);
		try {
			Description description = TO_DESCRIPTION.apply(dto)
					.orElseThrow(() -> new ResourceNotFoundException("Description", "desc_id", dto.getDescId()));
			Leads lead = leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadId));
			description.setLead(lead);
			if (nonNull(leadDaoService.addDesc(description))) {
				dataMap.put(MESSAGE, "Description Added Successfully");
				dataMap.put(SUCCESS, true);
			} else {
				dataMap.put(MESSAGE, "Description Not Added");
				dataMap.put(SUCCESS, false);
			}
			return new ResponseEntity<>(dataMap, CREATED);
		} catch (Exception e) {
			log.error("Got Exception while adding the description..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getContactInfo(Integer leadId) {
		log.info("inside the addDescription method...{}", leadId);
		EnumMap<ApiResponse, Object> contactInfoMap = new EnumMap<>(ApiResponse.class);
		try {
			contactInfoMap.put(SUCCESS, true);
			contactInfoMap.put(DATA, TO_CONTACT_DTOS.apply(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException(LEAD, LEAD_ID, leadId)).getContacts()));
			return new ResponseEntity<>(contactInfoMap, OK);
		} catch (Exception e) {
			log.error("Got Exception while the getContactInfo..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
