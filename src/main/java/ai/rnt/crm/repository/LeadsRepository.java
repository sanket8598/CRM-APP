package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.rnt.crm.entity.Leads;

public interface LeadsRepository extends JpaRepository<Leads, Integer> {

	List<Leads> findByStatusOrderByCreatedDateDesc(String leadsStatus);

	List<Leads> findByOrderByCreatedDateDesc();

	List<Leads> findByRemainderDueOnAndRemainderDueAtAndIsFollowUpRemainder(LocalDate todayAsDate, String time,
			boolean isFollowUpRemainder);

	@Query(value = "select source_name,count(lead_id) as leads from crm_lead cl ,crm_lead_source_master sm where cl.lead_source_id=sm.lead_source_id group by cl.lead_source_id", nativeQuery = true)
	List<Map<String, Integer>> getLeadSourceCount();

	@Query(value = "select source_name,count(lead_id) as leads from crm_lead cl ,crm_lead_source_master sm where cl.lead_source_id=sm.lead_source_id and assign_to = ?1 group by cl.lead_source_id", nativeQuery = true)
	List<Map<String, Integer>> getLeadSourceCount(Integer loggedInStaffId);
}
