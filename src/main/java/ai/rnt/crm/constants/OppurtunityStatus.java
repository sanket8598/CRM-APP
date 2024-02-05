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

	public static final String QUALIFY = "(In-Pipeline) Qualify";

	public static final String DEVELOP = "(In-Pipeline) Develop";

	public static final String PROPOSE = "(In-Pipeline) Propose";

	public static final String CLOSE = "(In-Pipeline) Close";

	public static final String WON = "Won";

	public static final String LOST = "Lost";

}
