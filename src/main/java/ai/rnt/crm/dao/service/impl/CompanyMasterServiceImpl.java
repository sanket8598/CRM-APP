package ai.rnt.crm.dao.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CompanyMasterService;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.repository.CompanyMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyMasterServiceImpl implements CompanyMasterService{
	
	private final CompanyMasterRepository companyMasterRepository;
	
	@Override
	public Optional<CompanyMaster> getById(Integer companyId) {
		return companyMasterRepository.findById(companyId);
	}

}
