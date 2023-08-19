package ai.rnt.crm.security.config;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ai.rnt.crm.dto.EmployeeDto;
import ai.rnt.crm.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetailsService{

	private EmployeeService employeeService;
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			Optional<EmployeeDto> user = employeeService.getEmployeeByUserId(userId);
			if(Objects.nonNull(user) && user.isPresent())
				return new User(user.get().getUserID(), user.get().getPassword(),user.get().getEmployeeRole().stream().map(role->new  SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList()));
		} catch (Exception e) {
			log.error("Exception occurred while loading user by name... {}",userId);
		}
		throw new UsernameNotFoundException("User not found with username: " + userId);
	}

}
