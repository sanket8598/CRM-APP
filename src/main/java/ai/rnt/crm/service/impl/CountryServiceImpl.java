package ai.rnt.crm.service.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto_mapper.CountryDtoMapper.TO_COUNTRY;
import static ai.rnt.crm.dto_mapper.CountryDtoMapper.TO_COUNTRY_DTOS;
import static ai.rnt.crm.dto_mapper.CountryDtoMapper.TO_COUNTRY_DTO_DATA;
import static ai.rnt.crm.enums.ApiResponse.DATA;
import static ai.rnt.crm.enums.ApiResponse.MESSAGE;
import static ai.rnt.crm.enums.ApiResponse.SUCCESS;
import static ai.rnt.crm.util.CommonUtil.addCurrencyDetails;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.EnumMap;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.CompanyMasterDaoService;
import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.dao.service.CurrencyDaoService;
import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.dto.CountryDto;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.enums.ApiResponse;
import ai.rnt.crm.exception.CRMException;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.service.CountryService;
import ai.rnt.crm.util.AuditAwareUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CountryServiceImpl implements CountryService {

	private final CountryDaoService countryDaoService;
	private final CurrencyDaoService currencyDaoService;
	private final AuditAwareUtil auditAwareUtil;
	private final CompanyMasterDaoService companyMasterDaoService;
	private final StateDaoService stateDaoService;

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getAllCountry() {
		log.info("inside the getAllCountry method...{}");
		try {
			EnumMap<ApiResponse, Object> allCountry = new EnumMap<>(ApiResponse.class);
			allCountry.put(SUCCESS, true);
			allCountry.put(DATA, TO_COUNTRY_DTOS.apply(countryDaoService.getAllCountry()));
			return new ResponseEntity<>(allCountry, OK);
		} catch (Exception e) {
			log.info("Got Exception while getting the Country..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> addCountry(CountryDto dto) {
		log.info("inside the addCountry method...{}");
		try {
			EnumMap<ApiResponse, Object> addCountry = new EnumMap<>(ApiResponse.class);
			addCountry.put(SUCCESS, false);
			addCountry.put(MESSAGE, "This Country Is Already Present !!");
			if (countryDaoService.isCountryPresent(dto.getCountry().trim()))
				return new ResponseEntity<>(addCountry, OK);
			CountryMaster country = TO_COUNTRY.apply(dto).orElseThrow(ResourceNotFoundException::new);
			if (nonNull(dto.getCurrency()))
				addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
						dto.getCurrency().getCurrencyName(), null).ifPresent(country::setCurrency);
			if (nonNull(countryDaoService.addCountry(country))) {
				addCountry.put(MESSAGE, "Country Added Successfully !!");
				addCountry.put(SUCCESS, true);
			} else {
				addCountry.put(MESSAGE, "Country Not Added !!");
				addCountry.put(SUCCESS, false);
			}
			return new ResponseEntity<>(addCountry, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while adding the Country..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> getCountry(Integer countryId) {
		log.info("inside the getCountry method...{}", countryId);
		try {
			EnumMap<ApiResponse, Object> getCountryData = new EnumMap<>(ApiResponse.class);
			getCountryData.put(DATA, TO_COUNTRY_DTO_DATA.apply(countryDaoService.findCountryById(countryId)
					.orElseThrow(() -> new ResourceNotFoundException("CountryMaster", "countryId", countryId))));
			getCountryData.put(SUCCESS, true);
			return new ResponseEntity<>(getCountryData, OK);
		} catch (Exception e) {
			log.info("Got Exception while get company by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	public ResponseEntity<EnumMap<ApiResponse, Object>> updateCountry(CountryDto dto, Integer countryId) {
		log.info("inside the updatetCountry method...{}", countryId);
		EnumMap<ApiResponse, Object> updateCountryData = new EnumMap<>(ApiResponse.class);
		try {
			updateCountryData.put(SUCCESS, false);
			updateCountryData.put(MESSAGE, "Can't update country it's already present !!");
			if (nonNull(dto) && countryDaoService.isCountryPresent(dto.getCountry(), countryId))
				return new ResponseEntity<>(updateCountryData, OK);
			CountryMaster countryById = countryDaoService.findCountryById(countryId)
					.orElseThrow(() -> new ResourceNotFoundException("CountryMaster", "countryId", countryId));
			if (nonNull(dto.getCountry()))
				countryById.setCountry(dto.getCountry());
			if (nonNull(dto.getCurrency()))
				addCurrencyDetails(currencyDaoService, dto.getCurrency().getCurrencySymbol(),
						dto.getCurrency().getCurrencyName(), dto.getCurrency().getCurrencyId())
						.ifPresent(countryById::setCurrency);
			if (nonNull(countryDaoService.addCountry(countryById))) {
				updateCountryData.put(MESSAGE, "Country Updated Successfully");
				updateCountryData.put(SUCCESS, true);
			} else
				updateCountryData.put(MESSAGE, "Country Not Update.");
			return new ResponseEntity<>(updateCountryData, CREATED);
		} catch (Exception e) {
			log.info("Got Exception while updating the country name by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}

	@Override
	@Transactional
	public ResponseEntity<EnumMap<ApiResponse, Object>> deleteCountry(Integer countryId) {
		log.info("inside the deleteCountry method...{}", countryId);
		try {
			EnumMap<ApiResponse, Object> deleteCountryData = new EnumMap<>(ApiResponse.class);
			deleteCountryData.put(SUCCESS, false);
			Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
			CountryMaster country = countryDaoService.findCountryById(countryId)
					.orElseThrow(() -> new ResourceNotFoundException("CountryMaster", "countryId", countryId));
			if (!companyMasterDaoService.findByCountryId(countryId).isEmpty()
					|| !stateDaoService.findByCountryId(countryId).isEmpty()) {
				deleteCountryData.put(MESSAGE, "This country is in use, You can't delete.");
				deleteCountryData.put(SUCCESS, false);
				return new ResponseEntity<>(deleteCountryData, OK);
			}
			country.setDeletedBy(loggedInStaffId);
			country.setDeletedDate(now().atZone(systemDefault()).withZoneSameInstant(of(INDIA_ZONE)).toLocalDateTime());
			if (nonNull(countryDaoService.addCountry(country))) {
				deleteCountryData.put(MESSAGE, "Country Deleted Successfully");
				deleteCountryData.put(SUCCESS, true);
			} else
				deleteCountryData.put(MESSAGE, "Country Not Delete.");
			return new ResponseEntity<>(deleteCountryData, OK);
		} catch (Exception e) {
			log.info("Got Exception while deleting the country by id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
