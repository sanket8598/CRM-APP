package ai.rnt.crm.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.projection.EmailIdProjection;

public interface EmployeeMasterRepository extends JpaRepository<EmployeeMaster, Integer> {

	Optional<EmployeeMaster> findByUserId(String userId);

	Optional<EmployeeMaster> findByFirstNameAndLastName(String firstName, String lastName);
	
	List<EmailIdProjection> findEmailIdByDepartureDateIsNullOrDepartureDateBefore(LocalDate deparatureDateShouldSmaller);
}
