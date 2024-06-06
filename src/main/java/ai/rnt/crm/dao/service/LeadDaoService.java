package ai.rnt.crm.dao.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import ai.rnt.crm.dto.LeadDto;
import ai.rnt.crm.entity.Description;
import ai.rnt.crm.entity.LeadImportant;
import ai.rnt.crm.entity.Leads;

public interface LeadDaoService extends CrudService<Leads, LeadDto> {

	Leads addLead(Leads leads);

	List<Leads> getLeadsByStatus(String leadsStatus);

	List<Leads> getAllLeads();

	List<Leads> getLeadDashboardData();

	Optional<Leads> getLeadById(Integer leadId);

	Optional<LeadImportant> addImportantLead(LeadImportant leadImportant);

	boolean deleteImportantLead(Integer leadId, Integer staffId);

	List<LeadImportant> findLeadByEmployeeStaffId(Integer loggedInStaffId);

	List<Leads> getFollowUpLeads(LocalDate todayAsDate, String time);

	List<Map<String, Integer>> getLeadSourceCount();

	List<Map<String, Integer>> getLeadSourceCount(Integer loggedInStaffId);

	Description addDesc(Description description);

	List<Description> getDescriptionByLeadIdAndIsOpportunity(Integer leadId);

	List<Description> getDescriptionByLeadId(Integer leadId);

}
