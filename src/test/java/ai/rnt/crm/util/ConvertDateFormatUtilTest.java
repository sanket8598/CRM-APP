package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.exception.CRMException;

class ConvertDateFormatUtilTest {

	@Test
	void testConvertDate() throws ParseException {
		LocalDateTime dateTime = LocalDateTime.of(2023, 9, 6, 10, 15, 30);
		String expectedDateTimeString = "06-Sep-2023 10:15 AM";
		assertEquals(expectedDateTimeString.toLowerCase(), ConvertDateFormatUtil.convertDate(dateTime).toLowerCase());
	}

	@Test
	void testConvertDateUtilDate() throws ParseException {
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		String expectedDateString = currentDate.format(formatter);
		Date date = new Date();
		assertEquals(expectedDateString, ConvertDateFormatUtil.convertDateUtilDate(date));
	}

	@Test
	void testConvertDateDateWithTime_Success() throws ParseException {
		Date date = mock(Date.class);
		String endTime = "12:00 PM";
		when(date.toString()).thenReturn("2023-12-05");
		String expectedDateString = "05-Dec-2023 12:00 PM";
		String actualDateString = ConvertDateFormatUtil.convertDateDateWithTime(date, endTime);
		assertEquals(expectedDateString, actualDateString);
	}

	@Test
	void testConvertDateDateWithTime_NullDate() {
		Date date = null;
		String endTime = "12:00 PM";
		ConvertDateFormatUtil.convertDateDateWithTime(date, endTime);
	}

	@Test
	void testConvertDateDateWithTime_Exception() {
		Date date = mock(Date.class);
		String endTime = "12:00 PM";
		when(date.toString()).thenReturn("Invalid Date");
		assertThrows(CRMException.class, () -> ConvertDateFormatUtil.convertDateDateWithTime(date, endTime));
	}

	@Test
	void testConvertDateDateWithTime_Success1() throws ParseException {
		LocalDate date = LocalDate.parse("2023-12-05");
		String endTime = "12:00 PM";
		String expectedDateString = "05-Dec-2023 12:00 PM";
		String actualDateString = ConvertDateFormatUtil.convertDateDateWithTime(date, endTime);
		assertEquals(expectedDateString, actualDateString);
	}

	@Test
	void testConvertDateDateWithTime_NullDate1() {
		LocalDate date = null;
		String endTime = "12:00 PM";
		ConvertDateFormatUtil.convertDateDateWithTime(date, endTime);
	}

	@Test
	void testConvertLocalDate_Success() throws ParseException {
		LocalDate date = LocalDate.parse("2023-12-05");
		String expectedDateString = "05-Dec-2023";
		String actualDateString = ConvertDateFormatUtil.convertLocalDate(date);
		assertEquals(expectedDateString, actualDateString);
	}

	@Test
	void testConvertLocalDate_NullDate() {
		LocalDate date = null;
		ConvertDateFormatUtil.convertLocalDate(date);
	}
}
