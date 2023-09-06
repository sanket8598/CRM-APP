package ai.rnt.crm.constants;

public class RoleConstants {

	public static final String CRM_ADMIN="CRM Admin";
	public static final String CRM_USER="CRM User";
	public static final String CHECK_BOTH_ACCESS="hasAuthority('"+CRM_ADMIN+"')"+" || hasAuthority('"+CRM_USER+"')";
	public static final String CHECK_ADMIN_ACCESS="hasAuthority('"+CRM_ADMIN+"')";
	public static final String CHECK_USER_ACCESS="hasAuthority('"+CRM_USER+"')";
}
