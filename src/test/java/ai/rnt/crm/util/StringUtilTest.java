package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringUtilTest {

	@Test
	public void testHasWhitespace_WithWhitespace() {
		assertTrue(StringUtil.hasWhitespace("Hello World"));
	}

	@Test
	public void testHasWhitespace_WithoutWhitespace() {
		assertFalse(StringUtil.hasWhitespace("HelloWorld"));
	}

	@Test
	public void testHasWhitespace_EmptyInput() {
		assertFalse(StringUtil.hasWhitespace(""));
	}

	@Test
	public void testSplitByWhitespace_WithWhitespace() {
		String text = "Hello World";
		String[] result = StringUtil.splitByWhitespace(text);
		assertArrayEquals(new String[] { "Hello", "World" }, result);
	}

	@Test
	public void testSplitByWhitespace_WithoutWhitespace() {
		String text = "HelloWorld";
		String[] result = StringUtil.splitByWhitespace(text);
		assertArrayEquals(new String[] { "HelloWorld" }, result);
	}

	@Test
	public void testSplitByWhitespace_EmptyInput() {
		String[] result = StringUtil.splitByWhitespace("");
		assertArrayEquals(new String[] { "" }, result);
	}

}
