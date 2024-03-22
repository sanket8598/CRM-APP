package ai.rnt.crm.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class UserDetailTest {

	@Test
    void testUserDetail_Constructor_SetsFieldsCorrectly() {
        String username = "testUser";
        String password = "password123";
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        Integer staffId = 1234;
        String emailId = "testuser@example.com";

        UserDetail userDetail = new UserDetail(
                username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities, staffId, emailId);
        userDetail.setStaffId(1);
        userDetail.setEmailId("ADSA");
        userDetail.getStaffId();
        userDetail.getEmailId();
        userDetail.getAuthorities();
        userDetail.hashCode();
        userDetail.equals(userDetail);
        userDetail.canEqual(userDetail);
        assertEquals(username, userDetail.getUsername());
        assertEquals(password, userDetail.getPassword());
        assertTrue(userDetail.isEnabled());
        assertTrue(userDetail.isAccountNonExpired());
        assertTrue(userDetail.isCredentialsNonExpired());
        assertTrue(userDetail.isAccountNonLocked());
    }
}
