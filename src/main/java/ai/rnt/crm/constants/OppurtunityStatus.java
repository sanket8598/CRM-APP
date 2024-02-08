package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * @author Sanket Wakankar
 * @since 03-02-2024.
 * @version 1.0
 *
 */
@NoArgsConstructor(access = PRIVATE)
public class OppurtunityStatus {

	public static final String OPEN = "Open";

	public static final String QUALIFY = "Qualify";

	public static final String ANALYSIS = "Analysis";

	public static final String PROPOSE = "Propose";

	public static final String CLOSE = "Close";

	public static final String WON = "Won";

	public static final String LOST = "Lost";
	
	public static final String IN_PIPELINE = "in-pipeline";

}
