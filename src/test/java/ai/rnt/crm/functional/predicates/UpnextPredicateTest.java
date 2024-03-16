package ai.rnt.crm.functional.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class UpnextPredicateTest {

	private static final String DATE_TIME_WITH_AM_OR_PM = "yyyy-MM-dd hh:mm:ss a";
	private static final String DDMMYYYY = "dd-MMM-yyyy hh:mm a";

    @Test
    void testUpnextWithNullString() {
        assertFalse(UpnextPredicate.UPNEXT.test(null),
                "Predicate should return false for null input.");
    }

    @Test
    void testUpnextWithInvalidDateString() {
        assertFalse(UpnextPredicate.UPNEXT.test("Invalid date"),
                "Predicate should return false for invalid date string.");
    }

    @Test
    void testUpnextWithFutureDateWithin4Days() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(3);
        String futureDateString = futureDateTime.format(DateTimeFormatter.ofPattern(DDMMYYYY));
        assertTrue(UpnextPredicate.UPNEXT.test(futureDateString),
                "Predicate should return true for future date within 4 days.");
    }

    @Test
    void testUpnextWithFutureDateBeyond4Days() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(5);
        String futureDateString = futureDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_WITH_AM_OR_PM));
        assertFalse(UpnextPredicate.UPNEXT.test(futureDateString),
                "Predicate should return false for future date beyond 4 days.");
    }

    @Test
    void testUpnextWithPastDate() {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        String pastDateString = pastDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_WITH_AM_OR_PM));
        assertFalse(UpnextPredicate.UPNEXT.test(pastDateString),
                "Predicate should return false for past date.");
    }
}
