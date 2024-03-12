package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class JwtTokenDecoderTest {

	@Test
	void testDecodeValidJWT() {
		JwtTokenDecoder decoder = new JwtTokenDecoder();
		String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
		String expectedDecodedToken = "{\"sub\":\"1234567890\",\"name\":\"John Doe\",\"iat\":1516239022}";
		assertEquals(expectedDecodedToken, decoder.testDecodeJWT(token));
	}

	@Test
	void testDecodeInvalidJWT() {
		JwtTokenDecoder decoder = new JwtTokenDecoder();
		String token = "invalid.token.format";
		assertThrows(IllegalStateException.class, () -> decoder.testDecodeJWT(token));
	}

}
