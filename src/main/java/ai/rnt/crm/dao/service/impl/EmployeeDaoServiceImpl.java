package ai.rnt.crm.dao.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.repository.EmployeeMasterRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeDaoServiceImpl implements EmployeeDaoService{

	private EmployeeMasterRepository employeeMasterRepository;
	
	@Override
	public Optional<EmployeeMaster> getEmployeebyUserId(String userId) {
		return employeeMasterRepository.findByUserID(userId);
	}

}
