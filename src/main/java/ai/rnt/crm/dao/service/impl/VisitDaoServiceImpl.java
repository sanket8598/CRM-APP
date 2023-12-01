package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.entity.VisitTask;
import ai.rnt.crm.repository.VisitRepository;
import ai.rnt.crm.repository.VisitTaskRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 14-09-2023.
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class VisitDaoServiceImpl implements VisitDaoService {

	private final VisitRepository visitRepository;
	private final VisitTaskRepository visitTaskRepository;

	@Override
	public Visit saveVisit(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	public List<Visit> getVisitsByLeadId(Integer leadId) {
		return visitRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}

	@Override
	public Optional<Visit> getVisitsByVisitId(Integer visitId) {
		return visitRepository.findById(visitId);
	}

	@Override
	public VisitTask addVisitTask(VisitTask visitTask) {
		return visitTaskRepository.save(visitTask);
	}

	@Override
	public Optional<VisitTask> getVisitTaskById(Integer taskId) {
		return visitTaskRepository.findById(taskId);
	}
}
