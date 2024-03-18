package ai.rnt.crm.util;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ai.rnt.crm.security.JWTTokenHelper;
import ai.rnt.crm.security.UserDetail;

class AuditAwareUtilTest {

	@Mock
	private JWTTokenHelper jwtTokenHelper;

	@Autowired
	MockMvc mockMvc;

	@InjectMocks
	private AuditAwareUtil auditAwareUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(auditAwareUtil).build();
	}

	@Test
	void testGetLoggedInStaffId() {
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		Collection<GrantedAuthority> authorities = Collections.singleton(authority);
		UserDetail userDetails = new UserDetail("ng1477", "123", true, true, true, true, authorities, 1477,
				"n.test@rnt.ai");
		Authentication authentication = mock(Authentication.class);
		when(authentication.getDetails()).thenReturn(userDetails);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Integer loggedInStaffId = auditAwareUtil.getLoggedInStaffId();
		assertEquals(1477, loggedInStaffId);
	}

	@Test
	void testGetLoggedInUserRole() {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
		List<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);
		UserDetail userDetails = new UserDetail("ng1477", "123", true, true, true, true, authorities, 1477,
				"n.test@rnt.ai");
		Authentication authentication = mock(Authentication.class);
		when(authentication.getDetails()).thenReturn(userDetails);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		String loggedInUserRole = auditAwareUtil.getLoggedInUserRole();
		assertEquals("Don't Have Role", loggedInUserRole);
	}

	@Test
	void testGetLoggedInUserName() {
		String expectedUserName = "John Doe";
		String token = "sampleToken";
		Authentication authentication = mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(token);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		when(jwtTokenHelper.getfullNameOfLoggedInUser(token)).thenReturn(expectedUserName);
		String loggedInUserName = auditAwareUtil.getLoggedInUserName();
		assertEquals(expectedUserName, loggedInUserName);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testIsAdminWithAdminRole() {
		Authentication authentication = mock(Authentication.class);
		UserDetail userDetails = mock(UserDetail.class);
		when(authentication.getDetails()).thenReturn(userDetails);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		GrantedAuthority adminAuthority = mock(GrantedAuthority.class);
		when(adminAuthority.getAuthority()).thenReturn("CRM Admin");
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(adminAuthority);
		when(userDetails.getAuthorities()).thenReturn((Collection<GrantedAuthority>) authorities);
		assertTrue(auditAwareUtil.isAdmin());
	}

	@SuppressWarnings("unchecked")
	@Test
	void testIsUserWithUserRole() {
		Authentication authentication = mock(Authentication.class);
		UserDetail userDetails = mock(UserDetail.class);
		when(authentication.getDetails()).thenReturn(userDetails);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		GrantedAuthority adminAuthority = mock(GrantedAuthority.class);
		when(adminAuthority.getAuthority()).thenReturn("CRM User");
		Collection<? extends GrantedAuthority> authorities = Arrays.asList(adminAuthority);
		when(userDetails.getAuthorities()).thenReturn((Collection<GrantedAuthority>) authorities);
		assertTrue(auditAwareUtil.isUser());
	}
}
