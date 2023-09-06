package ai.rnt.crm.dao.service;

import java.util.List;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Leads;

public interface LeadDaoService extends CrudService<Leads, LeadDto> {

	Leads addLead(Leads leads);

	Leads getLeadsByStatus(String leadsStatus);
	
    List<LeadDto> getAll();

}
