package ai.rnt.crm.security.config;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.Role;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetailsService {

	private final EmployeeService employeeService;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			Optional<EmployeeDto> user = employeeService.getEmployeeByUserId(userId);
			if (Objects.nonNull(user) && user.isPresent())
				return new UserDetail(user.get().getUserID(), user.get().getPassword(), true, true, true, true,
						user.get().getEmployeeRole().stream().map(Role::getRoleName).map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList()),
						user.get().getStaffId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while loading user by name... {}", userId);
		}
		throw new UsernameNotFoundException("User not found with username: " + userId);
	}

}
