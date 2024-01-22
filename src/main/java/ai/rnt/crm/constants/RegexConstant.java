package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * @author Sanket Wakankar
 * @since 22/01/2024
 * @version 1.0
 *
 */
@NoArgsConstructor(access = PRIVATE)
public final class RegexConstant {

	public static final String IS_DIGIT = "^\\d+$";

	public static final String IS_EMAIL = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

	public static final String IS_FNAME_LNAME = "^[a-zA-Z]+$";

	public static final String IS_STRING = "^[a-zA-Z\\s]+$";

	public static final String IS_PHONE_NUMBER = "^\\+?\\d{10,13}$";

	public static final String IS_AMOUNT = "^\\d+(?:\\.\\d+)?$";
}
