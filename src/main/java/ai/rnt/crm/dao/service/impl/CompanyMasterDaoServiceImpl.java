package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY_DTO;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY_DTOS;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.repository.CompanyMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyMasterDaoServiceImpl implements CompanyMasterDaoService {

	private final CompanyMasterRepository companyMasterRepository;

	@Override
	public Optional<CompanyDto> getById(Integer companyId) {
		log.info("inside the CompanyMasterDaoServiceImpl getById method...{}", companyId);
		return TO_COMPANY_DTO.apply(companyMasterRepository.findById(companyId).orElseThrow(null));
	}

	@Override
	public Optional<CompanyDto> save(CompanyMaster companyMaster) {
		log.info("inside the CompanyMasterDaoServiceImpl save method...{}");
		return TO_COMPANY_DTO.apply(companyMasterRepository.save(companyMaster));
	}

	@Override
	public Optional<CompanyDto> findByCompanyName(String companyName) {
		log.info("inside the findByCompanyName method...{}", companyName);
		return TO_COMPANY_DTO.apply(companyMasterRepository.findTopByCompanyName(companyName));
	}

	@Override
	public List<CompanyDto> findAllCompanies() {
		log.info("inside the findAllCompanies method...");
		return TO_COMPANY_DTOS.apply(companyMasterRepository.findAll());
	}

	@Override
	public Optional<CompanyMaster> findCompanyById(Integer companyId) {
		log.info("inside the findCompanyById method...{}", companyId);
		return companyMasterRepository.findById(companyId);
	}

	@Override
	public List<CompanyMaster> findByCountryId(Integer countryId) {
		log.info("inside the company master dao findByCountryId method...{}", countryId);
		return companyMasterRepository.findByCountryCountryId(countryId);
	}

	@Override
	public List<CompanyMaster> findByStateId(Integer stateId) {
		log.info("inside the company master dao findByStateId method...{}", stateId);
		return companyMasterRepository.findByStateStateId(stateId);
	}

	@Override
	public List<CompanyMaster> findByCityId(Integer cityId) {
		log.info("inside the company master dao findByCityId method...{}", cityId);
		return companyMasterRepository.findByCityCityId(cityId);
	}

	@Override
	public boolean isCompanyPresent(String company, Integer countryId, Integer stateId) {
		log.info("inside the company master dao isCompanyPresent method...{}{}{}", company, countryId, stateId);
		return companyMasterRepository.existsByCompanyNameAndCountryCountryIdAndStateStateId(company, countryId,
				stateId);
	}
}
