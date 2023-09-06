package ai.rnt.crm.constants;

public final class ApiConstants {

	public static final String SEPERATOR = "/";
	public static final String API = "api";
	public static final String VERSION = "v1";
	public static final String BASE = SEPERATOR + API + SEPERATOR + VERSION + SEPERATOR;
	public static final String AUTH = BASE + "auth" + SEPERATOR;
	public static final String LOGIN = SEPERATOR + "login";
	public static final String TOKENPARSE = SEPERATOR + "tokenparse";
	public static final String LEAD = BASE + "lead" + SEPERATOR;
	public static final String CREATE_LEAD = SEPERATOR + "create";
	public static final String GET_LEADS_BY_STATUS = SEPERATOR + "getLeadsByStatus"+SEPERATOR+"{leadsStatus}";
	public static final String GET_ALL_LEADS = SEPERATOR + "getAllLeads";

}
