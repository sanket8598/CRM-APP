package ai.rnt.crm.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.LeadSortFilter;

public interface LeadSortFilterRepository extends JpaRepository<LeadSortFilter, Integer>{

	Optional<LeadSortFilter> findTopByEmployeeStaffIdOrderByLeadSortFilterIdDesc(Integer loggedInStaffId);

}
