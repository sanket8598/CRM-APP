package ai.rnt.crm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.LeadImportant;

public interface ImpLeadRepository extends JpaRepository<LeadImportant, Integer> {

	Optional<LeadImportant> findByLeadLeadIdAndEmployeeStaffId(Integer leadId, Integer staffId);


	List<LeadImportant> findByEmployeeStaffId(Integer loggedInStaffId);

}
