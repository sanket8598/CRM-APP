package ai.rnt.crm.dao.service;

import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.opportunity.OpportunityDto;
import ai.rnt.crm.entity.Opportunity;

public interface OpportunityDaoService extends CrudService<Opportunity, OpportunityDto> {

	List<Opportunity> getOpportunityDashboardData();

	List<Opportunity> getOpportunityByStatus(String status);

	Opportunity addOpportunity(Opportunity opportunity);

	List<Opportunity> getOpportunityByStatusIn(List<String> asList);

	Optional<Opportunity> findOpportunity(Integer optId);

	List<Opportunity> findAllOpty();
}
