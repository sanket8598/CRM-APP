package ai.rnt.crm.util;

import static ai.rnt.crm.constants.RoleConstants.NO_ROLE;
import static ai.rnt.crm.util.RoleUtil.CHECK_ADMIN;
import static ai.rnt.crm.util.RoleUtil.CHECK_USER;
import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import ai.rnt.crm.security.UserDetail;

@Component
public class AuditAwareUtil {

	public Integer getLoggedInStaffId() {
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();
			return details.getStaffId();
		}
		return null;
	}

	public String getLoggedInUserRole() {
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();
			return RoleUtil.getSingleRole(details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		}
		return NO_ROLE;
	}
	
	public boolean isAdmin() {
		return CHECK_ADMIN.test(getLoggedInUserRole());
	}
	public boolean isUser() {
		return CHECK_USER.test(getLoggedInUserRole());
	}
	
}
