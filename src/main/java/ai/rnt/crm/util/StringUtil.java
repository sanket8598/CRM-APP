package ai.rnt.crm.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.regex.Pattern;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor(access = PRIVATE)
@Slf4j
public class StringUtil {

	public static boolean hasWhitespace(String text) {
		log.info("inside the hasWhitespace method...{}", text);
		return Pattern.compile("\\s+").matcher(text).find();
	}

	public static String[] splitByWhitespace(String text) {
		log.info("inside the splitByWhitespace method...{}", text);
		return Pattern.compile("\\s+").split(text);
	}
}
