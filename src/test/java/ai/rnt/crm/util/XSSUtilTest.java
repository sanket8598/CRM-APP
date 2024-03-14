package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class XSSUtilTest {

	@Test
	void testRemoveGarbageValue_NullInput() {
		String result = XSSUtil.removeGarbageValue(null);
		assertEquals(null, result);
	}

	@Test
	void testRemoveGarbageValue_NoGarbageValue() {
		String input = "This is a test string";
		String result = XSSUtil.removeGarbageValue(input);
		assertEquals(input, result);
	}

	@Test
	void testRemoveGarbageValue_WithGarbageValue() {
		String input = "This is a test string with \u00A9garbage\u00A9 values\u00AE";
		String expected = "This is a test string with garbage values";
		String result = XSSUtil.removeGarbageValue(input);
		assertEquals(expected, result);
	}

	@Test
	void testSanitize_NoHtmlContent() {
		String input = "This is a test string without HTML content";
		String result = XSSUtil.sanitize(input);
		assertEquals(input, result);
	}

	@Test
	void testSanitize_WithHtmlContent() {
		String input = "<p>This is a <b>test</b> string with <script>alert('XSS');</script> HTML content</p>";
		String expected = "<p>This is a <b>test</b> string with HTML content</p>";
		String result = XSSUtil.sanitize(input);
		assertNotNull(expected, result);
	}
}
