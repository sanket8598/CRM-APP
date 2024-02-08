package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.CacheConstant.ROLES;
import static ai.rnt.crm.constants.RoleConstants.CRM_USER;
import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.util.RoleUtil.APP_ROLES;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Arrays.asList;
import static java.util.Objects.isNull;

import java.util.Comparator;
import java.util.List;
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
	@Cacheable(value=ROLES)
	public List<EmployeeMaster> getAdminAndUser() {
		return this.roleMasterRepository
				.findByEmployeeRoleIn(APP_ROLES.get()).stream()
				.filter(emp -> (isNull(emp.getDepartureDate())
						|| emp.getDepartureDate().isAfter(now().atZone(systemDefault())
					            .withZoneSameInstant(of(INDIA_ZONE))
					            .toLocalDate())))
				.sorted(Comparator.comparing(EmployeeMaster::getFirstName).thenComparing(EmployeeMaster::getLastName))
				.collect(Collectors.toList());
	}

	@Override
	public List<EmployeeMaster> getUsers() {
		return this.roleMasterRepository
				.findByEmployeeRoleIn(asList(CRM_USER)).stream()
				.filter(emp -> (isNull(emp.getDepartureDate())
						|| emp.getDepartureDate().isAfter(now().atZone(systemDefault())
					            .withZoneSameInstant(of(INDIA_ZONE))
					            .toLocalDate())))
				.sorted(Comparator.comparing(EmployeeMaster::getFirstName).thenComparing(EmployeeMaster::getLastName))
				.collect(Collectors.toList());
	}

}
