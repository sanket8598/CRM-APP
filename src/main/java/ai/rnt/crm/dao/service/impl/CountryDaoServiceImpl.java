package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.CountryDaoService;
import ai.rnt.crm.entity.CountryMaster;
import ai.rnt.crm.repository.CountryMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CountryDaoServiceImpl implements CountryDaoService {

	private final CountryMasterRepository countryMasterRepository;

	@Override
	public List<CountryMaster> getAllCountry() {
		return countryMasterRepository.findAll();
	}

	@Override
	public Optional<CountryMaster> findByCountryName(String countryName) {
		return countryMasterRepository.findByCountry(countryName);
	}

}
