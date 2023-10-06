package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

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
	public List<CityMaster> getAllCity() {
		return cityMasterRepository.findAll();
	}

	@Override
	public Optional<CityMaster> existCityByName(String cityName) {
		return cityMasterRepository.findTopByCity(cityName);
	}
}
