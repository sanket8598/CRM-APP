package ai.rnt.crm.constants;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public final class DateFormatterConstant {
	
	public static final DateTimeFormatter DATE_TIME_WITH_AM_OR_PM = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	public static final SimpleDateFormat DEFAULT_UTIL_DATE_FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
	public static final SimpleDateFormat DD_MMM_YYYY = new SimpleDateFormat("dd-MMM-yyyy");
	public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat DATE_TIME_WITH_AM_AND_PM = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");


}
