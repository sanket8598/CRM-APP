package ai.rnt.crm.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * This class used to extend and override the details of the spring security's User Class
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 19-08-2023
 * @see org.springframework.security.core.userdetails.UserDetails
 */
@Getter
@Setter
public class UserDetail extends User {

	private static final long serialVersionUID = 338308531428207638L;

	private Integer staffId;

	public UserDetail(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			Integer staffId) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.staffId = staffId;
	}

}
