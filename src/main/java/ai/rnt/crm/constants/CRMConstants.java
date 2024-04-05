package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CRMConstants {

	public static final String DATA = "Data";
	public static final String TOKEN = "token";
	public static final String COUNT = "Count";
	public static final String EMPLOYEE = "Employee";
	public static final String STAFF_ID = "staffId";
	public static final String ROLE = "role";
	public static final String EMAIL_ID = "emailId";
	public static final String FULL_NAME = "fullName";
	public static final String LEAD_ID = "leadId";
	public static final String OPTY_ID = "optyId";
	public static final String OTHER = "Other";
	public static final String LEAD_SOURCE_MASTER = "LeadSourceMaster";
	public static final String LEAD_SOURCE_NAME = "leadSourceName";
	public static final String SERVICE_FALLS_MASTER = "ServiceFallMaster";
	public static final String SERVICE_FALL_ID = "serviceFallId";
	public static final String SERVICE_FALL = "serviceFalls";
	public static final String LEAD_INFO = "LeadInfo";
	public static final String LEAD_SOURCE = "leadSource";
	public static final String TIMELINE = "Timeline";
	public static final String ACTIVITY = "Activity";
	public static final String UPNEXT_DATA = "UpNext";
	public static final String TASK = "Task" + DATA;
	public static final String COUNTDATA = "COUNTDATA";
	public static final String SERVICE_FALL_DATA = "serviceFall" + DATA;
	public static final String LEAD_SOURCE_DATA = LEAD_SOURCE + DATA;
	public static final String DOMAINS = "domains";
	public static final String DOMAIN_MASTER_DATA = "domain" + DATA;
	public static final String ASSIGN_DATA = "assignTo" + DATA;
	public static final String SORT_FILTER = "sortFilter";
	public static final String UPNEXT_DATA_KEY = UPNEXT_DATA + DATA;
	public static final String ALL_TASK = "allTask";
	public static final String COMPLETED_TASK_KEY = "completedTask";
	public static final String IN_PROGRESS_TASK_KEY = "inProgressTask";
	public static final String ON_HOLD_TASK_KEY = "onHoldTask";
	public static final String NOT_STARTED_TASK_KEY = "notStartedTask";
	public static final String ALL_TASK_COUNT = ALL_TASK + COUNT;
	public static final String COMPLETED_TASK_COUNT = COMPLETED_TASK_KEY + COUNT;
	public static final String IN_PROGRESS_TASK_COUNT = IN_PROGRESS_TASK_KEY + COUNT;
	public static final String ON_HOLD_TASK_COUNT = ON_HOLD_TASK_KEY + COUNT;
	public static final String NOT_STARTED_TASK_COUNT = NOT_STARTED_TASK_KEY + COUNT;
	public static final String COUNT_BY_STATUS = "countByStatus";

}
