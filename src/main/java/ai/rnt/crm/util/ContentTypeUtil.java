package ai.rnt.crm.util;

import static lombok.AccessLevel.PRIVATE;

import java.util.HashMap;
import java.util.Map;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class ContentTypeUtil {

	private static Map<String, String> map;
	static {
		map = new HashMap<>();
		map.put("audio/aac", "AUDIO");
		map.put("application/x-abiword", "WORD");
		map.put("application/x-freearc", "ZIP");
		map.put("video/x-msvideo", "AV");
		map.put("application/vnd.amazon.ebook", "AmazKindleBook");
		map.put("application/octet-stream", "BFILE");
		map.put("text/css", "CSS");
		map.put("text/csv", "CSV");
		map.put("application/msword", "WORD");
		map.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "WORD");
		map.put("image/gif", "GIF");
		map.put("text/html", "HTML");
		map.put("text/calendar", "ICALANDER");
		map.put("application/java-archive", "JAR");
		map.put("image/vnd.microsoft.icon", "IMAGE");
		map.put("image/bmp", "IMAGE");
		map.put("image/avif", "IMAGE");
		map.put("image/jpeg", "IMAGE");
		map.put("image/png", "IMAGE");
		map.put("image/webp", "IMAGE");
		map.put("image/svg+xml", "IMAGE");
		map.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "EXCEL");
		map.put("application/vnd.ms-excel", "EXCEL");
		map.put("text/plain", "PLAIN TEXT");
		map.put("application/pdf", "PDF");
		map.put("application/vnd.ms-powerpoint", "PPT");
		map.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "PPT");
		map.put("application/xml", "XML");
		map.put("application/atom+xml", "XML");
		map.put("application/zip", "ZIP");
		map.put("application/x-7z-compressed", "ZIP");
		map.put("application/x-tar", "TAR");
	}

	public static String getContentTypeName(String contentType) {
		log.info("inside the getContentTypeName method...{}", contentType);
		return map.containsKey(contentType) ? map.get(contentType) : "OTHER";
	}
}
