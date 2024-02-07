package ai.rnt.crm.dao.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.LeadTaskDaoService;
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
	public List<LeadTask> getTodaysLeadTask(LocalDate todayAsDate, String time) {
		return leadTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true);
	}
}
