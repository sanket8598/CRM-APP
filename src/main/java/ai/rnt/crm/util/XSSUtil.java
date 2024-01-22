package ai.rnt.crm.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.Objects;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

import lombok.NoArgsConstructor;

/**
 * @author Sanket Wakankar
 * @since 22/01/2024
 * @version 1.0
 *
 */

@NoArgsConstructor(access = PRIVATE)
public class XSSUtil {
	private static final PolicyFactory POLICY = new HtmlPolicyBuilder().toFactory();

	public static String sanitize(String input) {
		return POLICY.sanitize(input);
	}

	public static String removeGarbageValue(String str) {
		return Objects.nonNull(str) ? str.replaceAll("[^\\x00-\\x7F]", "") : str;// to remove garbage value
	}
}
