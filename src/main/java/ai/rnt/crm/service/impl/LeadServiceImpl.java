package ai.rnt.crm.service.impl;

import static ai.rnt.crm.dto_mapper.LeadsDtoMapper.TO_NEWLEAD;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ai.rnt.crm.dao.service.CompanyMasterService;
import ai.rnt.crm.dao.service.LeadDaoService;
import ai.rnt.crm.dao.service.LeadSourceDaoService;
import ai.rnt.crm.dao.service.ServiceFallsDaoSevice;
import ai.rnt.crm.dto.NewLeadDto;
import ai.rnt.crm.entity.Leads;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.service.LeadService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {

	private final LeadDaoService leadDaoService;
	private final ServiceFallsDaoSevice serviceFallsDaoSevice;
	private final LeadSourceDaoService leadSourceDaoService;
	private final CompanyMasterService companyMasterService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> createLead(NewLeadDto leadDto, MultipartFile file) {
		EnumMap<ApiResponse, Object> createMap = new EnumMap<>(ApiResponse.class);
		try {
			Leads leads = TO_NEWLEAD.apply(leadDto).orElseThrow(null);
			if (nonNull(file) && !file.isEmpty())
				leads.setBusinessCard(Base64.getEncoder().encodeToString(file.getBytes()));
			serviceFallsDaoSevice.getById(leadDto.getServiceFallsId()).ifPresent(leads::setServiceFallsMaster);
			leadSourceDaoService.getById(leadDto.getLeadSourceId()).ifPresent(leads::setLeadSourceMaster);
			companyMasterService.getById(leadDto.getCompanyId()).ifPresent(leads::setCompanyMaster);
			if (nonNull(leadDaoService.addLead(leads)))
				createMap.put(MESSAGE, "Lead Added Successfully");
			else
				createMap.put(MESSAGE, "Lead Not Added");
			return new ResponseEntity<>(createMap, HttpStatus.CREATED);
		} catch (IOException e) {
			throw new CRMException(e);
		}
	}

}
