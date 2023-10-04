package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.AddEmail;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 12/09/2023.
 *
 */

public interface EmailRepository extends JpaRepository<AddEmail, Integer> {

	List<AddEmail> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

	Boolean existsByAddMailIdAndLeadLeadId(Integer addMailId, Integer leadId);

}
