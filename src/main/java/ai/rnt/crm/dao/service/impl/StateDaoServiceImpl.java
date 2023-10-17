package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.StateDaoService;
import ai.rnt.crm.entity.StateMaster;
import ai.rnt.crm.repository.StateMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StateDaoServiceImpl implements StateDaoService {

	private final StateMasterRepository stateMasterRepository;

	@Override
	public List<StateMaster> getAllState() {
		return stateMasterRepository.findAll();
	}

	@Override
	public Optional<StateMaster> findBystate(String state) {
		return stateMasterRepository.findTopByState(state);
	}

	@Override
	public StateMaster addState(StateMaster state) {
		return stateMasterRepository.save(state);
	}
}
