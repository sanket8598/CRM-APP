package ai.rnt.crm.util;

import java.text.NumberFormat;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyUtil {

	private CurrencyUtil() {

	}

	public static String CommaSepAmount(double amount) {
		String formattedAmount ="0.00";
		try {
		Locale india = new Locale("en", "IN");
		NumberFormat formatter = NumberFormat.getNumberInstance(india);
		 formattedAmount = formatter.format(amount);
	}catch(Exception e) {
		log.error("Got Exceptionwhile converting amount in Indian rupees format...{}" ,e.getMessage());
	}
		return formattedAmount;
	}
}
