package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.CALLS;
import static ai.rnt.crm.constants.CacheConstant.CALLS_BY_LEAD_ID;
import static ai.rnt.crm.constants.CacheConstant.CALL_TASK;
import static ai.rnt.crm.constants.CacheConstant.LEAD_ID;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.CallDaoService;
import ai.rnt.crm.entity.Call;
import ai.rnt.crm.entity.PhoneCallTask;
import ai.rnt.crm.repository.CallRepository;
import ai.rnt.crm.repository.CallTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CallDaoServiceImpl implements CallDaoService {

	private final CallRepository callRepository;
	private final CallTaskRepository callTaskRepository;

	@Override
	@CacheEvict(value = { CALLS, CALLS_BY_LEAD_ID }, allEntries = true)
	public Call call(Call call) {
		log.info("inside the addcall method...");
		return callRepository.save(call);
	}

	@Override
	@Cacheable(value = CALLS_BY_LEAD_ID, key = LEAD_ID, condition = "#leadId!=null")
	public List<Call> getCallsByLeadId(Integer leadId) {
		log.info("inside the getCallsByLeadId method...{}", leadId);
		return callRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<Call> getCallById(Integer callId) {
		log.info("inside the getCallById method...{}", callId);
		return callRepository.findById(callId);
	}

	@Override
	@CacheEvict(value = CALL_TASK, allEntries = true)
	public PhoneCallTask addCallTask(PhoneCallTask phoneCallTask) {
		log.info("inside the addCallTask method...");
		return callTaskRepository.save(phoneCallTask);
	}

	@Override
	public Optional<PhoneCallTask> getCallTaskById(Integer taskId) {
		log.info("inside the getCallTaskById method...{}", taskId);
		return callTaskRepository.findById(taskId);
	}

	@Override
	@Cacheable(value = CALL_TASK)
	public List<PhoneCallTask> getAllTask() {
		log.info("inside the getAllTask method...");
		return callTaskRepository.findAll();
	}

	@Override
	public List<PhoneCallTask> getTodaysCallTask(LocalDate todayDate, String time) {
		log.info("inside the getTodaysCallTask method...{} {}", todayDate, time);
		return callTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayDate, time, true);
	}

	@Override
	public List<Call> getCallsByLeadIdAndIsOpportunity(Integer leadId) {
		log.info("inside the getCallsByLeadIdAndIsOpportunity method...{}", leadId);
		return callRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false);
	}

	@Override
	@Cacheable(value = CALLS, key = "#isOpportunity", condition = "#isOpportunity!=null")
	public List<Call> getAllLeadCalls(boolean isOpportunity) {
		log.info("inside the getAllLeadCalls method...{}", isOpportunity);
		return callRepository.findByIsOpportunityOrderByCreatedDateDesc(isOpportunity);
	}
}
