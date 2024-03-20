package ai.rnt.crm.security.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public final class NoOpPasswordEncoder implements PasswordEncoder {

	private static final PasswordEncoder INSTANCE = new NoOpPasswordEncoder();

	private NoOpPasswordEncoder() {
	}

	@Override
	public String encode(CharSequence rawPassword) {
		return rawPassword.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		return rawPassword.toString().equals(encodedPassword);
	}

	/**
	 * Get the singleton {@link NoOpPasswordEncoder}.
	 */
	public static PasswordEncoder getInstance() {
		return INSTANCE;
	}

}
