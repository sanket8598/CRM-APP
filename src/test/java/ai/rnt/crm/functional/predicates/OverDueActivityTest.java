package ai.rnt.crm.functional.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

class OverDueActivityTest {

	private static final String DATE_TIME_WITH_AM_OR_PM = "yyyy-MM-dd hh:mm:ss a";
	private static final String DDMMYYYY = "dd-MMM-yyyy hh:mm a";

    @Test
    void testOverDueWithNullString() {
        assertFalse(OverDueActivity.OVER_DUE.test(null),
                "Predicate should return false for null input.");
    }

    @Test
    void testOverDueWithInvalidDateString() {
        assertFalse(OverDueActivity.OVER_DUE.test("Invalid date"),
                "Predicate should return false for invalid date string.");
    }

    @Test
    void testOverDueWithFutureDate() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
        String futureDateString = futureDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_WITH_AM_OR_PM));
        assertFalse(OverDueActivity.OVER_DUE.test(futureDateString),
                "Predicate should return false for future date.");
    }

    @Test
    void testOverDueWithPastDate() {
        LocalDateTime pastDateTime = LocalDateTime.now().minusDays(1);
        String pastDateString = pastDateTime.format(DateTimeFormatter.ofPattern(DDMMYYYY));
        assertTrue(OverDueActivity.OVER_DUE.test(pastDateString),
                "Predicate should return true for past date.");
    }

    @Test
    void testOverDueWithCurrentDate() {
        String currentDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_WITH_AM_OR_PM));
        assertFalse(OverDueActivity.OVER_DUE.test(currentDateString),
                "Predicate should return false for current date.");
    }
}
