package ai.rnt.crm.security.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;

class CsrfRequireMatcherTest {

	@Test
    void matchesShouldReturnFalseForGetRequests() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/api/v1/auth/test");
        when(request.getMethod()).thenReturn("GET");

        CsrfRequireMatcher matcher = new CsrfRequireMatcher();
        assertFalse(matcher.matches(request));
    }

    @Test
    void matchesShouldReturnTrueForPostRequestsOnNonAllowUrl() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getServletPath()).thenReturn("/api/v1/auth/login");
        when(request.getMethod()).thenReturn("POST");

        CsrfRequireMatcher matcher = new CsrfRequireMatcher();
        assertFalse(matcher.matches(request));
    }
    @Test
    void matchesShouldReturnTrueForPostRequestsOnNonAllowUrl1() {
    	HttpServletRequest request = mock(HttpServletRequest.class);
    	when(request.getServletPath()).thenReturn("/api/v1/auth/test");
    	when(request.getMethod()).thenReturn("POST");
    	
    	CsrfRequireMatcher matcher = new CsrfRequireMatcher();
    	assertTrue(matcher.matches(request));
    }
}
