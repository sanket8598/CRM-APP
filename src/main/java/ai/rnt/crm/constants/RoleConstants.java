package ai.rnt.crm.constants;

public class RoleConstants {

	private RoleConstants() {

	}

	public static final String CRM_ADMIN = "CRM Admin";

	public static final String CRM_USER = "CRM User";

	public static final String HAS_AUTORITY = "hasAuthority('";

	public static final String HAS_AUTHORITY_CLOSER = "')";

	public static final String CHECK_BOTH_ACCESS = HAS_AUTORITY + CRM_ADMIN + HAS_AUTHORITY_CLOSER + " || "
			+ HAS_AUTORITY + CRM_USER + HAS_AUTHORITY_CLOSER;

	public static final String CHECK_ADMIN_ACCESS = HAS_AUTORITY + CRM_ADMIN + HAS_AUTHORITY_CLOSER;

	public static final String CHECK_USER_ACCESS = HAS_AUTORITY + CRM_USER + HAS_AUTHORITY_CLOSER;

	public static final String NO_ROLE = "Don't Have Role";
}
