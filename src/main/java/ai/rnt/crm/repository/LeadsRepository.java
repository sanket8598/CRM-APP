package ai.rnt.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Leads;

public interface LeadsRepository extends JpaRepository<Leads, Integer> {

	List<Leads> findByStatusOrderByCreatedDateDesc(String leadsStatus);

	List<Leads> findByOrderByCreatedDateDesc();

	List<Leads> findByRemainderDueOnAndRemainderDueAtAndIsFollowUpRemainder(Date todayAsDate, String time,
			boolean isFollowUpRemainder);

}
