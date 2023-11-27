package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Call;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 11/09/2023.
 *
 */
public interface AddCallRepository extends JpaRepository<Call, Integer> {

	List<Call> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

}
