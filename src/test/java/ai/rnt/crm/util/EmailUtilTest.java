package ai.rnt.crm.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;

import org.junit.jupiter.api.Test;

import ai.rnt.crm.entity.Email;

class EmailUtilTest {

	private EmailUtil emailUtil;

	//@Test
	void testSendEmail_Success() throws AddressException, MessagingException {
		// Mocking the dependencies
		Session session = mock(Session.class);
		Message message = mock(Message.class);
	//	when(EmailUtil.getSession()).thenReturn(session);
		when(session.getTransport("smtp")).thenReturn(mock(Transport.class));
		doNothing().when(message).setFrom(any());
		doNothing().when(message).setRecipients(any(), any());
		doNothing().when(message).setSubject(any());
		doNothing().when(message).setSentDate(any());
		doNothing().when(message).setContent(any(), any());
		doNothing().when(session.getTransport("smtp")).send(any());

		// Creating an email instance
		Email email = new Email();
		email.setToMail("recipient@example.com");
		email.setSubject("Test Email");
		email.setContent("This is a test email.");

		// Calling the method under test
		boolean result = EmailUtil.sendEmail(email);

		// Verifying that the email was sent successfully
		assertTrue(result);
	}

	//@Test
	void testSendEmail_Failure() throws AddressException, MessagingException {
		// Mocking the dependencies
		Session session = mock(Session.class);
		Message message = mock(Message.class);
	//	when(EmailUtil.getSession()).thenReturn(session);
		when(session.getTransport("smtp")).thenReturn(mock(Transport.class));
		doNothing().when(message).setFrom(any());
		doNothing().when(message).setRecipients(any(), any());
		doNothing().when(message).setSubject(any());
		doNothing().when(message).setSentDate(any());
		doNothing().when(message).setContent(any(), any());
		doThrow(new MessagingException("Failed to send email")).when(session.getTransport("smtp")).send(any());

		// Creating an email instance
		Email email = new Email();
		email.setToMail("recipient@example.com");
		email.setSubject("Test Email");
		email.setContent("This is a test email.");

		// Calling the method under test
		boolean result = EmailUtil.sendEmail(email);

		// Verifying that the email sending failed
		assertFalse(result);
	}
}
