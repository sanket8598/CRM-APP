package ai.rnt.crm.util;

import static java.util.Objects.nonNull;
import static java.util.regex.Pattern.compile;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ExcelFieldValidationUtil {

	public static boolean isValidEmail(String email) {
		log.info("inside the isValidEmail method...{}", email);
		return nonNull(email) && compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
				.matcher(email).matches();
	}

	public static boolean isValidFnameLname(String name) {
		log.info("inside the isValidFnameLname method...{}", name);
		return nonNull(name) && compile("^[a-zA-Z]+$").matcher(name).matches();
	}

	public static boolean isValidString(String name) {
		log.info("inside the isValidString method...{}", name);
		return nonNull(name) && compile("^[a-zA-Z\\s]+$").matcher(name).matches();
	}

	public static boolean isValidPhoneNumber(String number) {
		log.info("inside the isValidPhoneNumber method...{}", number);
		return nonNull(number) && compile("^\\+?\\d{11,13}$").matcher(number).matches();
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
		return nonNull(amount) && compile("^\\d+(?:\\.\\d+)?$").matcher(amount).matches();
	}
}
