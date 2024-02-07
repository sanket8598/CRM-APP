package ai.rnt.crm.dao.impl;

import static ai.rnt.crm.constants.CacheConstant.STATES;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.StateDaoService;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.repository.StateMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StateDaoServiceImpl implements StateDaoService {

	private final StateMasterRepository stateMasterRepository;

	@Override
	@Cacheable(STATES)
	public List<StateMaster> getAllState() {
		return stateMasterRepository.findAll();
	}

	@Override
	@Cacheable(STATES)
	public Optional<StateMaster> findBystate(String state) {
		return stateMasterRepository.findTopByState(state);
	}

	@Override
	@CacheEvict(value=STATES,allEntries=true)
	public StateMaster addState(StateMaster state) {
		return stateMasterRepository.save(state);
	}
}
