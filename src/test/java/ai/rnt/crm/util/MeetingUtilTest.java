package ai.rnt.crm.util;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;

class MeetingUtilTest {

	 @Mock
	    private Session session;
	 
	 @Mock
	    private Transport transport;

	 @InjectMocks
	    private MeetingUtil meetingUtil;

	    @Mock
	    private Message message;

	    @BeforeEach
	    void setUp() {
	        Logger logger = mock(Logger.class);
	        meetingUtil = new MeetingUtil();
	    }

	    @Test
	    void testParseTime_ValidInput() {
	        String validTime = "10:30";
	        LocalTime parsedTime = meetingUtil.parseTime(validTime);
	        assertEquals(LocalTime.of(10, 30), parsedTime);
	    }
	    
	    @Test
	    void testFormatDateTime_ValidInput() throws ParseException {
	        String inputDateStr = "2024-03-07";
	        Date inputDate = new SimpleDateFormat("yyyy-MM-dd").parse(inputDateStr);
	        String inputTime = "10:30";
	        String formattedDateTime = meetingUtil.formatDateTime(inputDate, inputTime);
	        assertEquals("20240307T103000", formattedDateTime);
	    }
	    
	    @Test
	    void testFormatDate_ValidInput() throws ParseException {
	    	LocalDate currentDate = LocalDate.now();
	    	LocalDate previousDate = currentDate.minusDays(1);
	        String expectedDateStr = previousDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
	        String formattedDate = meetingUtil.formatDate(Date.from(currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
	        assertEquals(expectedDateStr, formattedDate);
	    }
}

