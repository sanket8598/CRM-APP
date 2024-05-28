package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.rnt.crm.entity.Email;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 *
 */

public interface EmailRepository extends JpaRepository<Email, Integer> {

	List<Email> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

	Boolean existsByMailIdAndLeadLeadId(Integer addMailId, Integer leadId);

	List<Email> findByScheduledOnAndScheduledAtAndScheduled(LocalDate todayAsDate, String time, boolean scheduled);

	List<Email> findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(Integer leadId, boolean flag);

	@Query(value = "select mail_password from corp_mail where mail_id = ?1", nativeQuery = true)
	String findPasswordByMailId(String userName);

	List<Email> findByIsOpportunityOrderByCreatedDateDesc(boolean isOpportunity);

}
