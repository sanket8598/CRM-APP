package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CacheConstant {

	public static final String LEADS = "leads";
	public static final String LEAD_SOURCE = "leadSource";
	public static final String LEAD_SOURCE_ID = "#leadSourceId";

	public static final String SERVICE_FALLS = "serviceFall";
	public static final String SERVICE_FALLS_ID = "#serviceFallsId";
	public static final String ROLES = "roles";

	public static final String OPPORTUNITIES = "opportunities";

	public static final String EXCEL_HEADER = "excelHeaders";

	public static final String STATES = "states";
	public static final String COUNTRY = "countries";
	public static final String CITY = "cities";
	public static final String CONTACT = "contact";
	public static final String COMPANY = "company";

	public static final String STATE_ID = "#stateId";
	public static final String COUNTRY_ID = "#countryId";
	public static final String CITY_ID = "#cityId";
	public static final String COMPANY_ID = "#companyId";

	public static final String DOMAIN = "domain";
	public static final String DOMAIN_ID = "#domainId";

	public static final String LEAD_ID = "#leadId";

	public static final String CALLS = "calls";
	public static final String VISITS = "visits";
	public static final String MEETINGS = "meetings";
	public static final String CALLS_BY_LEAD_ID = "callsByLeadId";
	public static final String VISITS_BY_LEAD_ID = "visitsByLeadId";
	public static final String MEETINGS_BY_LEAD_ID = "meetingsByLeadId";

	public static final String CALL_TASK = "callTask";
	public static final String VISIT_TASK = "visitTask";
	public static final String MEETING_TASK = "meetingTask";
	
	public static final String CURRENCY = "currency";
}
