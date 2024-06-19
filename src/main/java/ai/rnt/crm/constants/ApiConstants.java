package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 11/09/2023.
 * @version 1.0
 *
 */
@NoArgsConstructor(access = PRIVATE)
public final class ApiConstants {

	public static final String SEPERATOR = "/";
	public static final String API = "api";
	public static final String VERSION = "v1";
	public static final String ADD = "add";
	public static final String EDIT = "edit";
	public static final String UPDATE = "update";
	public static final String ASSIGN = "assign";
	public static final String DELETE = "delete";
	public static final String TASK = "task";
	public static final String STATUS = "{status}";
	public static final String TASK_ID = "{taskId}";
	public static final String BASE = SEPERATOR + API + SEPERATOR + VERSION + SEPERATOR;
	public static final String AUTH = BASE + "auth" + SEPERATOR;

	// Login
	public static final String LOGIN = SEPERATOR + "login";
	public static final String TOKENPARSE = SEPERATOR + "tokenparse";
	public static final String GET_ADMIN_AND_USER = SEPERATOR + "getAdminAndUser";

	// Lead
	public static final String LEAD_ID = "{leadId}";
	public static final String LEAD = BASE + "lead" + SEPERATOR;
	public static final String CREATE_LEAD = SEPERATOR + "create";
	public static final String EDIT_LEAD = SEPERATOR + EDIT + SEPERATOR + LEAD_ID;
	public static final String ASSIGN_LEAD = SEPERATOR + ASSIGN;
	public static final String QUALIFY_LEAD = SEPERATOR + "qualify" + SEPERATOR + LEAD_ID;
	public static final String DIS_QUALIFY_LEAD = SEPERATOR + "disQualify" + SEPERATOR + LEAD_ID;
	public static final String UPDATE_LEAD_CONTACT = SEPERATOR + "updateLeadContact" + SEPERATOR + LEAD_ID;
	public static final String IMPORTANT = SEPERATOR + "important" + SEPERATOR + LEAD_ID + SEPERATOR + STATUS;
	public static final String REACTIVE = SEPERATOR + "reactive" + SEPERATOR + LEAD_ID;
	public static final String ADD_SORT_FILTER = SEPERATOR + "addSortFilter";
	public static final String UPLOAD_EXCEL = SEPERATOR + "uploadExcel";
	public static final String EDIT_QUALIFY_LEAD = SEPERATOR + "qualify" + SEPERATOR + EDIT + SEPERATOR + LEAD_ID;
	public static final String GET_ALL_LEADS = SEPERATOR + "getAllLeads";
	public static final String GET_LEADS_BY_STATUS = GET_ALL_LEADS + SEPERATOR + "{leadsStatus}";
	public static final String GET_ALL_SERVICE_FALLS = SEPERATOR + "getAllSerciveFalls";
	public static final String SERVICE_FALLS = BASE + "serviceFalls" + SEPERATOR;
	public static final String GET_ALL_LEAD_SOURCE = SEPERATOR + "getAllLeadSource";
	public static final String GET_DROP_DOWN_DATA = SEPERATOR + "getDropDownData";
	public static final String DASHBOARD_ALL_LEADS = SEPERATOR + "dashboardAllLeads";
	public static final String DASHBOARD_LEADS_BY_STATUS = SEPERATOR + "dashboardLeads" + SEPERATOR + "{leadsStatus}";

	// Call
	public static final String CALL_ID = "{callId}";
	public static final String CALL = BASE + "call" + SEPERATOR;
	public static final String ADD_CALL = SEPERATOR + ADD + SEPERATOR + LEAD_ID;
	public static final String EDIT_CALL = EDIT + SEPERATOR + CALL_ID;
	public static final String UPDATE_CALL = UPDATE + SEPERATOR + CALL_ID + SEPERATOR + STATUS;
	public static final String ASSIGN_CALL = SEPERATOR + ASSIGN;
	public static final String DELETE_CALL = SEPERATOR + DELETE + SEPERATOR + CALL_ID;

	public static final String ADD_CALL_TASK = SEPERATOR + TASK + SEPERATOR + LEAD_ID + SEPERATOR + CALL_ID;
	public static final String GET_CALL_TASK = SEPERATOR + TASK + SEPERATOR + TASK_ID;
	public static final String UPDATE_CALL_TASK = SEPERATOR + TASK + SEPERATOR + TASK_ID;
	public static final String ASSIGN_CALL_TASK = SEPERATOR + TASK + SEPERATOR + ASSIGN;
	public static final String DELETE_CALL_TASK = SEPERATOR + TASK + SEPERATOR + DELETE + SEPERATOR + TASK_ID;

