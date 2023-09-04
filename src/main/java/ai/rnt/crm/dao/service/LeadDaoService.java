package ai.rnt.crm.dao.service;

import org.springframework.http.ResponseEntity;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Leads;

public interface LeadDaoService extends CrudService<Leads,LeadDto>{

	ResponseEntity<String> addLead(String lead);
}
