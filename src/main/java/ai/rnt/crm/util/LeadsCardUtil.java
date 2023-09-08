package ai.rnt.crm.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ai.rnt.crm.exception.CRMException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikhil Gaikwad
 * @version 1.0
 * @since 08/09/2023.
 *
 */
@Slf4j
public class LeadsCardUtil {

	private LeadsCardUtil() {

	}

	public static String shortName(String fName, String lName) {
		String result = null;
		try {
			Pattern pattern = Pattern.compile("^.");
			Matcher firstNameMatcher = pattern.matcher(fName);
			Matcher lastNameMatcher = pattern.matcher(lName);
			if (firstNameMatcher.find() && lastNameMatcher.find()) {
				result = firstNameMatcher.group() + lastNameMatcher.group();
			}
		} catch (Exception e) {
			log.error("Got exception while concating the fname and lname");
			throw new CRMException(e);
		}
		return result.toUpperCase();
	}
}
