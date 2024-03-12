package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;

class HttpUtilsTest {

	@Test
	void testGetURLWithoutQueryString() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURL()).thenReturn(new StringBuffer("https://example.com/test"));
		assertEquals("https://example.com/test", HttpUtils.getURL(request));
	}

	@Test
	void testGetURLWithQueryString() {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getRequestURL()).thenReturn(new StringBuffer("https://example.com/test"));
		when(request.getQueryString()).thenReturn("param1=value1&param2=value2");
		assertEquals("https://example.com/test?param1=value1&param2=value2", HttpUtils.getURL(request));
	}

	@Test
	void testGetURLWithNullRequest() {
		assertEquals(null, HttpUtils.getURL(null));
	}
}
