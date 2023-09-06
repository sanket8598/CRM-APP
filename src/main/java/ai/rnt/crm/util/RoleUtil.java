package ai.rnt.crm.util;

import static ai.rnt.crm.constants.RoleConstants.CRM_ADMIN;
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
		return RoleUtil.APP_ROLES.get().stream().anyMatch(s::equalsIgnoreCase);
	};

	public static final Supplier<List<String>> APP_ROLES = () -> Arrays.asList(CRM_ADMIN, CRM_USER);

	public static final UnaryOperator<String> GET_ROLE= s -> ALLOW_ROLES.test(s) ? s : "Don't Have Role";
}
