package ai.rnt.crm.security.config;

import static ai.rnt.crm.constants.SecurityConstant.TOKEN_PREFIX_BEARER;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerExceptionResolver;

import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;

class JwtAuthenticationFilterTest {

	@Mock
    private CustomUserDetails customUserDetails;
    
	@Mock
    private JWTTokenHelper jwtTokenHelper;
    
    @Mock
    private HandlerExceptionResolver exceptionResolver;
    
    @Mock
    private FilterChain filterChain;
    
    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtTokenHelper=mock(JWTTokenHelper.class);
        SecurityContextHolder.clearContext();
    }
    
    private static final String VALID_TOKEN = TOKEN_PREFIX_BEARER + " validToken";
    private static final String USERNAME = "user";

    @Test
    void shouldContinueFilterChainOnNotAllowedUrl() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("/nonallowed-path");
        MockHttpServletResponse response = new MockHttpServletResponse();
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(0)).doFilter(request, response);
    }
    @Test
    void shouldContinueFilterChainOnAllowedUrl() throws ServletException, IOException {
    	MockHttpServletRequest request = new MockHttpServletRequest();
    	request.setServletPath("/swagger");
    	MockHttpServletResponse response = new MockHttpServletResponse();
    	jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
    	verify(filterChain, times(1)).doFilter(request, response);
    }
    
    @Test
    void shouldAuthenticateUserWithValidToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(AUTHORIZATION, VALID_TOKEN);
        MockHttpServletResponse response = new MockHttpServletResponse();
        when(jwtTokenHelper.extractUsername(anyString())).thenReturn(anyString());
    //    when(jwtTokenHelper.extractClaim(anyString(), any())).thenReturn(true);
        Collection<? extends GrantedAuthority> list=new ArrayList<>();
        UserDetail userDetail = new UserDetail(USERNAME, USERNAME, false, false, false, false, list, 1, USERNAME); 
        when(customUserDetails.loadUserByUsername(USERNAME)).thenReturn(userDetail);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain, times(0)).doFilter(request, response);
    }
}
