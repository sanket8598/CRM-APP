package ai.rnt.crm.security.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

class NoOpPasswordEncoderTest {

	@Test
    void testEncode() {
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        assertEquals(rawPassword, encodedPassword, "Encoded password should be same as raw password");
    }

    @Test
    void testMatches() {
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        String rawPassword = "password";
        String encodedPassword = "password"; // Same as raw password since it's a NoOpPasswordEncoder
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), "Password should match");
    }

    @Test
    void testGetInstance() {
        PasswordEncoder passwordEncoder1 = NoOpPasswordEncoder.getInstance();
        PasswordEncoder passwordEncoder2 = NoOpPasswordEncoder.getInstance();
        assertSame(passwordEncoder1, passwordEncoder2, "getInstance should return the same instance");
    }
}
