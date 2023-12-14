package ai.rnt.crm.util;

import java.util.regex.Pattern;

public class StringUtil {

	public static boolean hasWhitespace(String text) {
		return Pattern.compile("\\s+").matcher(text).find();
	}

	public static String[] splitByWhitespace(String text) {
        return Pattern.compile("\\s+").split(text);
    }
}
