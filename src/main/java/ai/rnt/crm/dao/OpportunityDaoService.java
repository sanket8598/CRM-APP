package ai.rnt.crm.dao;

import java.util.List;

import ai.rnt.crm.entity.Opportunity;

public interface OpportunityDaoService {

	List<Opportunity> getOpportunityDashboardData();

	List<Opportunity> getOpportunityByStatus(String status);

	Opportunity addOpportunity(Opportunity opportunity);
}
