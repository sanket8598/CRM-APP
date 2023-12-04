package ai.rnt.crm.util;

import static java.util.Objects.nonNull;

import java.util.regex.Pattern;

public class ExcelFieldValidationUtil {

	ExcelFieldValidationUtil() {
	}

	public static boolean isValidEmail(String email) {
		return nonNull(email)
				&& Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$")
						.matcher(email).matches();
	}

	public static boolean isValidFnameLname(String name) {
		return nonNull(name) && Pattern.compile("^[a-zA-Z]+$").matcher(name).matches();
	}

	public static boolean isValidString(String name) {
		return nonNull(name) && Pattern.compile("^[a-zA-Z\\s]+$").matcher(name).matches();
	}

	public static boolean isValidPhoneNumber(String number) {
		return nonNull(number) && Pattern.compile("^\\+?\\d{10,14}$").matcher(number).matches();
	}

	public static boolean isValidDesignation(String designation) {
		return isValidString(designation);
	}

	public static boolean isValidServiceFalls(String serviceFalls) {
		return isValidString(serviceFalls);
	}

	public static boolean isValidBudgetAmount(String amount) {
		return nonNull(amount) && Pattern.compile("[0-9]+").matcher(amount).matches();
	}
}
