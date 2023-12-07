package ai.rnt.crm.functional.predicates;

import static ai.rnt.crm.constants.DateFormatterConstant.DATE_TIME_WITH_AM_OR_PM;
import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.parse;
import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class UpnextPredicate {

	/*
	 * * This Predicate return true if it the date is within 4 days.
	 * 
	 * @since version 1.0
	 */
	public static final Predicate<String> UPNEXT = s -> {
		if (isNull(s))
			return false;
		else {
			try {
				LocalDateTime now = now();
				LocalDateTime parse = parse(s, DATE_TIME_WITH_AM_OR_PM);
				return (DAYS.between(now, parse) >= 0 && DAYS.between(now, parse) <= 4)
						&& MINUTES.between(now, parse) >= 0;
			} catch (Exception e) {
				return false;
			}
		}
	};

}
