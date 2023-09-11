package ai.rnt.crm.security.config;

import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.dto.Role;
import ai.rnt.crm.exception.ResourceNotFoundException;
import ai.rnt.crm.security.UserDetail;
import ai.rnt.crm.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * This class overrides the details of the spring security's UserDetailsService
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 * @see org.springframework.security.core.userdetails.UserDetails
 */
/**
 * @author Sanket Wakankar
 * @version 1.0
 * @since 17-08-2023
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetailsService {

	private final EmployeeService employeeService;

	/**
	 * Overrided method use to pass the User with the Custom Details. Core interface
	 * which loads user-specific data. 
	 * {@inheritDoc}
	 * 
	 * @Version 1.0
	 */
	@Override
	public UserDetail loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			EmployeeDto user = employeeService.getEmployeeByUserId(userId)
					.orElseThrow(() -> new ResourceNotFoundException("Employee", "userId", userId));
			if (Objects.nonNull(user))
				return new UserDetail(
						user.getUserID(), user.getPassword(), true, true, true, true, user.getEmployeeRole().stream()
								.map(Role::getRoleName).map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
						user.getStaffId());
		} catch (Exception e) {
			log.error("Exception occurred while loading user by name... {}", userId, e);
		}
		throw new UsernameNotFoundException("User not found with username: " + userId);
	}

}
