package ai.rnt.crm.util;

import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelFiledValidationUtil {

	ExcelFiledValidationUtil() {

	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
		Pattern pattern = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pattern.matcher(email).matches();
	}

	public static boolean isValidFnameLname(String name) {
		String stringRegex = "^[a-zA-Z]+$";
		Pattern pattern = Pattern.compile(stringRegex);
		if (name == null)
			return false;
		return pattern.matcher(name).matches();
	}

	public static boolean isValidPhoneNumber(String number) {
		String numberRegex = "^\\+?\\d{10,14}$";
		Pattern pattern = Pattern.compile(numberRegex);
		if (number == null)
			return false;
		return pattern.matcher(number).matches();
	}

	public static boolean isValidDesignation(String designation) {
		return isValidFnameLname(designation);
	}

	public static boolean isValidServiceFalls(String serviceFalls) {
		return isValidFnameLname(serviceFalls);
	}

	public static boolean isValidBudgetAmount(String amount) {
		String amountRegex = "^[\\d,]+$";
		Pattern pattern = Pattern.compile(amountRegex);
		if (amount == null)
			return false;
		return pattern.matcher(amount).matches();
	}
}
