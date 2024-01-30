package ai.rnt.crm.dao.service.impl;

import static ai.rnt.crm.constants.SchedularConstant.INDIA_ZONE;
import static ai.rnt.crm.dto_mapper.EmployeeToDtoMapper.TO_EMPLOYEE;
import static java.time.LocalDateTime.now;
import static java.time.ZoneId.of;
import static java.time.ZoneId.systemDefault;
import static java.util.Objects.nonNull;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ai.rnt.crm.dao.service.EmployeeDaoService;
import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.entity.EmployeeMaster;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.projection.EmailIdProjection;
import ai.rnt.crm.repository.EmployeeMasterRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeDaoServiceImpl implements EmployeeDaoService {

	private final EmployeeMasterRepository employeeMasterRepository;

	@Override
	public Optional<EmployeeMaster> getEmployeebyUserId(String userId) {
		return employeeMasterRepository.findByUserId(userId);
	}

	@Override
	public Optional<EmployeeDto> getById(Integer assignTo) {
		return TO_EMPLOYEE.apply(employeeMasterRepository.findById(assignTo)
				.orElseThrow(() -> new ResourceNotFoundException("Employee", "staffId", assignTo)));
	}

	@Override
	public Optional<EmployeeMaster> findByName(String firstName, String lastName) {
		return employeeMasterRepository.findByFirstNameAndLastName(firstName, lastName);
	}

	@Override
	public List<String> activeEmployeeEmailIds() {
		return employeeMasterRepository.findEmailIdByDepartureDateIsNullOrDepartureDateBefore(now().atZone(systemDefault())
	            .withZoneSameInstant(of(INDIA_ZONE))
	            .toLocalDate()).stream()
				.filter(e -> nonNull(e) && nonNull(e.getEmailId())).map(EmailIdProjection::getEmailId)
				.collect(Collectors.toList());
	}

	@Override
	public String getEmailId(Integer staffId) {
		return employeeMasterRepository.findEmailIdByStaffId(staffId).getEmailId();
	}

	@Override
	public Integer findTopStaffIdByEmailId(String email) {
		return employeeMasterRepository.findTopStaffIdByEmailId(email).getStaffId();
	}
}
