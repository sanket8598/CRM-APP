package ai.rnt.crm.constants;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class SchedularConstant {

	public static final String INDIA_ZONE = "Asia/Kolkata";
	public static final String EVERY_DAY_11_AM = "0 0 11 * * ?";
	public static final String EVERY_1_HOUR = "0 * * * *";
}
