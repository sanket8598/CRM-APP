package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class MessageConstants {

	public static final String BAD_CREDENTIALS = "Your Credentails Are Not Valid !!";
	public static final String ACCESS_DENIED = "You Don't Have The Access !!";
	public static final String TOKEN_EXPIRED = "JWT Token is Expired !!";
	public static final String ERROR_MSG = "Something unexpected happened !!";
	public static final String WAIT_FOR = "Wait for %d day(s)";
	public static final String NO_ACTIVITY = "No Upcoming Activities";
	public static final String SOON_MORE = "Soon More Activities";
	public static final String MSG = "Message";

}
