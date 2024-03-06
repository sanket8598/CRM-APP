package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CurrencyUtilTest {

	@Test
	void testCommaSepAmount() {
		double amount = 123456.78;
		String expectedFormattedAmount = "1,23,456.78";
		String actualFormattedAmount = CurrencyUtil.commaSepAmount(amount);
		assertEquals(expectedFormattedAmount, actualFormattedAmount);
	}

	@Test
	void testCommaSepAmount_Zero() {
		double amount = 0;
		String expectedFormattedAmount = "0.00";
		String actualFormattedAmount = CurrencyUtil.commaSepAmount(amount);
		assertEquals(expectedFormattedAmount, actualFormattedAmount);
	}

	@Test
	void testCommaSepAmount_Negative() {
		double amount = 1234.56;
		String expectedFormattedAmount = "1,234.56";
		String actualFormattedAmount = CurrencyUtil.commaSepAmount(amount);
		assertEquals(expectedFormattedAmount, actualFormattedAmount);
	}
}
