package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.Meetings;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 25/11/2023.
 *
 */
public interface MeetingRepository extends JpaRepository<Meetings, Integer> {

	List<Meetings> findByLeadLeadIdOrderByCreatedDateDesc(Integer leadId);

	List<Meetings> findByLeadLeadIdAndIsOpportunityOrderByCreatedDateDesc(Integer leadId, boolean flag);

}
