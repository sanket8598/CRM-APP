package ai.rnt.crm.util;

import static ai.rnt.crm.constants.RoleConstants.CRM_ADMIN;
import static ai.rnt.crm.constants.RoleConstants.CRM_USER;
import static ai.rnt.crm.constants.RoleConstants.NO_ROLE;
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
		return RoleUtil.APP_ROLES.get().stream().anyMatch(s::equalsIgnoreCase);
	};

	public static final Supplier<List<String>> APP_ROLES = () -> Arrays.asList(CRM_ADMIN, CRM_USER);

	public static final UnaryOperator<String> GET_ROLE = s -> {
		if (CRM_ADMIN.equalsIgnoreCase(s))
			return CRM_ADMIN;
		else if (CRM_USER.equalsIgnoreCase(s))
			return CRM_USER;
		return NO_ROLE;
	};

	/**
	 * This Predicate return true if it contains the CRM Admin Role.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> CHECK_ADMIN = s -> {
		if (isNull(s))
			return false;
		return CRM_ADMIN.equalsIgnoreCase(s);
	};
	/**
	 * This Predicate return true if it contains the CRM User Role.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> CHECK_USER = s -> {
		if (isNull(s))
			return false;
		return CRM_USER.equalsIgnoreCase(s);
	};

	public static final String getSingleRole(List<String> roles) {
		for (String s : roles) {
			if (CRM_ADMIN.equalsIgnoreCase(s))
				return CRM_ADMIN;
		}
		for (String s : roles) {
			if (CRM_USER.equalsIgnoreCase(s))
				return CRM_USER;
		}
		return NO_ROLE;
	}

}
