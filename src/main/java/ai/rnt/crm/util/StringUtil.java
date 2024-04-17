package ai.rnt.crm.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.Random;
import java.util.regex.Pattern;

import ai.rnt.crm.exception.CRMException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class StringUtil {

	private static final Random random = new Random();

	public static boolean hasWhitespace(String text) {
		log.info("inside the hasWhitespace method...{}", text);
		return Pattern.compile("\\s+").matcher(text).find();
	}

	public static String[] splitByWhitespace(String text) {
		log.info("inside the splitByWhitespace method...{}", text);
		return Pattern.compile("\\s+").split(text);
	}

	public static String randomNumberGenerator() {
		log.info("inside the randomNumberGenerator method");
		try {
			String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			int length = 6;
			String prefix = "RNT-";
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < length; i++) {
				int randomIndex = random.nextInt(characters.length());
				stringBuilder.append(characters.charAt(randomIndex));
			}
			return prefix + stringBuilder.toString();
		} catch (Exception e) {
			log.error("Got exception while generating proposal id..{}", e.getMessage());
			throw new CRMException(e);
		}
	}
}
