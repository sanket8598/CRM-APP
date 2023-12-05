package ai.rnt.crm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import ai.rnt.crm.exception.CRMException;
import lombok.extern.slf4j.Slf4j;


/**
 * This class is used to get the required formattedDates through methods and functions.
 * 
 * @author Sanket Wakankar
 * @version 1.0
 * @since 05-12-2023
 */

@Slf4j
public class ConvertDateFormatUtil {

	private ConvertDateFormatUtil() {

	}

	/**
	 * @author Nikhil Gaikwad
	 * @version 1.0
	 * @since 06/09/2023.
	 * @param toConvertDate
	 * @return
	 */

	public static String convertDate(LocalDateTime toConvertDate) {
		try {
			return new SimpleDateFormat("dd-MMM-yyyy hh:mm a").format(new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy")
					.parse(Date.from(toConvertDate.atZone(ZoneId.systemDefault()).toInstant()).toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format:", e.getMessage());
			throw new CRMException("error while date conversion");
		}
	}

	public static String convertDateUtilDate(Date date) throws ParseException {
		try {
			return new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").parse(date.toString()));
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateUtilDate:", e.getMessage());
			throw new CRMException("error while date conversion");
		}
	}
	
	/**
	 * This method is used to convert the given date(yyyy-MM-dd) to the format (dd-MMM-yyyy) with give String time added to it.
	 *  e.g 2023-12-04 is converted to the  04 Dec 2023 and also time is added.
	 * @author Sanket Wakankar
	 * @version 1.0
	 * @since 05/12/2023.
	 * @param date in java.util.Date,Time in String
	 * @return String
	 */
	public static String convertDateDateWithTime(Date date,String endTime) {
		try {
			return new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(date.toString()))+" "+endTime;
		} catch (Exception e) {
			log.error("Got Excetion while converting date format in convertDateDateWithTime:", e.getMessage());
			throw new CRMException("error while date conversion");
		}
	}
	
}
