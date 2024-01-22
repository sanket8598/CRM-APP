package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.util.function.Predicate;

import lombok.NoArgsConstructor;
@NoArgsConstructor(access = PRIVATE)
public class OverDueActivity {
	
	/*
	 * * This Predicate return true if it the due date is before the current datetime.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> OVER_DUE = s -> {
		if (isNull(s))
			return false;
		else {
			try {
				return parse(s, DATE_TIME_WITH_AM_OR_PM).isBefore(now());
			} catch (Exception e) {
				return false;
			}
		}
	};


}
