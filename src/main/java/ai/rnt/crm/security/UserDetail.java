package ai.rnt.crm.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ai.rnt.crm.entity.RoleMaster;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



/**
 * 
 * This class overrides the details of the spring security's UserDetails
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 * @see org.springframework.security.core.userdetails.UserDetails
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDetail implements UserDetails{
	

	private static final long serialVersionUID = 338308531428207638L;

	private String userId;
	
	private String password1;
	
	private List<RoleMaster> roles=new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(role->new  SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password1;
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}


}
