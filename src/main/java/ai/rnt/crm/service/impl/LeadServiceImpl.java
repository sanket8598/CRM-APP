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
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import ai.rnt.crm.dto.EditVisitDto;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.dto.QualifyLeadDto;
import ai.rnt.crm.dto.TimeLineActivityDto;
import ai.rnt.crm.dto.UpdateLeadDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.EmployeeMaster;
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
			if (existCompany.isPresent())
				leads.setCompanyMaster(TO_COMPANY.apply(existCompany.orElseThrow(null)).orElseThrow(null));
			else
				leads.setCompanyMaster(TO_COMPANY.apply(companyMasterDaoService
						.save(TO_COMPANY.apply(CompanyDto.builder().companyName(leadDto.getCompanyName())
								.companyWebsite(leadDto.getCompanyWebsite()).build()).orElseThrow(null))
						.orElseThrow(null)).orElseThrow(null));
			leads.setStatus("Open");
			serviceFallsDaoSevice.getById(leadDto.getServiceFallsId()).ifPresent(leads::setServiceFallsMaster);
			leadSourceDaoService.getById(leadDto.getLeadSourceId()).ifPresent(leads::setLeadSourceMaster);
			employeeService.getById(leadDto.getAssignTo()).ifPresent(leads::setEmployee);
			if (nonNull(leadDaoService.addLead(leads)))
				createMap.put(MESSAGE, "Lead Added Successfully");
			else
				createMap.put(MESSAGE, "Lead Not Added");
			createMap.put(SUCCESS, true);
			return new ResponseEntity<>(createMap, CREATED);
		} catch (Exception e) {
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
				List<Leads> allLeads = leadDaoService.getAllLeads();
				dataMap.put("allLead", TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads));
				dataMap.put("openLead",
						TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads.stream().filter(l -> nonNull(l.getStatus())
								&& (l.getStatus().equalsIgnoreCase("new") || l.getStatus().equalsIgnoreCase("open")))
								.collect(Collectors.toList())));
				dataMap.put("closeLead",
						TO_DASHBOARD_CARDS_LEADDTOS.apply(allLeads.stream()
								.filter(l -> nonNull(l.getStatus()) && (l.getStatus().equalsIgnoreCase("close")
										|| l.getStatus().equalsIgnoreCase("disqualify")))
								.collect(Collectors.toList())));
				getAllLeads.put(DATA, dataMap);
			} else
				getAllLeads.put(DATA, TO_LEAD_DTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus)));
			return new ResponseEntity<>(getAllLeads, FOUND);
		} catch (Exception e) {
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
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> editLead(Integer leadId) {
		EnumMap<ApiResponse, Object> lead = new EnumMap<>(ApiResponse.class);
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			Map<String, Object> dataMap = new LinkedHashMap<>();
			List<TimeLineActivityDto> timeLine = new ArrayList<>();
			List<EditCallDto> list = addCallDaoService.getCallsByLeadId(leadId).stream()
					.filter(call -> nonNull(call.getUpdatedBy()) || nonNull(call.getUpdatedDate())).map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getAddCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType("Call");
						callDto.setBody(call.getComment());
						callDto.setDueDate(dateFormat.format(call.getDueDate()));
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDate(call.getUpdatedDate()));
						callDto.setShortName(LeadsCardUtil.shortName(call.getCallTo()));
						TO_Employee.apply(call.getCallFrom())
								.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());
			timeLine.addAll(list);
			timeLine.addAll(emailDaoService.getEmailByLeadId(leadId).stream()
					.filter(email -> nonNull(email.getUpdatedBy()) || nonNull(email.getUpdatedDate())).map(email -> {
						EditEmailDto editEmailDto = new EditEmailDto();
						editEmailDto.setId(email.getAddMailId());
						editEmailDto.setType("Email");
						editEmailDto.setSubject(email.getSubject());
						editEmailDto.setBody(email.getContent());
						editEmailDto.setAttachments(TO_ATTACHMENT_DTOS.apply(email.getAttachment()));
						editEmailDto.setCreatedOn(ConvertDateFormatUtil.convertDate(email.getUpdatedDate()));
						editEmailDto.setShortName(LeadsCardUtil.shortName(email.getMailFrom()));
						return editEmailDto;
					}).collect(Collectors.toList()));
			timeLine.addAll(visitDaoService.getVisitsByLeadId(leadId).stream()
					.filter(visit -> nonNull(visit.getUpdatedBy()) || nonNull(visit.getUpdatedDate())).map(visit -> {
						EditVisitDto visitDto = new EditVisitDto();
						visitDto.setId(visit.getVisitId());
						visitDto.setLocation(visit.getLocation());
						visitDto.setSubject(visit.getSubject());
						visitDto.setType("Visit");
						visitDto.setBody(visit.getContent());
						visitDto.setDueDate(dateFormat.format(visit.getDueDate()));
						EmployeeMaster byId = employeeService.getById(visit.getCreatedBy()).orElseThrow(null);
						visitDto.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName()));
						return visitDto;
					}).collect(Collectors.toList()));
			List<TimeLineActivityDto> activity = addCallDaoService.getCallsByLeadId(leadId).stream()
					.filter(call -> nonNull(call.getCreatedBy()) && isNull(call.getUpdatedBy())).map(call -> {
						EditCallDto callDto = new EditCallDto();
						callDto.setId(call.getAddCallId());
						callDto.setSubject(call.getSubject());
						callDto.setType("Call");
						callDto.setBody(call.getComment());
						callDto.setDueDate(dateFormat.format(call.getDueDate()));
						callDto.setCreatedOn(ConvertDateFormatUtil.convertDate(call.getCreatedDate()));
						callDto.setShortName(LeadsCardUtil.shortName(call.getCallTo()));
						TO_Employee.apply(call.getCallFrom())
								.ifPresent(e -> callDto.setCallFrom(e.getFirstName() + " " + e.getLastName()));
						return callDto;
					}).collect(Collectors.toList());
			activity.addAll(emailDaoService.getEmailByLeadId(leadId).stream()
					.filter(email -> nonNull(email.getCreatedBy()) && isNull(email.getUpdatedBy())).map(email -> {
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
					.filter(visit -> nonNull(visit.getCreatedBy()) && isNull(visit.getUpdatedBy())).map(visit -> {
						EditVisitDto editVisitDto = new EditVisitDto();
						editVisitDto.setId(visit.getVisitId());
						editVisitDto.setLocation(visit.getLocation());
						editVisitDto.setSubject(visit.getSubject());
						editVisitDto.setType("Visit");
						editVisitDto.setBody(visit.getContent());
						editVisitDto.setDueDate(dateFormat.format(visit.getDueDate()));
						EmployeeMaster byId = employeeService.getById(visit.getCreatedBy()).orElseThrow(null);
						editVisitDto
								.setShortName(LeadsCardUtil.shortName(byId.getFirstName() + " " + byId.getLastName()));
						return editVisitDto;
					}).collect(Collectors.toList()));
			dataMap.put("Contact", TO_EDITLEAD_DTO.apply(leadDaoService.getLeadById(leadId)
					.orElseThrow(() -> new ResourceNotFoundException("Lead", "leadId", leadId))));
			dataMap.put("serviceFalls", TO_SERVICEFALLMASTER_DTOS.apply(serviceFallsDaoSevice.getAllSerciveFalls()));
			dataMap.put("leadSource", TO_LEAD_SOURCE_DTOS.apply(leadSourceDaoService.getAllLeadSource()));
			dataMap.put("Timeline", timeLine);
			dataMap.put("Activity", activity);
			lead.put(SUCCESS, true);
			lead.put(DATA, dataMap);
			return new ResponseEntity<>(lead, FOUND);
		} catch (Exception e) {
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
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateLeadContact(Integer leadId, UpdateLeadDto dto) {
		EnumMap<ApiResponse, Object> result = new EnumMap<>(ApiResponse.class);
		try {
			Leads lead = leadDaoService.getLeadById(leadId).orElseThrow(null);
			lead.setTopic(dto.getTopic());
			lead.setFirstName(dto.getFirstName());
			lead.setLastName(dto.getLastName());
			lead.setPhoneNumber(dto.getPhoneNumber());
			lead.setEmail(dto.getEmail());
			lead.setBudgetAmount(dto.getBudgetAmount());
			Optional<CityMaster> existCityByName = cityDaoService.existCityByName(dto.getCity());
			Optional<StateMaster> findBystate = stateDaoService.findBystate(dto.getState());
			Optional<CountryMaster> findByCountryName = countryDaoService.findByCountryName(dto.getCountry());
			Optional<CompanyDto> existCompany = companyMasterDaoService.findByCompanyName(dto.getCompanyName());
			if (existCompany.isPresent()) {
				CompanyMaster companyMaster = TO_COMPANY.apply(existCompany.orElseThrow(null)).orElseThrow(null);
				existCityByName.ifPresent(companyMaster::setCity);
				findByCountryName.ifPresent(companyMaster::setCountry);
				findBystate.ifPresent(companyMaster::setState);
				companyMaster.setZipCode(dto.getZipCode());
				companyMaster.setAddressLineOne(dto.getAddressLineOne());
				TO_COMPANY.apply(companyMasterDaoService.save(companyMaster).orElseThrow(null))
						.ifPresent(lead::setCompanyMaster);
			} else {
				CompanyMaster companyMaster = TO_COMPANY.apply(CompanyDto.builder().companyName(dto.getCompanyName())
						.companyWebsite(dto.getCompanyWebsite()).build()).orElseThrow(null);
				existCityByName.ifPresent(companyMaster::setCity);
				findByCountryName.ifPresent(companyMaster::setCountry);
				findBystate.ifPresent(companyMaster::setState);
				companyMaster.setZipCode(dto.getZipCode());
				companyMaster.setAddressLineOne(dto.getAddressLineOne());
				TO_COMPANY.apply(companyMasterDaoService.save(companyMaster).orElseThrow(null))
						.ifPresent(lead::setCompanyMaster);
			}
			serviceFallsDaoSevice.getById(dto.getServiceFallsId()).ifPresent(lead::setServiceFallsMaster);
			leadSourceDaoService.getById(dto.getLeadSourceId()).ifPresent(lead::setLeadSourceMaster);

			if (nonNull(leadDaoService.addLead(lead)))
				result.put(MESSAGE, "Leads Contact Updated Successfully");
			else
				result.put(MESSAGE, "Leads Contact Not Updated.");
			result.put(SUCCESS, true);
			return new ResponseEntity<>(result, CREATED);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}
}
