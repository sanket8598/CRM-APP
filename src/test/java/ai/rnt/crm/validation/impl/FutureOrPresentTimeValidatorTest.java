package ai.rnt.crm.validation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder.NodeBuilderCustomizableContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ai.rnt.crm.validation.FutureOrPresentTime;

@ExtendWith(MockitoExtension.class)
class FutureOrPresentTimeValidatorTest {

	private FutureOrPresentTimeValidator validator;

	@Mock
	private ConstraintValidatorContext context;

	@Mock
	private ConstraintViolationBuilder violationBuilder;

	@Mock
	private NodeBuilderCustomizableContext nodeBuilder;

	@Captor
	private ArgumentCaptor<String> messageCaptor;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		validator = new FutureOrPresentTimeValidator();
		validator.timefieldOne = "startTime";
		validator.timefieldSec = "endTime";
		validator.dateFieldOne = "startDate";
		validator.dateFieldSec = "endDate";
	}

	private static class TestObject {
		private String startTime;
		private String endTime;
		private Date startDate;
		private Date endDate;

		public TestObject(String startTime, String endTime, Date startDate, Date endDate) {
			this.startTime = startTime;
			this.endTime = endTime;
			this.startDate = startDate;
			this.endDate = endDate;
		}

		public String getStartTime() {
			return startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public Date getStartDate() {
			return startDate;
		}

		public Date getEndDate() {
			return endDate;
		}
	}

	@Test
    void testInitialize_ShouldSetFieldsCorrectly() {
        FutureOrPresentTime annotation = mock(FutureOrPresentTime.class);
        when(annotation.timefieldOne()).thenReturn("startTime");
        when(annotation.timefieldSec()).thenReturn("endTime");
        when(annotation.dateFieldOne()).thenReturn("startDate");
        when(annotation.dateFieldSec()).thenReturn("endDate");

        validator.initialize(annotation);

        assertEquals("startTime", validator.timefieldOne);
        assertEquals("endTime", validator.timefieldSec);
        assertEquals("startDate", validator.dateFieldOne);
        assertEquals("endDate", validator.dateFieldSec);
    }
	
	@Test
	void testIsValid_WhenStartDateTimeIsInFuture_AndEndDateTimeIsInFuture_ShouldReturnTrue() {
		TestObject testObject = new TestObject("12:00", "14:00", getFutureDate(), getFutureDate());
		boolean result = validator.isValid(testObject, context);
		assertTrue(result);
	}

	@Test
    void testIsValid_WhenStartDateTimeIsInPast_ShouldReturnFalse() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
        TestObject testObject = new TestObject("12:00", "14:00", getPastDate(), getFutureDate());
        boolean result = validator.isValid(testObject, context);
        assertFalse(result);
        verify(context).buildConstraintViolationWithTemplate(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains("Start Date & Time must not be smaller than today's date!!"));
    }

	@Test
    void testIsValid_WhenEndDateTimeIsInPast_ShouldReturnFalse() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
        TestObject testObject = new TestObject("12:00", "14:00", getFutureDate(), getPastDate());
        boolean result = validator.isValid(testObject, context);
        assertFalse(result);
        verify(context).buildConstraintViolationWithTemplate(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains("End Date & Time must not be smaller than today's Date & Time!!"));
    }

	@Test
    void testIsValid_WhenEndDateTimeIsBeforeStartDateTime_ShouldReturnFalse() {
        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
        TestObject testObject = new TestObject("12:00", "10:00", getFutureDate(), getFutureDate());
        boolean result = validator.isValid(testObject, context);
        assertFalse(result);
        verify(context).buildConstraintViolationWithTemplate(messageCaptor.capture());
        assertTrue(messageCaptor.getValue().contains("End Date and Time must not be smaller than Start Date and Time!!"));
    }

	private Date getFutureDate() {
		ZonedDateTime futureDateTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).plusDays(1);
		return Date.from(futureDateTime.toInstant());
	}

	private Date getPastDate() {
		ZonedDateTime pastDateTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata")).minusDays(1);
		return Date.from(pastDateTime.toInstant());
	}
}
