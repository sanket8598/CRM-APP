package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY;
import static ai.rnt.crm.dto_mapper.CompanyDtoMapper.TO_COMPANY_DTO;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.ContactDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CompanyDto;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.entity.CompanyMaster;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.CompanyService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyServiceImpl implements CompanyService {

	private final CompanyMasterDaoService companyMasterDaoService;
	private final CountryDaoService countryDaoService;
	private final StateDaoService stateDaoService;
	private final CityDaoService cityDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final ContactDaoService contactDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> companyList() {
		log.info("inside the companyList method...");
		EnumMap<ApiResponse, Object> companyList = new EnumMap<>(ApiResponse.class);
		try {
			companyList.put(DATA, companyMasterDaoService.findAllCompanies());
			companyList.put(SUCCESS, true);
			return new ResponseEntity<>(companyList, OK);
		} catch (Exception e) {
			companyList.put(SUCCESS, false);
			log.error("error occured while getting company list..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCompany(Integer companyId) {
		log.info("inside the getCompany method...{}", companyId);
		EnumMap<ApiResponse, Object> company = new EnumMap<>(ApiResponse.class);
		try {
			company.put(DATA, TO_COMPANY_DTO.apply(companyMasterDaoService.findCompanyById(companyId)
					.orElseThrow(() -> new ResourceNotFoundException("CompanyMaster", "companyId", companyId))));
			company.put(SUCCESS, true);
			return new ResponseEntity<>(company, OK);
		} catch (Exception e) {
			company.put(SUCCESS, false);
			log.error("error occured while getting company by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCompany(CompanyDto dto) {
		log.info("inside the addCompany method...{}");
		EnumMap<ApiResponse, Object> addCompanyData = new EnumMap<>(ApiResponse.class);
		try {
			if (companyMasterDaoService.isCompanyPresent(dto.getCompanyName().trim(), dto.getCountry().getCountryId(),
					dto.getState().getStateId())) {
				addCompanyData.put(SUCCESS, false);
				addCompanyData.put(MESSAGE, "This Company Is Already Present !!");
				return new ResponseEntity<>(addCompanyData, OK);
			}
			CompanyMaster companyMaster = TO_COMPANY.apply(dto).orElseThrow(ResourceNotFoundException::new);
			if (nonNull(companyMasterDaoService.save(companyMaster))) {
				addCompanyData.put(MESSAGE, "Company Added Successfully");
				addCompanyData.put(SUCCESS, true);
			} else {
				addCompanyData.put(MESSAGE, "Company Not Added");
				addCompanyData.put(SUCCESS, false);
			}
			return new ResponseEntity<>(addCompanyData, CREATED);
		} catch (Exception e) {
			log.error("error occured while adding the company..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCompany(CompanyDto dto, Integer companyId) {
		log.info("inside the updateCompany method...{}", companyId);
		EnumMap<ApiResponse, Object> updateCompanyData = new EnumMap<>(ApiResponse.class);
		updateCompanyData.put(SUCCESS, false);
		try {
			CompanyMaster companyData = companyMasterDaoService.findCompanyById(companyId)
					.orElseThrow(() -> new ResourceNotFoundException("CompanyMaster", "companyId", companyId));
			CountryMaster countryMaster = countryDaoService.findCountryById(dto.getCountry().getCountryId())
					.orElseThrow(() -> new ResourceNotFoundException("CountryMaster", "countryId",
							dto.getCountry().getCountryId()));
			StateMaster stateMaster = stateDaoService.findStateById(dto.getState().getStateId()).orElseThrow(
					() -> new ResourceNotFoundException("StateMaster", "stateId", dto.getState().getStateId()));
			CityMaster cityMaster = cityDaoService.findCityById(dto.getCity().getCityId()).orElseThrow(
					() -> new ResourceNotFoundException("CityMaster", "cityId", dto.getCity().getCityId()));
			companyData.setCompanyName(dto.getCompanyName());
			companyData.setCompanyWebsite(dto.getCompanyWebsite());
			companyData.setAddressLineOne(dto.getAddressLineOne());
			companyData.setZipCode(dto.getZipCode());
			companyData.setCountry(countryMaster);
			companyData.setState(stateMaster);
			companyData.setCity(cityMaster);
			if (nonNull(companyMasterDaoService.save(companyData))) {
				updateCompanyData.put(MESSAGE, "Company Updated Successfully");
				updateCompanyData.put(SUCCESS, true);
			} else
				updateCompanyData.put(MESSAGE, "Company Not Update.");
			return new ResponseEntity<>(updateCompanyData, CREATED);
		} catch (Exception e) {
			log.error("error occured while updating the company by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCompany(Integer companyId) {
		log.info("inside the deleteCompany method...{}", companyId);
		EnumMap<ApiResponse, Object> deleteCompanyData = new EnumMap<>(ApiResponse.class);
		deleteCompanyData.put(SUCCESS, false);
		try {
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			CompanyMaster company = companyMasterDaoService.findCompanyById(companyId)
					.orElseThrow(() -> new ResourceNotFoundException("CompanyMaster", "companyId", companyId));
			if (!contactDaoService.findByCompanyId(companyId).isEmpty()) {
				deleteCompanyData.put(MESSAGE, "You Can't Delete This Company Is In Use!!");
				deleteCompanyData.put(SUCCESS, false);
				return new ResponseEntity<>(deleteCompanyData, OK);
			}
			company.setDeletedBy(loggedInStaffId);
			company.setDeletedDate(now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(companyMasterDaoService.save(company))) {
				deleteCompanyData.put(MESSAGE, "Company Deleted Successfully");
				deleteCompanyData.put(SUCCESS, true);
			} else
				deleteCompanyData.put(MESSAGE, "Company Not Delete.");
			return new ResponseEntity<>(deleteCompanyData, OK);
		} catch (Exception e) {
			log.error("error occured while deleting the company by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
