package ai.rnt.crm.util;

import static com.ibm.icu.text.NumberFormat.getCurrencyInstance;
import static java.lang.String.valueOf;
import static lombok.AccessLevel.PRIVATE;

import java.text.Format;
import java.util.Locale;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class CurrencyUtil {

	private static final int ASCI_CODE_160 = 160;
	private static final Locale LOCAL_IN = new Locale("en", "in");
	private static final Format NUMBER_FORMAT = getCurrencyInstance(LOCAL_IN);

	public static String commaSepAmount(double amount) {
		log.info("inside the commaSepAmount method...{}", amount);
		StringBuilder sb = new StringBuilder();
		String amountInString = NUMBER_FORMAT.format(amount);
		sb.append(amountInString);
		sb = sb.replace(0, 1, "");
		try {
			sb = sb.deleteCharAt(sb.indexOf(valueOf((char) ASCI_CODE_160)));
		} catch (Exception e) {
			log.error("Got Exception while converting amount in Indian rupees format...{}", e.getMessage());
		}
		return sb.toString();
	}

}
