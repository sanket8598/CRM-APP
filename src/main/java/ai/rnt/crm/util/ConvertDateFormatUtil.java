package ai.rnt.crm.util;

import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_AND_PM;
import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
import static ai.rnt.crm.constants.DateFormatterConstant.DD_MMM_YYYY;
import static ai.rnt.crm.constants.DateFormatterConstant.DEFAULT_UTIL_DATE_FORMAT;
import static ai.rnt.crm.constants.DateFormatterConstant.DEFAULT_UTIL_DATE_FORMAT_VALIDATION;
import static ai.rnt.crm.constants.DateFormatterConstant.TIME_12_HRS;
import static ai.rnt.crm.constants.DateFormatterConstant.TIME_24_HRS;
import static ai.rnt.crm.constants.DateFormatterConstant.YYYY_MM_DD;
import static java.time.LocalDateTime.parse;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import ai.rnt.crm.exception.CRMException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to get the required formattedDates through methods and
 * functions.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 05-12-2023
 */

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ConvertDateFormatUtil {

	/**
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 06/09/2023.
	 * @param toConvertDate
	 * @return
	 */
	private static final ThreadLocal<SimpleDateFormat> DEFAULT_UTIL_DATE_FORMAT_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(DEFAULT_UTIL_DATE_FORMAT));

	private static final ThreadLocal<SimpleDateFormat> DEFAULT_UTIL_DATE_FORMAT_VALIDATION_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(DEFAULT_UTIL_DATE_FORMAT_VALIDATION));

	private static final ThreadLocal<SimpleDateFormat> DATE_TIME_WITH_AM_AND_PM_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(DATE_TIME_WITH_AM_AND_PM));

	private static final ThreadLocal<SimpleDateFormat> DD_MMM_YYYY_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(DD_MMM_YYYY));

	private static final ThreadLocal<SimpleDateFormat> YYYY_MM_DD_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(YYYY_MM_DD));

	private static final ThreadLocal<SimpleDateFormat> TIME_24_HRS_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(TIME_24_HRS));

	private static final ThreadLocal<SimpleDateFormat> TIME_12_HRS_THREAD_LOCAL = ThreadLocal
			.withInitial(() -> new SimpleDateFormat(TIME_12_HRS));

	public static String convertDate(LocalDateTime toConvertDate) {
		log.info("inside the convertDate method...{}", toConvertDate);
		try {
			return isNull(toConvertDate) ? null
					: DATE_TIME_WITH_AM_AND_PM_THREAD_LOCAL.get().format(DEFAULT_UTIL_DATE_FORMAT_THREAD_LOCAL.get()
							.parse(from(toConvertDate.atZone(systemDefault()).toInstant()).toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format:", e.getMessage());
			throw new CRMException("error while date conversion: " + toConvertDate);
		} finally {
			DEFAULT_UTIL_DATE_FORMAT_THREAD_LOCAL.remove();
			DATE_TIME_WITH_AM_AND_PM_THREAD_LOCAL.remove();
		}
	}

	public static String convertDateUtilDate(Date date) throws ParseException {
		log.info("inside the convertDateUtilDate method...{}", date);
		try {
			return isNull(date) ? null
					: DD_MMM_YYYY_THREAD_LOCAL.get()
							.format(DEFAULT_UTIL_DATE_FORMAT_THREAD_LOCAL.get().parse(date.toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateUtilDate:", e.getMessage());
			throw new CRMException("error while date convertDateUtilDate: " + date);
		} finally {
			DEFAULT_UTIL_DATE_FORMAT_THREAD_LOCAL.remove();
			DD_MMM_YYYY_THREAD_LOCAL.remove();
		}
	}

	/**
	 * This method is used to convert the given date(yyyy-MM-dd) to the format
	 * (dd-MMM-yyyy) with give String time added to it. e.g 2023-12-04 is converted
	 * to the 04 Dec 2023 and also time is added.
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 05/12/2023.
	 * @param date in java.util.Date,Time in String
	 * @return String
	 */
	public static String convertDateDateWithTime(Date date, String endTime) {
		log.info("inside the convertDateDateWithTime method...{} {}", date, endTime);
		try {
			return isNull(date) ? null
					: DD_MMM_YYYY_THREAD_LOCAL.get().format(YYYY_MM_DD_THREAD_LOCAL.get().parse(date.toString())) + " "
							+ endTime;
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateDateWithTime:", e.getMessage());
			throw new CRMException("error while date convertDateDateWithTime: " + date);
		} finally {
			YYYY_MM_DD_THREAD_LOCAL.remove();
			DD_MMM_YYYY_THREAD_LOCAL.remove();
		}
	}

	/**
	 * This method is used to convert the given date(EEE MMM dd HH:mm:ss zzz yyyy
	 * HH:mm) to the format (dd-MMM-yyyy) with give String time added to it. e.g
	 * 2023-12-04 is converted to the 04 Dec 2023 and also time is added.
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 05/12/2023.
	 * @param date in java.util.Date,Time in String
	 * @return String
	 */
	public static String convertDateDateWithTime(LocalDate date, String endTime) {
		log.info("inside the convertDateDateWithTime method...{} {}", date, endTime);
		try {
			return isNull(date) ? null
					: DD_MMM_YYYY_THREAD_LOCAL.get().format(YYYY_MM_DD_THREAD_LOCAL.get().parse(date.toString())) + " "
							+ endTime;
		} catch (Exception e) {
			log.error("Got Excetion while converting local date format in convertDateDateWithTime:", e.getMessage());
			throw new CRMException("error while date convertDateDateWithTime for local date: " + date);
		} finally {
			YYYY_MM_DD_THREAD_LOCAL.remove();
			DD_MMM_YYYY_THREAD_LOCAL.remove();
		}
	}

	/**
	 * This method is used to convert the given date(yyyy-MM-dd) to the format
	 * (dd-MMM-yyyy) with give String time added to it. e.g 2023-12-04 is converted
	 * to the 04 Dec 2023.
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 08-02-2024.
	 * @param date in java.util.LocalDate
	 * @return String
	 */
	public static String convertLocalDate(LocalDate date) {
		log.info("inside the convertLocalDate method...{} {}", date);
		try {
			return isNull(date) ? null
					: DD_MMM_YYYY_THREAD_LOCAL.get().format(YYYY_MM_DD_THREAD_LOCAL.get().parse(date.toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting local date format in convertLocalDate:", e.getMessage());
			throw new CRMException("error while date convertDateDateWithTime for local date: " + date);
		} finally {
			YYYY_MM_DD_THREAD_LOCAL.remove();
			DD_MMM_YYYY_THREAD_LOCAL.remove();
		}
	}

	/**
	 * This method is used to convert the given String time of 24 hrs format to the
	 * 12 hrs format
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 08-02-2024.
	 * @param time in String
	 * @return String
	 */
	public static String convertTimeTo12Hours(String time) {
		log.info("inside the convertTimeTo12Hours method...{} {}", time);
		try {
			return TIME_12_HRS_THREAD_LOCAL.get().format(TIME_24_HRS_THREAD_LOCAL.get().parse(time));
		} catch (Exception e) {
			return time;
		} finally {
			TIME_24_HRS_THREAD_LOCAL.remove();
			TIME_12_HRS_THREAD_LOCAL.remove();
		}
	}

	/**
	 * This method is used to convert the given date(yyyy-MM-dd) to the format
	 * (dd-MMM-yyyy) with give String time added to it. e.g 2023-12-04 is converted
	 * to the 04 Dec 2023 and also time is added.
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 05/12/2023.
	 * @param date in java.util.Date,Time in String
	 * @return String
	 */
	public static LocalDateTime convertDateDateWithTimeToLocalDate(Date date, String endTime) {
		log.info("inside the convertDateDateWithTimeToLocalDate method...{} {}", date, endTime);
		try {
			return isNull(date) ? null
					: parse(DATE_TIME_WITH_AM_AND_PM_THREAD_LOCAL.get().format(DEFAULT_UTIL_DATE_FORMAT_VALIDATION_THREAD_LOCAL.get()
							.parse(date.toString() + " " + endTime)),DATE_TIME_WITH_AM_OR_PM);
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateDateWithTimeToLocalDate:",
					e.getMessage());
			throw new CRMException("error while date convertDateDateWithTimeToLocalDate: " + date);
		} finally {
			DEFAULT_UTIL_DATE_FORMAT_VALIDATION_THREAD_LOCAL.remove();
			DATE_TIME_WITH_AM_AND_PM_THREAD_LOCAL.remove();
		}
	}

	/**
	 * This method is used to convert the given date(yyyy-MM-dd) to the format
	 * (dd-MMM-yyyy) with give String time added to it. e.g 2023-12-04 is converted
	 * to the 04 Dec 2023 and also time is added.
	 * 
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 05/12/2023.
	 * @param date in java.util.Date,Time in String
	 * @return String
	 */
	public static LocalDateTime convertLocalDateDateWithTimeToLocalDate(LocalDate date, String endTime) {
		log.info("inside the convertLocalDateDateWithTimeToLocalDate method...{} {}", date, endTime);
		try {
			return isNull(date) ? null
					: parse(convertDateDateWithTime(date,convertTimeTo12Hours(endTime)),DATE_TIME_WITH_AM_OR_PM);
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertLocalDateDateWithTimeToLocalDate:",
					e.getMessage());
			throw new CRMException("error while date convertLocalDateDateWithTimeToLocalDate: " + date);
		}
	}
	
}
