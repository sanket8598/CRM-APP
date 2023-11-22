package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY_DTO;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.repository.CompanyMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyMasterServiceImpl implements CompanyMasterDaoService{
	
	private final CompanyMasterRepository companyMasterRepository;
	
	@Override
	public Optional<CompanyDto> getById(Integer companyId) {
		return TO_COMPANY_DTO.apply(companyMasterRepository.findById(companyId).orElseThrow(null));
	}

	@Override
	public Optional<CompanyDto> save(CompanyMaster companyMaster) {
		return TO_COMPANY_DTO.apply(companyMasterRepository.save(companyMaster));
	}

	@Override
	public Optional<CompanyDto> findByCompanyName(String companyName) {
		return TO_COMPANY_DTO.apply(companyMasterRepository.findTopByCompanyName(companyName));
	}

}
