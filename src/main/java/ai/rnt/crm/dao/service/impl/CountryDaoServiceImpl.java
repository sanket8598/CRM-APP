package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.COUNTRY;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.repository.CountryMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CountryDaoServiceImpl implements CountryDaoService {

	private final CountryMasterRepository countryMasterRepository;

	@Override
	@Cacheable(COUNTRY)
	public List<CountryMaster> getAllCountry() {
		log.info("inside the getAllCountry method...{}");
		return countryMasterRepository.findAll();
	}

	@Override
	@Cacheable(COUNTRY)
	public Optional<CountryMaster> findByCountryName(String countryName) {
		log.info("inside the findByCountryName method...{}", countryName);
		return countryMasterRepository.findTopByCountry(countryName);
	}

	@Override
	@CacheEvict(value = COUNTRY, allEntries = true)
	public CountryMaster addCountry(CountryMaster country) {
		log.info("inside the addCountry method...{}");
		return countryMasterRepository.save(country);
	}

	@Override
	public Optional<CountryMaster> findCountryById(Integer countryId) {
		log.info("inside the findCountryById method...{}", countryId);
		return countryMasterRepository.findById(countryId);
	}

	@Override
	public boolean isCountryPresent(String country) {
		log.info("inside the isCountryPresent method...{}", country);
		return countryMasterRepository.existsByCountry(country);
	}
}
