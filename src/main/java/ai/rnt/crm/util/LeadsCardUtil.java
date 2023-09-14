package ai.rnt.crm.util;

import java.util.Objects;
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
		try {
			Pattern pattern = Pattern.compile("^.");
			Matcher firstNameMatcher = pattern.matcher(fName);
			Matcher lastNameMatcher = pattern.matcher(lName);
			if (firstNameMatcher.find() && lastNameMatcher.find())
				return (firstNameMatcher.group() + lastNameMatcher.group()).toUpperCase();
			return null;
		} catch (Exception e) {
			log.error("Got exception while concating the fname and lname");
			throw new CRMException(e);
		}
	}

	public static String shortName(String fullName) {
		try {
			if (Objects.nonNull(fullName) && fullName.trim().contains(" ")) {
				String[] result = fullName.split(" ");
				Pattern pattern = Pattern.compile("^.");
				Matcher firstNameMatcher = pattern.matcher(result[0]);
				Matcher lastNameMatcher = pattern.matcher(result[1]);
				if (firstNameMatcher.find() && lastNameMatcher.find())
					return (firstNameMatcher.group() + lastNameMatcher.group()).toUpperCase();
			}
			return null;
		} catch (Exception e) {
			log.error("Got exception while concating the fname and lname");
			throw new CRMException(e);
		}

	}
}
