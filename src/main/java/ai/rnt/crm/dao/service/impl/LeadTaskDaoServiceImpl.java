package ai.rnt.crm.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.LeadTaskDaoService;
import ai.rnt.crm.entity.LeadTask;
import ai.rnt.crm.repository.LeadTaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class LeadTaskDaoServiceImpl implements LeadTaskDaoService {

	private final LeadTaskRepository leadTaskRepository;

	@Override
	public LeadTask addTask(LeadTask leadTask) {
		return leadTaskRepository.save(leadTask);
	}
}
