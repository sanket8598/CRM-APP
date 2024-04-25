package ai.rnt.crm.util;

import static ai.rnt.crm.constants.OppurtunityStatus.ANALYSIS;
import static ai.rnt.crm.constants.OppurtunityStatus.CLOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.PROPOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.QUALIFY;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * @author Nikhil Gaikwad
 * @since 08/02/2024
 * @version 2.0
 *
 */
@NoArgsConstructor(access = PRIVATE)
public class OpportunityUtil {

	private static String[] suffix = new String[] { "K", "L", "Cr" };

	public static String amountInWords(double n) {
		long wholePart = (long) n;
		double fractionalPart = n - wholePart;
		int size = valueOf(wholePart).length();
	  if (size <= 3) {
			return formatAmount((wholePart + fractionalPart) / 1000.0, suffix[0]);
		} else if (size >= 4 && size < 6) {
			return formatAmount((wholePart + fractionalPart) / 1000.0, suffix[0]);
		} else if (size >= 6 && size < 8) {
			return formatAmount((wholePart + fractionalPart) / 100000.0, suffix[1]);
		} else if (size >= 8) {
			return formatAmount((wholePart + fractionalPart) / 10000000.0, suffix[2]);
		} else {
			return valueOf(n);
		}
	}

	private static String formatAmount(double value, String suffix) {
		return format("%.1f%s", value, suffix);
	}

	public static Double calculateBubbleSize(double opportunityBudget, double totalBudget) {
		return (opportunityBudget / totalBudget) * 100.0;
	}

	public static String checkPhase(String status) {
		switch (status) {
		case QUALIFY:
			return "Phase 1";
		case ANALYSIS:
			return "Phase 2";
		case PROPOSE:
			return "Phase 3";
		case CLOSE:
			return "Phase 4";
		default:
			return null;
		}
	}
}
