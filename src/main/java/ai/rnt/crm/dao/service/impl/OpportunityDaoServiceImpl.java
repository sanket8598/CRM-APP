package ai.rnt.crm.dao.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.OpportunityDaoService;
import ai.rnt.crm.entity.Opportunity;
import ai.rnt.crm.repository.OpportunityRepository;
import lombok.RequiredArgsConstructor;

/**
 * @author Sanket Wakankar
 * @since 03-02-2024
 * @version 1.0
 *
 */
@Service
@RequiredArgsConstructor
@Transactional
public class OpportunityDaoServiceImpl implements OpportunityDaoService {

	private final OpportunityRepository opportunityRespoitory;

	@Override
	public Opportunity addOpportunity(Opportunity opportunity) {
		return opportunityRespoitory.save(opportunity);
	}

}