	// Visit
	public static final String VISIT_ID = "{visitId}";
	public static final String VISIT = BASE + "visit" + SEPERATOR;
	public static final String EDIT_VISIT = SEPERATOR + EDIT + SEPERATOR + VISIT_ID;
	public static final String UPDATE_VISIT = SEPERATOR + UPDATE + SEPERATOR + VISIT_ID + SEPERATOR + STATUS;
	public static final String ASSIGN_VISIT = SEPERATOR + ASSIGN;
	public static final String DELETE_VISIT = SEPERATOR + DELETE + SEPERATOR + VISIT_ID;

	public static final String ADD_VISIT_TASK = SEPERATOR + TASK + SEPERATOR + LEAD_ID + SEPERATOR + VISIT_ID;
	public static final String GET_VISIT_TASK = SEPERATOR + TASK + SEPERATOR + TASK_ID;
	public static final String UPDATE_VISIT_TASK = SEPERATOR + TASK + SEPERATOR + TASK_ID;
	public static final String ASSIGN_TASK = SEPERATOR + TASK + SEPERATOR + ASSIGN;
	public static final String DELETE_TASK = SEPERATOR + TASK + SEPERATOR + DELETE + SEPERATOR + TASK_ID;

	// Meeting
	public static final String MEETING_ID = "{meetingId}";
	public static final String MEETING = BASE + "meeting" + SEPERATOR;
	public static final String ADD_MEETING = SEPERATOR + ADD + SEPERATOR + LEAD_ID;
	public static final String EDIT_MEETING = SEPERATOR + EDIT + SEPERATOR + MEETING_ID;
	public static final String UPDATE_MEETING = SEPERATOR + UPDATE + SEPERATOR + MEETING_ID + SEPERATOR + STATUS;
	public static final String ASSIGN_MEETING = SEPERATOR + ASSIGN;
	public static final String DELETE_MEETING = SEPERATOR + DELETE + SEPERATOR + MEETING_ID;

	public static final String ADD_MEETING_TASK = SEPERATOR + TASK + SEPERATOR + LEAD_ID + SEPERATOR + MEETING_ID;
	public static final String GET_MEETING_TASK = SEPERATOR + TASK + SEPERATOR + TASK_ID;
	public static final String UPDATE_MEETING_TASK = SEPERATOR + TASK + SEPERATOR + TASK_ID;
	public static final String ASSIGN_MEETING_TASK = SEPERATOR + TASK + SEPERATOR + ASSIGN;
	public static final String DELETE_MEETING_TASK = SEPERATOR + TASK + SEPERATOR + DELETE + SEPERATOR + TASK_ID;

	// LeadTask
	public static final String LEAD_TASK = BASE + "lead" + SEPERATOR + TASK + SEPERATOR;
	public static final String ADD_LEAD_TASK = SEPERATOR + ADD + SEPERATOR + LEAD_ID;
	public static final String GET_LEAD_TASK = SEPERATOR + TASK_ID;
	public static final String UPDATE_LEAD_TASK = SEPERATOR + UPDATE + SEPERATOR + TASK_ID;
	public static final String ASSIGN_LEAD_TASK = SEPERATOR + ASSIGN;
	public static final String DELETE_LEAD_TASK = SEPERATOR + DELETE + SEPERATOR + TASK_ID;

	// Email
	public static final String EMAIL_ID = "{mailId}";
	public static final String EMAIL = BASE + "email" + SEPERATOR;
	public static final String GET_ALL_MAIL_ID = GET_ADMIN_AND_USER + SEPERATOR + "{email}";
	public static final String ADD_EMAIL = SEPERATOR + "addEmail" + SEPERATOR + LEAD_ID + SEPERATOR + STATUS;
	public static final String GET_EMAIL = SEPERATOR + EMAIL_ID;
	public static final String UPDATE_EMAIL = SEPERATOR + UPDATE + SEPERATOR + EMAIL_ID + SEPERATOR + STATUS;
	public static final String ASSIGN_EMAIL = SEPERATOR + ASSIGN;
	public static final String DELETE_EMAIL = SEPERATOR + DELETE + SEPERATOR + EMAIL_ID;
	
	// Customer
    public static final String CUSTOMER = BASE + "customer" + SEPERATOR;

	// City
	public static final String CITY = BASE + "city" + SEPERATOR;
	public static final String ALL_CITY = SEPERATOR + "allCity";

	// Company
	public static final String COMPANY = BASE + "company" + SEPERATOR;
	public static final String COMPANIES = SEPERATOR + "companies";

	// Contact
	public static final String CONTACT_ID = SEPERATOR + "{contactId}";
	public static final String CONTACT = BASE + "contact" + SEPERATOR;
	public static final String CREATE_CONTACT = SEPERATOR + LEAD_ID;

	// Country
	public static final String COUNTRY = BASE + "country" + SEPERATOR;
	public static final String ALL_COUNTRIES = SEPERATOR + "allCountries";

	// State
	public static final String STATE = BASE + "state" + SEPERATOR;
	public static final String ALL_STATE = SEPERATOR + "allState";

	// Excel
	public static final String EXCEL = BASE + "excel" + SEPERATOR;
	public static final String DOWNLOAD = SEPERATOR + "download";
}
