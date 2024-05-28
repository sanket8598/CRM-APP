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
public interface CallRepository extends JpaRepository<Call, Integer> {

	List<Call> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

	List<Call> findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(Integer leadId, boolean flag);

	List<Call> findByIsOpportunityOrderByCreatedDateDesc(boolean isOpportunity);

}
