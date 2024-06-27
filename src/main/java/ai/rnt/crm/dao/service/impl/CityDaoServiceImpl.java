package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.CITY;
import static ai.rnt.crm.constants.CacheConstant.CITY_ID;
import static ai.rnt.crm.constants.CacheConstant.STATE_ID;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.CityDaoService;
import ai.rnt.crm.entity.CityMaster;
import ai.rnt.crm.repository.CityMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CityDaoServiceImpl implements CityDaoService {

	private final CityMasterRepository cityMasterRepository;

	@Override
	@Cacheable(CITY)
	public List<CityMaster> getAllCity() {
		log.info("inside the getAllCity method...{}");
		return cityMasterRepository.findAll();
	}

	@Override
	@Cacheable(value = CITY, key = "#cityName", condition = "#cityName!=null")
	public Optional<CityMaster> existCityByName(String cityName) {
		log.info("inside the existCityByName method...{}", cityName);
		return cityMasterRepository.findTopByCity(cityName);
	}

	@Override
	@CacheEvict(value = CITY, allEntries = true)
	public CityMaster addCity(CityMaster city) {
		log.info("inside the addCity method...{}");
		return cityMasterRepository.save(city);
	}

	@Override
	@Cacheable(value = CITY, key = STATE_ID, condition = "#stateId!=null")
	public List<CityMaster> findByStateId(Integer stateId) {
		log.info("inside the findByStateId method...{}", stateId);
		return cityMasterRepository.findByStateStateId(stateId);
	}

	@Override
	@Cacheable(value = CITY, key = "#city + '-' + #stateId", condition = "#city!=null && #stateId != null")
	public boolean isCityPresent(String city, Integer stateId) {
		log.info("inside the isCityPresent method...{}{}", city, stateId);
		return cityMasterRepository.existsByCityAndStateStateId(city, stateId);
	}

	@Override
	@Cacheable(value = CITY, key = CITY_ID, condition = "#cityId!=null")
	public Optional<CityMaster> findCityById(Integer cityId) {
		log.info("inside the findCityById method...{}{}", cityId);
		return cityMasterRepository.findById(cityId);
	}
}
