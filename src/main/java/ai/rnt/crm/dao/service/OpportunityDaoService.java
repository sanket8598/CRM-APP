package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.entity.Opportunity;

public interface OpportunityDaoService {

	List<Opportunity> getOpportunityDashboardData();

	List<Opportunity> getOpportunityByStatus(String status);

	Opportunity addOpportunity(Opportunity opportunity);

}
