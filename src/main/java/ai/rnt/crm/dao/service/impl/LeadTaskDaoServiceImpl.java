package ai.rnt.crm.dao.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	@Override
	public Optional<LeadTask> getTaskById(Integer taskId) {
		return leadTaskRepository.findById(taskId);
	}

	@Override
	public List<LeadTask> getAllTask() {
		return leadTaskRepository.findAll();
	}

	@Override
	public List<LeadTask> getTodaysLeadTask(Date todayAsDate, String time) {
		return leadTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true);
	}
}
