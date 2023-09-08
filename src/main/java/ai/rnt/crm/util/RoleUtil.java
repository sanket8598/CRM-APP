package ai.rnt.crm.util;

import static ai.rnt.crm.constants.RoleConstants.CRM_ADMIN;
import static ai.rnt.crm.constants.RoleConstants.NO_ROLE;
import static ai.rnt.crm.constants.RoleConstants.CRM_USER;
import static java.util.Objects.isNull;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class RoleUtil {

	/**
	 * This Predicate return true if it contains the Role.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> ALLOW_ROLES = s -> {
		if (isNull(s))
			return false;
		if(RoleUtil.APP_ROLES.get().stream().anyMatch(s::equalsIgnoreCase))
		   return true;
		return false;
	};

	public static final Supplier<List<String>> APP_ROLES = () -> Arrays.asList(CRM_ADMIN, CRM_USER);

	public static final UnaryOperator<String> GET_ROLE= s ->{
		if(CRM_ADMIN.equalsIgnoreCase(s))
			return CRM_ADMIN;
		else if(CRM_USER.equalsIgnoreCase(s)) 
			return CRM_USER;
		return NO_ROLE;
	};
}
