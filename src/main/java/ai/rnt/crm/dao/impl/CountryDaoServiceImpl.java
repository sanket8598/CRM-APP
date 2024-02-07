package ai.rnt.crm.dao.impl;

import static ai.rnt.crm.constants.CacheConstant.COUNTRY;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.CountryDaoService;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.repository.CountryMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryDaoServiceImpl implements CountryDaoService {

	private final CountryMasterRepository countryMasterRepository;

	@Override
	@Cacheable(COUNTRY)
	public List<CountryMaster> getAllCountry() {
		return countryMasterRepository.findAll();
	}

	@Override
	@Cacheable(COUNTRY)
	public Optional<CountryMaster> findByCountryName(String countryName) {
		return countryMasterRepository.findTopByCountry(countryName);
	}

	@Override
	@CacheEvict(value=COUNTRY,allEntries = true)
	public CountryMaster addCountry(CountryMaster country) {
		return countryMasterRepository.save(country);	
	}

}
