package ai.rnt.crm.util;

import static ai.rnt.crm.constants.RoleConstants.NO_ROLE;
import static ai.rnt.crm.util.RoleUtil.CHECK_ADMIN;
import static ai.rnt.crm.util.RoleUtil.CHECK_USER;
import static java.util.Objects.nonNull;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAwareUtil {
	
	private final JWTTokenHelper helper;
	
	@Value("${spring.profiles.active}")
	public static  String activeProfile;

	public Integer getLoggedInStaffId() {
		log.info("inside getLoggedInStaffId method... ");
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();
			return details.getStaffId();
		}
		return null;
	}

	public String getLoggedInUserRole() {
		log.info("inside getLoggedInUserRole method... ");
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getDetails())) {
			UserDetail details = (UserDetail) getContext().getAuthentication().getDetails();
			return RoleUtil.getSingleRole(
					details.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
		}
		return NO_ROLE;
	}

	public String getLoggedInUserName() {
		log.info("inside getLoggedInUserName method... ");
		if (nonNull(getContext()) && nonNull(getContext().getAuthentication())
				&& nonNull(getContext().getAuthentication().getPrincipal())) {
			return helper.getfullNameOfLoggedInUser(getContext().getAuthentication().getPrincipal().toString());
		}
		return null;
	}

	public boolean isAdmin() {
		return CHECK_ADMIN.test(getLoggedInUserRole());
	}

	public boolean isUser() {
		return CHECK_USER.test(getLoggedInUserRole());
	}

}
