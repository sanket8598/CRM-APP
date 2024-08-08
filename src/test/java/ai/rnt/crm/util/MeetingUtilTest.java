package ai.rnt.crm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.dto.MeetingDto;

class MeetingUtilTest {

	@Autowired
	MockMvc mockMvc;

	@Mock
	private PropertyUtil propertyUtil;

	@Mock
	private Transport transport;

	@InjectMocks
	private MeetingUtil meetingUtil;

	@Mock
	private Message message;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(meetingUtil).build();
	}

	@Test
	void testParseTimeValidInput() {
		String validTime = "10:30";
		LocalTime parsedTime = meetingUtil.parseTime(validTime);
		assertEquals(LocalTime.of(10, 30), parsedTime);
	}

	@Test
	void testFormatDateTimeValidInput() throws ParseException {
		String inputDateStr = "2024-03-07";
		Date inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(inputDateStr);
		String inputTime = "10:30";
		String formattedDateTime = meetingUtil.formatDateTime(inputDate, inputTime);
		assertEquals("20240307T103000", formattedDateTime);
	}

	@Test
	void testFormatDateValidInput() throws ParseException {
		LocalDate currentDate = LocalDate.now();
		//LocalDate previousDate = currentDate.minusDays(1);
		String expectedDateStr = currentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		String formattedDate = meetingUtil
				.formatDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		assertEquals(expectedDateStr, formattedDate);
	}

	@Test
	void testExtractDstartDend() throws ParseException {
		MeetingDto dto = mock(MeetingDto.class);
		when(dto.isAllDay()).thenReturn(false);
		when(dto.getStartDate()).thenReturn(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-20"));
		when(dto.getEndDate()).thenReturn(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-21"));
		when(dto.getStartTime()).thenReturn("10:00");
		when(dto.getEndTime()).thenReturn("12:00");

		String result = meetingUtil.extractDstartDend(dto);
		assertFalse(result.contains("DTSTART;VALUE=DATE:2024-03-20"));
		assertFalse(result.contains("DTEND;VALUE=DATE:2024-03-21"));
	}

	@Test
	void testExtractDstartDendAllDay() throws ParseException {
		MeetingDto dto = mock(MeetingDto.class);
		when(dto.isAllDay()).thenReturn(true);
		when(dto.getStartDate()).thenReturn(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-20"));
		when(dto.getEndDate()).thenReturn(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-21"));
		String result = meetingUtil.extractDstartDend(dto);
		assertFalse(result.contains("DTSTART;VALUE=DATE:2024-03-20"));
		assertFalse(result.contains("DTEND;VALUE=DATE:2024-03-21"));
	}

	@Test
	void testGetBufferString() throws ParseException {
		// Create a MeetingDto object with sample data
		MeetingDto dto = new MeetingDto();
		dto.setStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-20"));
		dto.setEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2024-03-20"));
		dto.setDescription("Meeting Description");
		dto.setMeetingTitle("Meeting Title");
		dto.setStartTime("10:10");
		dto.setEndTime("11:10");
		dto.setParticipates(Collections.singletonList("participant1@example.com"));

		MeetingUtil meetingUtil = new MeetingUtil(null);
		String result = meetingUtil.getBufferString(dto);

		assertTrue(result.contains("BEGIN:VCALENDAR"));
		assertTrue(result.contains("PRODID: Asset View 2.0"));
		assertTrue(result.contains("VERSION:2.0"));
		assertTrue(result.contains("METHOD:REQUEST"));
		assertTrue(result.contains("BEGIN:VEVENT"));
		assertFalse(result.contains("DTSTAMP:2024-03-20"));
		assertFalse(result.contains("DTSTART:2024-03-20"));
		assertFalse(result.contains("DTEND:2024-03-20"));
		assertTrue(result.contains("DESCRIPTION:Meeting Description"));
		assertTrue(result.contains("SUMMARY:Meeting Title"));
		assertFalse(result.contains("ORGANIZER:MAILTO:" + "nik.gaikwad@rnt.ai"));
		assertTrue(result.contains("ATTENDEE;CN=participant1@example.com;RSVP=TRUE:mailto:"));
		assertTrue(result.contains("X-MICROSOFT-CDO-BUSYSTATUS:FREE"));
		assertTrue(result.contains("BEGIN:VALARM"));
		assertTrue(result.contains("ACTION:DISPLAY"));
		assertTrue(result.contains("DESCRIPTION:Reminder"));
		assertTrue(result.contains("TRIGGER:-P1D"));
		assertTrue(result.contains("END:VALARM"));
		assertTrue(result.contains("END:VEVENT"));
		assertTrue(result.contains("END:VCALENDAR"));
	}
}
