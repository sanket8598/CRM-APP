package ai.rnt.crm.security.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.AuthenticationException;

class JWTAuthenticationEntryPointTest {

	@Test
    void testCommence() throws IOException, ServletException {
        JWTAuthenticationEntryPoint entryPoint = new JWTAuthenticationEntryPoint();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);
        entryPoint.commence(request, response, authException);
        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !!");
    }
}
