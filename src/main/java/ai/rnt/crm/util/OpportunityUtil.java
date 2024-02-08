package ai.rnt.crm.util;

import static ai.rnt.crm.constants.OppurtunityStatus.ANALYSIS;
import static ai.rnt.crm.constants.OppurtunityStatus.CLOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.PROPOSE;
import static ai.rnt.crm.constants.OppurtunityStatus.QUALIFY;
import static java.lang.String.valueOf;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class OpportunityUtil {

	private static String[] c = new String[] { "K", "L", "Cr" };

	public static String amountInWords(double n) {
		long wholePart = (long) n;
		double fractionalPart = n - wholePart;
		int size = valueOf(wholePart).length();
		if (size <= 3) {
			return format((wholePart + fractionalPart) / 1000.0, c[0]);
		} else if (size >= 4 && size < 6) {
			return format((wholePart + fractionalPart) / 1000.0, c[0]);
		} else if (size >= 6 && size < 8) {
			return format((wholePart + fractionalPart) / 100000.0, c[1]);
		} else if (size >= 8) {
			return format((wholePart + fractionalPart) / 10000000.0, c[2]);
		} else {
			return valueOf(n);
		}
	}

	private static String format(double value, String suffix) {
		return String.format("%.1f%s", value, suffix);
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
