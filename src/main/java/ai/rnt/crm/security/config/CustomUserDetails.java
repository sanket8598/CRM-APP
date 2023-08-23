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
	 * Overrided method use to pass the User with the Custom Details.
	 * Core interface which loads user-specific data.
	 * <p>
	 * It is used throughout the framework as a user DAO and is the strategy used by the
	 * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider
	 * DaoAuthenticationProvider}.
	 *
	 * <p>
	 * The interface requires only one read-only method, which simplifies support for new
	 * data-access strategies.
	 *
	 * @author Ben Alex
	 * @see org.springframework.security.authentication.dao.DaoAuthenticationProvider
	 * @see UserDetails
	 */
	@Override
	public UserDetail loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			Optional<EmployeeDto> user = employeeService.getEmployeeByUserId(userId);
			if (Objects.nonNull(user) && user.isPresent())
				return new UserDetail(user.get().getUserID(), user.get().getPassword(), true, true, true, true,
						user.get().getEmployeeRole().stream().map(Role::getRoleName).map(SimpleGrantedAuthority::new)
								.collect(Collectors.toList()),
						user.get().getStaffId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occurred while loading user by name... {}", userId,e);
		}
		throw new UsernameNotFoundException("User not found with username: " + userId);
	}

}
