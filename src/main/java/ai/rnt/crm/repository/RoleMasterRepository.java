package ai.rnt.crm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.entity.RoleMaster;

public interface RoleMasterRepository extends JpaRepository<RoleMaster, Integer> {

	@Query("SELECT new EmployeeMaster(em.staffId,em.firstName, em.lastName,em.departureDate,em.emailId)"
			+ "FROM #{#entityName} as rm join rm.employees ur,EmployeeMaster as em "
			+ "WHERE rm.roleName In(?#{[0]}) and ur.deletedBy is null and em.staffId=ur "
			+ " and em.deletedBy is null group by em.staffId ")
	List<EmployeeMaster> findByEmployeeRoleIn(List<String> role);
}
