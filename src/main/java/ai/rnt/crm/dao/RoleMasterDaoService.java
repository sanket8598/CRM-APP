package ai.rnt.crm.dao;

import java.util.List;

import ai.rnt.crm.entity.EmployeeMaster;

public interface RoleMasterDaoService {

	List<EmployeeMaster> getAdminAndUser();

}
