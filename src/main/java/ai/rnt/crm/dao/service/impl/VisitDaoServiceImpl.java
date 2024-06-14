package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.LEAD_ID;
import static ai.rnt.crm.constants.CacheConstant.VISITS;
import static ai.rnt.crm.constants.CacheConstant.VISITS_BY_LEAD_ID;
import static ai.rnt.crm.constants.CacheConstant.VISIT_TASK;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.repository.VisitRepository;
import ai.rnt.crm.repository.VisitTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 14-09-2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class VisitDaoServiceImpl implements VisitDaoService {

	private final VisitRepository visitRepository;
	private final VisitTaskRepository visitTaskRepository;

	@Override
	@CacheEvict(value = { VISITS, VISITS_BY_LEAD_ID }, allEntries = true)
	public Visit saveVisit(Visit visit) {
		log.info("inside the saveVisit method...");
		return visitRepository.save(visit);
	}

	@Override
	@Cacheable(value = VISITS_BY_LEAD_ID, key = LEAD_ID, condition = "#leadId!=null")
	public List<Visit> getVisitsByLeadId(Integer leadId) {
		log.info("inside the getVisitsByLeadId method...{}", leadId);
		return visitRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<Visit> getVisitsByVisitId(Integer visitId) {
		log.info("inside the getVisitsByVisitId method...{}", visitId);
		return visitRepository.findById(visitId);
	}

	@Override
	@CacheEvict(value = VISIT_TASK, allEntries = true)
	public VisitTask addVisitTask(VisitTask visitTask) {
		log.info("inside the addVisitTask method...");
		return visitTaskRepository.save(visitTask);
	}

	@Override
	public Optional<VisitTask> getVisitTaskById(Integer taskId) {
		log.info("inside the getVisitTaskById method...{}", taskId);
		return visitTaskRepository.findById(taskId);
	}

	@Override
	@Cacheable(value = VISIT_TASK)
	public List<VisitTask> getAllTask() {
		log.info("inside the getAllTask method...");
		return visitTaskRepository.findAll();
	}

	@Override
	public List<VisitTask> getTodaysAllVisitTask(LocalDate todayAsDate, String time) {
		log.info("inside the getTodaysAllVisitTask method...{} {}", todayAsDate, time);
		return visitTaskRepository.findByRemainderDueOnAndRemainderDueAtAndRemainderOn(todayAsDate, time, true);
	}

	@Override
	public List<Visit> getVisitsByLeadIdAndIsOpportunity(Integer leadId) {
		log.info("inside the getVisitsByLeadIdAndIsOpportunity method...{}", leadId);
		return visitRepository.findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(leadId, false);
	}

	@Override
	@Cacheable(value = VISITS, key = "#isOpportunity", condition = "#isOpportunity!=null")
	public List<Visit> getAllLeadVisits(boolean isOpportunity) {
		log.info("inside the getAllLeadVisits method...{}", isOpportunity);
		return visitRepository.findByIsOpportunityOrderByCreatedDateDesc(isOpportunity);
	}
}
