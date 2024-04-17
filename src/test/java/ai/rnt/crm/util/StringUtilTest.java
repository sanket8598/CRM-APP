package ai.rnt.crm.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringUtilTest {

	@Test
	void testHasWhitespace_WithWhitespace() {
		assertTrue(StringUtil.hasWhitespace("Hello World"));
	}

	@Test
	void testHasWhitespace_WithoutWhitespace() {
		assertFalse(StringUtil.hasWhitespace("HelloWorld"));
	}

	@Test
	void testHasWhitespace_EmptyInput() {
		assertFalse(StringUtil.hasWhitespace(""));
	}

	@Test
	void testSplitByWhitespace_WithWhitespace() {
		String text = "Hello World";
		String[] result = StringUtil.splitByWhitespace(text);
		assertArrayEquals(new String[] { "Hello", "World" }, result);
	}

	@Test
	void testSplitByWhitespace_WithoutWhitespace() {
		String text = "HelloWorld";
		String[] result = StringUtil.splitByWhitespace(text);
		assertArrayEquals(new String[] { "HelloWorld" }, result);
	}

	@Test
	void testSplitByWhitespace_EmptyInput() {
		String[] result = StringUtil.splitByWhitespace("");
		assertArrayEquals(new String[] { "" }, result);
	}

	@Test
	void testRandomNumberGenerator() {
		String generatedNumber = StringUtil.randomNumberGenerator();
		assertNotNull(generatedNumber);
		assertTrue(generatedNumber.startsWith("RNT-"));
		assertEquals(10, generatedNumber.length());
	}
}
