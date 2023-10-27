package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.AttachmentDtoMapper.TO_ATTACHMENT_DTOS;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employee;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_Employees;
import static ai.rnt.crm.dto_mapper.LeadSourceDtoMapper.TO_LEAD_SOURCE_DTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_CARDS_LEADDTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_DASHBOARD_LEADDTOS;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_EDITLEAD_DTO;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD_DTOS;
import static ai.rnt.crm.dto_mapper.ServiceFallsDtoMapper.TO_SERVICEFALLMASTER_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.AddCallDaoService;
import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.EmailDaoService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.dto.EditCallDto;
import ai.rnt.crm.dto.EditEmailDto;
import ai.rnt.crm.dto.EditLeadDto;
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.LeadService;
import ai.rnt.crm.util.AuditAwareUtil;
import ai.rnt.crm.util.ConvertDateFormatUtil;
import ai.rnt.crm.util.LeadsCardUtil;
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
	private final AddCallDaoService addCallDaoService;
	private final EmailDaoService emailDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final VisitDaoService visitDaoService;
	private final CityDaoService cityDaoService;
	private final StateDaoService stateDaoService;
	private final CountryDaoService countryDaoService;

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
			leads.setStatus("Open");
			leads.setDisqualifyAs("Open");
			leads.setPseudoName(auditAwareUtil.getLoggedInUserName());
			serviceFallsDaoSevice.getById(leadDto.getServiceFallsId()).ifPresent(leads::setServiceFallsMaster);
			leadSourceDaoService.getById(leadDto.getLeadSourceId()).ifPresent(leads::setLeadSourceMaster);
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

				if (auditAwareUtil.isAdmin()) {
					dataMap.put("allLead", TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads));
					dataMap.put("openLead", TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads.stream().filter(l -> nonNull(
							l.getStatus())
							&& (l.getStatus().equalsIgnoreCase("new") || l.getStatus().equalsIgnoreCase("open")))
							.collect(Collectors.toList())));
					dataMap.put("closeLead", TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads.stream()
							.filter(l -> !l.getStatus().equalsIgnoreCase("open")).collect(Collectors.toList())));
				} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
					dataMap.put("allLead",
							TO_DASHBOARD_CARDS_LEADDTOS.apply(
									allLeads.stream().filter(l -> l.getEmployee().getStaffId().equals(loggedInStaffId))
											.collect(Collectors.toList())));
					dataMap.put("openLead",
							TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads.stream()
									.filter(l -> (nonNull(l.getStatus()) && (l.getStatus().equalsIgnoreCase("new")
											|| l.getStatus().equalsIgnoreCase("open")))
											&& l.getEmployee().getStaffId().equals(loggedInStaffId))
									.collect(Collectors.toList())));
					dataMap.put("closeLead",
							TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads.stream()
									.filter(l -> !l.getStatus().equalsIgnoreCase("open")
											&& l.getEmployee().getStaffId().equals(loggedInStaffId))
									.collect(Collectors.toList())));
				} else
					dataMap.put("Data", Collections.emptyList());
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
			dataMap.put("serviceFallData", TO_SERVICEFALLMASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
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
			if (auditAwareUtil.isAdmin()) {
				if (nonNull(leadsStatus) && leadsStatus.equalsIgnoreCase("All"))
					leadsDataByStatus.put(DATA, TO_DASHBOARD_LEADDTOS.apply(leadDashboardData));
				else
					leadsDataByStatus.put(DATA, TO_DASHBOARD_LEADDTOS
							.apply(leadDaoService.getLeadsByStatus(leadsStatus).stream().collect(Collectors.toList())));
			} else if (auditAwareUtil.isUser() && nonNull(loggedInStaffId)) {
				if (nonNull(leadsStatus) && leadsStatus.equalsIgnoreCase("All"))
					leadsDataByStatus.put(DATA,
							TO_DASHBOARD_LEADDTOS.apply(leadDashboardData.stream()
									.filter(d -> d.getEmployee().getStaffId().equals(loggedInStaffId))
									.collect(Collectors.toList())));
				else
					leadsDataByStatus.put(DATA,
							TO_DASHBOARD_LEADDTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus).stream()
									.filter(d -> d.getEmployee().getStaffId().equals(loggedInStaffId))
									.collect(Collectors.toList())));
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
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
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

			List<TimeLineActivityDto> timeLine = new ArrayList<>();
			List<EditCallDto> list = addCallDaoService.getCallsByLeadId(leadId).stream()
					.filter(call -> nonNull(call.getStatus()) && call.getStatus().equalsIgnoreCase("complete"))
					.map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getAddCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType("Call");
						callDto.setBody(call.getComment());
						callDto.setDueDate(nonNull(call.getDueDate()) ? dateFormat.format(call.getDueDate()) : null);
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDate(call.getUpdatedDate()));
						callDto.setShortName(LeadsCardUtil.shortName(call.getCallTo()));
						TO_Employee.apply(call.getCallFrom())
								.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());
			timeLine.addAll(list);
			timeLine.addAll(emailDaoService.getEmailByLeadId(leadId).stream()
					.filter(email -> nonNull(email.getStatus()) && email.getStatus().equalsIgnoreCase("send"))
					.map(email -> {
						EditEmailDto editEmailDto = new EditEmailDto();
						editEmailDto.setId(email.getAddMailId());
						editEmailDto.setType("Email");
						editEmailDto.setSubject(email.getSubject());
						editEmailDto.setBody(email.getContent());
						editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
						editEmailDto.setCreatedOn(ConvertDateFormatUtil.convertDate(email.getCreatedDate()));
						editEmailDto.setShortName(LeadsCardUtil.shortName(email.getMailFrom()));
						return editEmailDto;
					}).collect(Collectors.toList()));
			timeLine.addAll(visitDaoService.getVisitsByLeadId(leadId).stream()
					.filter(visit -> nonNull(visit.getStatus()) && visit.getStatus().equalsIgnoreCase("complete"))
					.map(visit -> {
						EditVisitDto visitDto = new EditVisitDto();
						visitDto.setId(visit.getVisitId());
						visitDto.setLocation(visit.getLocation());
						visitDto.setSubject(visit.getSubject());
						visitDto.setType("Visit");
						visitDto.setBody(visit.getContent());
						visitDto.setDueDate(nonNull(visit.getDueDate()) ? dateFormat.format(visit.getDueDate()) : null);
						employeeService.getById(visit.getCreatedBy()).ifPresent(byId -> visitDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						visitDto.setCreatedOn(ConvertDateFormatUtil.convertDate(visit.getCreatedDate()));
						return visitDto;
					}).collect(Collectors.toList()));
			timeLine.sort((t1, t2) -> LocalDateTime.parse(t2.getCreatedOn(), formatter)
					.compareTo(LocalDateTime.parse(t1.getCreatedOn(), formatter)));
			List<TimeLineActivityDto> activity = addCallDaoService.getCallsByLeadId(leadId).stream()
					.filter(call -> isNull(call.getStatus()) || call.getStatus().equalsIgnoreCase("save")).map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getAddCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType("Call");
						callDto.setBody(call.getComment());
						callDto.setDueDate(nonNull(call.getDueDate()) ? dateFormat.format(call.getDueDate()) : null);
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDate(call.getCreatedDate()));
						callDto.setShortName(LeadsCardUtil.shortName(call.getCallTo()));
						TO_Employee.apply(call.getCallFrom())
								.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());
			activity.addAll(emailDaoService.getEmailByLeadId(leadId).stream()
					.filter(email -> isNull(email.getStatus()) || email.getStatus().equalsIgnoreCase("save"))
					.map(email -> {
						EditEmailDto editEmailDto = new EditEmailDto();
						editEmailDto.setId(email.getAddMailId());
						editEmailDto.setType("Email");
						editEmailDto.setSubject(email.getSubject());
						editEmailDto.setBody(email.getContent());
						editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
						editEmailDto.setCreatedOn(ConvertDateFormatUtil.convertDate(email.getCreatedDate()));
						editEmailDto.setShortName(LeadsCardUtil.shortName(email.getMailFrom()));
						return editEmailDto;
					}).collect(Collectors.toList()));
			activity.addAll(visitDaoService.getVisitsByLeadId(leadId).stream()
					.filter(visit -> isNull(visit.getStatus()) || visit.getStatus().equalsIgnoreCase("save"))
					.map(visit -> {
						EditVisitDto editVisitDto = new EditVisitDto();
						editVisitDto.setId(visit.getVisitId());
						editVisitDto.setLocation(visit.getLocation());
						editVisitDto.setSubject(visit.getSubject());
						editVisitDto.setType("Visit");
						editVisitDto.setBody(visit.getContent());
						editVisitDto
								.setDueDate(nonNull(visit.getDueDate()) ? dateFormat.format(visit.getDueDate()) : null);
						employeeService.getById(visit.getCreatedBy()).ifPresent(byId -> editVisitDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName())));
						editVisitDto.setCreatedOn(ConvertDateFormatUtil.convertDate(visit.getCreatedDate()));
						return editVisitDto;
					}).collect(Collectors.toList()));
			timeLine.sort((t1, t2) -> LocalDateTime.parse(t2.getCreatedOn(), formatter)
					.compareTo(LocalDateTime.parse(t1.getCreatedOn(), formatter)));
			dataMap.put("Contact", dto);
			dataMap.put("serviceFalls", TO_SERVICEFALLMASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put("leadSource", TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put("Timeline", timeLine);
			dataMap.put("Activity", activity);
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
				lead.get().setStatus("Qualify");
				lead.get().setDisqualifyAs("Qualify");
				lead.get().setServiceFallsMaster(
						serviceFallsDaoSevice.getById(dto.getServiceFallsMaster().getServiceFallsId())
								.orElseThrow(() -> new ResourceNotFoundException("ServiceFallMaster", "serviceFallId",
										dto.getServiceFallsMaster().getServiceFallsId())));
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
				lead.get().setStatus("Close");
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
			serviceFallsDaoSevice.getById(dto.getServiceFallsId()).ifPresent(lead::setServiceFallsMaster);
			leadSourceDaoService.getById(dto.getLeadSourceId()).ifPresent(lead::setLeadSourceMaster);

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
				lead.get().setDisqualifyAs("Open");
				lead.get().setDisqualifyReason(null);
				lead.get().setStatus("Open");
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
}
