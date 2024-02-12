package ai.rnt.crm.dao.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.repository.OpportunityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @since 03-02-2024
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OpportunityDaoServiceImpl implements OpportunityDaoService {

	private final OpportunityRepository opportunityRespoitory;

	@Override
	public List<Opportunity> getOpportunityDashboardData() {
		log.info("inside the getOpportunityDashboardData method...");
		return opportunityRespoitory.findByOrderByCreatedDateDesc();
	}

	@Override
	public List<Opportunity> getOpportunityByStatus(String status) {
		log.info("inside the getOpportunityByStatus method...{}", status);
		return opportunityRespoitory.findByStatusOrderByCreatedDateDesc(status);
	}

	@Override
	public Opportunity addOpportunity(Opportunity opportunity) {
		log.info("inside the addOpportunity method...{}");
		return opportunityRespoitory.save(opportunity);
	}

	@Override
	public List<Opportunity> getOpportunityByStatusIn(List<String> status) {
		log.info("inside the getOpportunityByStatusIn method...{}");
		return opportunityRespoitory.findByStatusInOrderByCreatedDateDesc(status);
	}

	@Override
	public Optional<Opportunity> findOpportunity(Integer optId) {
		log.info("inside the findOpportunityById method...{}", optId);
		return opportunityRespoitory.findById(optId);
	}
}
