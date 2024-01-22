package ai.rnt.crm.util;

import static ai.rnt.crm.constants.RegexConstant.IS_AMOUNT;
import static ai.rnt.crm.constants.RegexConstant.IS_EMAIL;
import static ai.rnt.crm.constants.RegexConstant.IS_FNAME_LNAME;
import static ai.rnt.crm.constants.RegexConstant.IS_PHONE_NUMBER;
import static ai.rnt.crm.constants.RegexConstant.IS_STRING;
import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Sanket Wakankar
 * @since 22/01/2024
 * @version 1.0
 *
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ExcelFieldValidationUtil {

	public static boolean isValidEmail(String email) {
		log.info("inside the isValidEmail method...{}", email);
		return nonNull(email) && compile(IS_EMAIL).matcher(email).matches();
	}

	public static boolean isValidFnameLname(String name) {
		log.info("inside the isValidFnameLname method...{}", name);
		return nonNull(name) && compile(IS_FNAME_LNAME).matcher(name).matches();
	}

	public static boolean isValidString(String name) {
		log.info("inside the isValidString method...{}", name);
		return nonNull(name) && compile(IS_STRING).matcher(name).matches();
	}

	public static boolean isValidPhoneNumber(String number) {
		log.info("inside the isValidPhoneNumber method...{}", number);
		return nonNull(number) && compile(IS_PHONE_NUMBER).matcher(number).matches();
	}

	public static boolean isValidDesignation(String designation) {
		log.info("inside the isValidDesignation method...{}", designation);
		return isValidString(designation);
	}

	public static boolean isValidServiceFalls(String serviceFalls) {
		log.info("inside the isValidServiceFalls method...{}", serviceFalls);
		return isValidString(serviceFalls);
	}

	public static boolean isValidBudgetAmount(String amount) {
		log.info("inside the isValidBudgetAmount method...{}", amount);
		return nonNull(amount) && compile(IS_AMOUNT).matcher(amount).matches();
	}
}
