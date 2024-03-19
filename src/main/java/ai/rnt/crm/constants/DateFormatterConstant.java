package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class DateFormatterConstant {

	public static final DateTimeFormatter DATE_TIME_WITH_AM_OR_PM = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");
	public static final String DEFAULT_UTIL_DATE_FORMAT = "E MMM dd HH:mm:ss z yyyy";
	public static final String DD_MMM_YYYY = "dd-MMM-yyyy";
	public static final String YYYY_MM_DD ="yyyy-MM-dd";
	public static final String DATE_TIME_WITH_AM_AND_PM = "dd-MMM-yyyy hh:mm a";
	public static final SimpleDateFormat TIME_24_HRS = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat TIME_12_HRS = new SimpleDateFormat("hh:mm a");
	public static final String START_TIME = "00:00";
	public static final String END_TIME = "23:59";
	
	

}
