package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.COUNTRY_ID;
import static ai.rnt.crm.constants.CacheConstant.STATES;
import static ai.rnt.crm.constants.CacheConstant.STATE_ID;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.repository.StateMasterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StateDaoServiceImpl implements StateDaoService {

	private final StateMasterRepository stateMasterRepository;

	@Override
	@Cacheable(STATES)
	public List<StateMaster> getAllState() {
		log.info("inside the getAllState method...{}");
		return stateMasterRepository.findByOrderByStateAsc();
	}

	@Override
	@Cacheable(STATES)
	public Optional<StateMaster> findBystate(String state) {
		log.info("inside the findBystate method...{}", state);
		return stateMasterRepository.findTopByState(state);
	}

	@Override
	@CacheEvict(value = STATES, allEntries = true)
	public StateMaster addState(StateMaster state) {
		log.info("inside the addState method...{}");
		return stateMasterRepository.save(state);
	}

	@Override
	@Cacheable(value = STATES, key = COUNTRY_ID, condition = "#countryId!=null")
	public List<StateMaster> findByCountryId(Integer countryId) {
		log.info("inside the state master dao findByCountryId method...{}", countryId);
		return stateMasterRepository.findByCountryCountryId(countryId);
	}

	@Override
	@Cacheable(value = STATES, key = "#state + '-' + #countryId", condition = "#state!=null && #countryId != null")
	public boolean isStatePresent(String state, Integer countryId) {
		log.info("inside the state master dao isStatePresent method...{}{}", state, countryId);
		return stateMasterRepository.existsByStateAndCountryCountryId(state, countryId);
	}

	@Override
	@Cacheable(value = STATES, key = STATE_ID, condition = "#stateId!=null")
	public Optional<StateMaster> findStateById(Integer stateId) {
		log.info("inside the state master dao findStateById method...{}{}", stateId);
		return stateMasterRepository.findById(stateId);
	}
}
