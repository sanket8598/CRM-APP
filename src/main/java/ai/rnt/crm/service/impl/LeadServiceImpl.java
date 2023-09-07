package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD;
import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_LEAD_DTOS;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CompanyMasterService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.EmployeeService;
import ai.rnt.crm.service.LeadService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {

	private final LeadDaoService leadDaoService;
	private final ServiceFallsDaoSevice serviceFallsDaoSevice;
	private final LeadSourceDaoService leadSourceDaoService;
	private final CompanyMasterService companyMasterService;
	private final EmployeeService employeeService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(LeadDto leadDto) {
		EnumMap<ApiResponse, Object> createMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads leads = TO_LEAD.apply(leadDto).orElseThrow(null);
			serviceFallsDaoSevice.getById(leadDto.getServiceFallsId()).ifPresent(leads::setServiceFallsMaster);
			leadSourceDaoService.getById(leadDto.getLeadSourceId()).ifPresent(leads::setLeadSourceMaster);
			companyMasterService.getById(leadDto.getCompanyId()).ifPresent(leads::setCompanyMaster);
			employeeService.getById(leadDto.getAssignTo()).ifPresent(leads::setEmployee);
			if (nonNull(leadDaoService.addLead(leads)))
				createMap.put(MESSAGE, "Lead Added Successfully");
			else
				createMap.put(MESSAGE, "Lead Not Added");
			return new ResponseEntity<>(createMap, HttpStatus.CREATED);
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
				dataMap.put("allLead", TO_LEAD_DTOS.apply(allLeads));
				dataMap.put("openLead",
						TO_LEAD_DTOS.apply(allLeads.stream().filter(l -> nonNull(l.getStatus())
								&& (l.getStatus().equalsIgnoreCase("new") || l.getStatus().equalsIgnoreCase("open")))
								.collect(Collectors.toList())));
				dataMap.put("closeLead", TO_LEAD_DTOS.apply(allLeads.stream().filter(l -> nonNull(l.getStatus())
						&& (l.getStatus().equalsIgnoreCase("close") || l.getStatus().equalsIgnoreCase("disqualify")))
						.collect(Collectors.toList())));
				getAllLeads.put(DATA, dataMap);
			} else
				getAllLeads.put(DATA, TO_LEAD_DTOS.apply(leadDaoService.getLeadsByStatus(leadsStatus)));
			return new ResponseEntity<>(getAllLeads, HttpStatus.FOUND);
		} catch (Exception e) {
			throw new CRMException(e);
		}
	}

}
