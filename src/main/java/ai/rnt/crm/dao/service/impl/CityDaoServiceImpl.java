package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.CITY;

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

@Service
@RequiredArgsConstructor
@Transactional
public class CityDaoServiceImpl implements CityDaoService {

	private final CityMasterRepository cityMasterRepository;

	@Override
	@Cacheable(CITY)
	public List<CityMaster> getAllCity() {
		return cityMasterRepository.findAll();
	}

	@Override
	@Cacheable(CITY)
	public Optional<CityMaster> existCityByName(String cityName) {
		return cityMasterRepository.findTopByCity(cityName);
	}

	@Override
	@CacheEvict(value=CITY,allEntries = true)
	public CityMaster addCity(CityMaster city) {
		return cityMasterRepository.save(city);
	}
}
