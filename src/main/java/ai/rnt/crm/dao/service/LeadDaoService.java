package ai.rnt.crm.dao.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.Leads;

public interface LeadDaoService extends CrudService<Leads, LeadDto> {

	Leads addLead(Leads leads);

	List<Leads> getLeadsByStatus(String leadsStatus);

	List<Leads> getAllLeads();

	List<Leads> getLeadDashboardData();

	Optional<Leads> getLeadById(Integer leadId);
	
	Optional<LeadImportant> addImportantLead(LeadImportant leadImportant);
	
	boolean deleteImportantLead(Integer leadId,Integer staffId);

	List<LeadImportant> findLeadByEmployeeStaffId(Integer loggedInStaffId);

	List<Leads> getFollowUpLeads(Date todayAsDate, String time);

}
