package ai.rnt.crm.dao.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.repository.CallRepository;
import ai.rnt.crm.repository.CallTaskRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class CallDaoServiceImpl implements CallDaoService {

	private final CallRepository callRepository;
	private final CallTaskRepository callTaskRepository;

	@Override
	public Call call(Call call) {
		return callRepository.save(call);
	}

	@Override
	public List<Call> getCallsByLeadId(Integer leadId) {
		return callRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<Call> getCallById(Integer callId) {
		return callRepository.findById(callId);
	}

	@Override
	public PhoneCallTask addCallTask(PhoneCallTask phoneCallTask) {
		return callTaskRepository.save(phoneCallTask);
	}

	@Override
	public Optional<PhoneCallTask> getCallTaskById(Integer taskId) {
		return callTaskRepository.findById(taskId);
	}

	@Override
	public List<PhoneCallTask> getAllTask() {
		return callTaskRepository.findAll();
	}

	@Override
	public List<PhoneCallTask> getTodaysCallTask(LocalDate todayDate, String time) {
		return callTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, time, true);
	}
}
