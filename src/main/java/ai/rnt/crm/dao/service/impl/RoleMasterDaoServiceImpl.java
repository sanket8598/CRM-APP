package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.util.RoleUtil.APP_ROLES;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import static java.util.Objects.isNull;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dao.service.RoleMasterDaoService;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.repository.RoleMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleMasterDaoServiceImpl implements RoleMasterDaoService {
	
	public final RoleMasterRepository roleMasterRepository;
	
	@Override
	@Cacheable(value="roles")
	public List<EmployeeMaster> getAdminAndUser() {
		return this.roleMasterRepository
				.findByEmployeeRoleIn(APP_ROLES.get()).stream()
				.filter(emp -> (isNull(emp.getDepartureDate())
						|| emp.getDepartureDate().isAfter(LocalDate.now())))
				.sorted(Comparator.comparing(EmployeeMaster::getFirstName).thenComparing(EmployeeMaster::getLastName))
				.collect(Collectors.toList());
	}

}
