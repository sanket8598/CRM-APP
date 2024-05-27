package ai.rnt.crm.validation.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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

import ai.rnt.crm.validation.ValidDueAndRemainderDateTime;

@ExtendWith(MockitoExtension.class)
class DueAndRemainderDateTimeValidatorTest {

	private DueAndRemainderDateTimeValidator validator;

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
		validator = new DueAndRemainderDateTimeValidator();
		validator.timefieldOne = "dueTime";
		validator.timefieldSec = "remainderTime";
		validator.dateFieldOne = "dueDate";
		validator.dateFieldSec = "remainderDate";
		validator.remainderField = "remainderOn";
	}

	private static class TestObject {
		private String dueTime;
		private LocalDate dueDate;
		private boolean remainderOn;
		private String remainderTime;
		private LocalDate remainderDate;

		public TestObject(String dueTime, LocalDate dueDate, boolean remainderOn, String remainderTime,
				LocalDate remainderDate) {
			this.dueTime = dueTime;
			this.dueDate = dueDate;
			this.remainderOn = remainderOn;
			this.remainderTime = remainderTime;
			this.remainderDate = remainderDate;
		}

		public String getDueTime() {
			return dueTime;
		}

		public LocalDate getDueDate() {
			return dueDate;
		}

		public boolean isRemainderOn() {
			return remainderOn;
		}

		public String getRemainderTime() {
			return remainderTime;
		}

		public LocalDate getRemainderDate() {
			return remainderDate;
		}
	}

	@Test
    void testInitialize_ShouldSetFieldsCorrectly() {
        ValidDueAndRemainderDateTime annotation = mock(ValidDueAndRemainderDateTime.class);
        when(annotation.timefieldOne()).thenReturn("dueTime");
        when(annotation.timefieldSec()).thenReturn("remainderTime");
        when(annotation.dateFieldOne()).thenReturn("dueDate");
        when(annotation.dateFieldSec()).thenReturn("remainderDate");
        when(annotation.remainderField()).thenReturn("remainderOn");

        validator.initialize(annotation);

        assertEquals("dueTime", validator.timefieldOne);
        assertEquals("remainderTime", validator.timefieldSec);
        assertEquals("dueDate", validator.dateFieldOne);
        assertEquals("remainderDate", validator.dateFieldSec);
        assertEquals("remainderOn", validator.remainderField);
    }
	
	@Test
	void testIsValid_WhenDueDateTimeIsInFuture_AndNoRemainder_ShouldReturnTrue() {
		TestObject testObject = new TestObject("12:00", LocalDate.now().plusDays(1), false, null, null);
		boolean result = validator.isValid(testObject, context);
		assertTrue(result);
	}

	@Test
	    void testIsValid_WhenDueDateTimeIsInPast_ShouldReturnFalse() {
	        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
	        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
	        TestObject testObject = new TestObject("12:00", LocalDate.now().minusDays(1), false, null, null);
	        boolean result = validator.isValid(testObject, context);
	        assertFalse(result);
	        verify(context).buildConstraintViolationWithTemplate(messageCaptor.capture());
	        assertTrue(messageCaptor.getValue().contains("Due Date & Time must not be smaller than current date & time!!"));
	    }

	@Test
	    void testIsValid_WhenRemainderDateTimeIsBeforeCurrent_ShouldReturnFalse() {
	        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
	        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
	        TestObject testObject = new TestObject("12:00", LocalDate.now().plusDays(1), true, "10:00", LocalDate.now().minusDays(1));
	        boolean result = validator.isValid(testObject, context);
	        assertFalse(result);
	        verify(context).buildConstraintViolationWithTemplate(messageCaptor.capture());
	        assertTrue(messageCaptor.getValue().contains("Remainder Date & Time must not be smaller than current Date & Time!!"));
	    }

	@Test
	    void testIsValid_WhenRemainderDateTimeIsBeforeDueDateTime_ShouldReturnFalse1() {
	        when(context.buildConstraintViolationWithTemplate(anyString())).thenReturn(violationBuilder);
	        when(violationBuilder.addPropertyNode(anyString())).thenReturn(nodeBuilder);
	        when(nodeBuilder.addConstraintViolation()).thenReturn(context);
	        TestObject testObject = new TestObject("12:00", LocalDate.now().plusDays(1), true, "10:00", LocalDate.now());
	        boolean result = validator.isValid(testObject, context);
	        assertFalse(result);
	        verify(context).buildConstraintViolationWithTemplate(messageCaptor.capture());
	        String capturedMessage = messageCaptor.getValue();
	        assertTrue(capturedMessage.contains("Remainder Date & Time must not be smaller than current Date & Time!!"));
	    }

	@Test
	void testIsValid_WhenRemainderDateTimeIsAfterDueDateTime_ShouldReturnTrue() {
		TestObject testObject = new TestObject("12:00", LocalDate.now().plusDays(1), true, "14:00",
				LocalDate.now().plusDays(1));
		boolean result = validator.isValid(testObject, context);
		assertTrue(result);
	}
}
