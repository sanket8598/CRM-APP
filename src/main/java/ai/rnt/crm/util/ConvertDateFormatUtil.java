package ai.rnt.crm.util;

import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_AND_PM;
import static ai.rnt.crm.constants.DateFormatterConstant.DD_MMM_YYYY;
import static ai.rnt.crm.constants.DateFormatterConstant.DEFAULT_UTIL_DATE_FORMAT;
import static ai.rnt.crm.constants.DateFormatterConstant.YYYY_MM_DD;
import static java.time.ZoneId.systemDefault;
import static java.util.Date.from;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.text.ParseException;
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

	public static String convertDate(LocalDateTime toConvertDate) {
		log.info("inside the convertDate method...{}", toConvertDate);
		try {
			return isNull(toConvertDate) ? null
					: DATE_TIME_WITH_AM_AND_PM.format(DEFAULT_UTIL_DATE_FORMAT
							.parse(from(toConvertDate.atZone(systemDefault()).toInstant()).toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format:", e.getMessage());
			throw new CRMException("error while date conversion: " + toConvertDate);
		}
	}

	public static String convertDateUtilDate(Date date) throws ParseException {
		log.info("inside the convertDateUtilDate method...{}", date);
		try {
			return isNull(date) ? null : DD_MMM_YYYY.format(DEFAULT_UTIL_DATE_FORMAT.parse(date.toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateUtilDate:", e.getMessage());
			throw new CRMException("error while date convertDateUtilDate: " + date);
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
			return isNull(date) ? null : DD_MMM_YYYY.format(YYYY_MM_DD.parse(date.toString())) + " " + endTime;
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateDateWithTime:", e.getMessage());
			throw new CRMException("error while date convertDateDateWithTime: " + date);
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
	public static String convertDateDateWithTime(LocalDate date, String endTime) {
		log.info("inside the convertDateDateWithTime method...{} {}", date, endTime);
		try {
			return isNull(date) ? null : DD_MMM_YYYY.format(YYYY_MM_DD.parse(date.toString())) + " " + endTime;
		} catch (Exception e) {
			log.error("Got Excetion while converting local date format in convertDateDateWithTime:", e.getMessage());
			throw new CRMException("error while date convertDateDateWithTime for local date: " + date);
		}
	}
}
