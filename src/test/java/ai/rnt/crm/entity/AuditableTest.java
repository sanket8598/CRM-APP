package ai.rnt.crm.entity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import ai.rnt.crm.security.UserDetail;

class AuditableTest {

	@Test
	void testBeforePersist() {
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		Authentication authentication = mock(Authentication.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		UserDetail userDetail = mock(UserDetail.class);
		when(authentication.getDetails()).thenReturn(userDetail);
		when(userDetail.getStaffId()).thenReturn(123); // Set whatever staff ID you want for testing
		Auditable auditable = new Auditable() {
		};
		auditable.beforPersist();
	}

	@Test
	void testBeforeUpdate() {
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		Authentication authentication = mock(Authentication.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		UserDetail userDetail = mock(UserDetail.class);
		when(authentication.getDetails()).thenReturn(userDetail);
		when(userDetail.getStaffId()).thenReturn(123); // Set whatever staff ID you want for testing
		Auditable auditable = new Auditable() {
		};
		auditable.beforUpdate();
		auditable.getUpdatedBy();
		auditable.setUpdatedBy(1375);
	}

	@Test
	void testBeforeDelete() {
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		Authentication authentication = mock(Authentication.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		UserDetail userDetail = mock(UserDetail.class);
		when(authentication.getDetails()).thenReturn(userDetail);
		when(userDetail.getStaffId()).thenReturn(123); // Set whatever staff ID you want for testing
		Auditable auditable = new Auditable() {
		};
		auditable.beforDelete();
	}
}
