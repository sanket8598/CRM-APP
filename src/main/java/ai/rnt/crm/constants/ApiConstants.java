package ai.rnt.crm.constants;

public final class ApiConstants {

	private ApiConstants() {

	}

	public static final String SEPERATOR = "/";

	public static final String API = "api";

	public static final String VERSION = "v1";

	public static final String BASE = SEPERATOR + API + SEPERATOR + VERSION + SEPERATOR;

	public static final String AUTH = BASE + "auth" + SEPERATOR;

	public static final String LOGIN = SEPERATOR + "login";

	public static final String TOKENPARSE = SEPERATOR + "tokenparse";

	public static final String LEAD = BASE + "lead" + SEPERATOR;

	public static final String CREATE_LEAD = SEPERATOR + "create";

	public static final String GET_ALL_LEADS = SEPERATOR + "getAllLeads";

	public static final String GET_LEADS_BY_STATUS = GET_ALL_LEADS + SEPERATOR + "{leadsStatus}";

	public static final String SERVICE_FALLS = BASE + "serviceFalls" + SEPERATOR;

	public static final String GET_ALL_SERVICE_FALLS = SEPERATOR + "getAllSerciveFalls";

	public static final String GET_ALL_LEAD_SOURCE = SEPERATOR + "getAllLeadSource";

	public static final String GET_ADMIN_AND_USER = SEPERATOR + "getAdminAndUser";

	public static final String GET_DROP_DOWN_DATA = SEPERATOR + "getDropDownData";

	public static final String DASHBOARD_ALL_LEADS = SEPERATOR + "dashboardAllLeads";

	public static final String DASHBOARD_LEADS_BY_STATUS = SEPERATOR + "dashboardLeads" + SEPERATOR + "{leadsStatus}";

	public static final String COMPANY = BASE + "company" + SEPERATOR;

	public static final String COUNTRY = BASE + "country" + SEPERATOR;

	public static final String STATE = BASE + "state" + SEPERATOR;

	public static final String CITY = BASE + "city" + SEPERATOR;

	public static final String ALL_STATE = SEPERATOR + "allState";

	public static final String ALL_COUNTRIES = SEPERATOR + "allCountries";

	public static final String ALL_CITY = SEPERATOR + "allCity";

}
