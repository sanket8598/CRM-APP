package ai.rnt.crm.dao.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.VisitDaoService;
import ai.rnt.crm.entity.Visit;
import ai.rnt.crm.repository.VisitRepository;
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

	@Override
	public Visit saveVisit(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	public List<Visit> getVisitsByLeadId(Integer leadId) {
		return visitRepository.findByLeadLeadIdOrderByCreatedDateDesc(leadId);
	}
}
