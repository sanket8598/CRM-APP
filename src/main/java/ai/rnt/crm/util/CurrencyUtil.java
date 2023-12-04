package ai.rnt.crm.util;

import java.text.Format;
import java.util.Locale;

import com.ibm.icu.text.NumberFormat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CurrencyUtil {

	private CurrencyUtil() {

	}
	
	private static final int ASCI_CODE_160 = 160;
	private static final Locale LOCAL_IN = new Locale("en", "in");
	private static final Format NUMBER_FORMAT = NumberFormat.getCurrencyInstance(LOCAL_IN);

	public static String commaSepAmount(double amount) {
		StringBuilder sb = new StringBuilder();
		String amountInString = NUMBER_FORMAT.format(amount);
		sb.append(amountInString);
		sb = sb.replace(0, 1, "");
		try {
			sb = sb.deleteCharAt(sb.indexOf(String.valueOf((char) ASCI_CODE_160)));
		} catch (Exception e) {
			log.error("Got Exceptionwhile converting amount in Indian rupees format...{}" ,e.getMessage());
		}
		return sb.toString();
	}
	
}
