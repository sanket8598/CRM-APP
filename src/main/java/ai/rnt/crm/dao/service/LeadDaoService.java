package ai.rnt.crm.dao.service;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Leads;

public interface LeadDaoService extends CrudService<Leads, LeadDto> {

	Leads addLead(Leads leads);
	
}
