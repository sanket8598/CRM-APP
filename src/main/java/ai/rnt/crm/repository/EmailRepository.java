package ai.rnt.crm.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

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

	List<Email> findByScheduledOnAndScheduledAtAndScheduled(Date todayAsDate, String time, boolean scheduled);

}
