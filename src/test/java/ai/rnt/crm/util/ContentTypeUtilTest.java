package ai.rnt.crm.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ContentTypeUtilTest {

	 @Test
	    void testGetContentTypeName() {
	        assertEquals("PDF", ContentTypeUtil.getContentTypeName("application/pdf"));
	        assertEquals("WORD", ContentTypeUtil.getContentTypeName("application/msword"));
	        assertEquals("ZIP", ContentTypeUtil.getContentTypeName("application/zip"));
	        assertEquals("IMAGE", ContentTypeUtil.getContentTypeName("image/jpeg"));
	        assertEquals("AUDIO", ContentTypeUtil.getContentTypeName("audio/aac"));
	        assertEquals("PLAIN TEXT", ContentTypeUtil.getContentTypeName("text/plain"));
	        assertEquals("HTML", ContentTypeUtil.getContentTypeName("text/html"));
	        assertEquals("CSV", ContentTypeUtil.getContentTypeName("text/csv"));
	        assertEquals("GIF", ContentTypeUtil.getContentTypeName("image/gif"));
	        assertEquals("ICALANDER", ContentTypeUtil.getContentTypeName("text/calendar"));
	        assertEquals("JAR", ContentTypeUtil.getContentTypeName("application/java-archive"));
	        assertEquals("EXCEL", ContentTypeUtil.getContentTypeName("application/vnd.ms-excel"));
	        assertEquals("EXCEL", ContentTypeUtil.getContentTypeName("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
	        assertEquals("XML", ContentTypeUtil.getContentTypeName("application/xml"));
	        assertEquals("TAR", ContentTypeUtil.getContentTypeName("application/x-tar"));
	        assertEquals("OTHER", ContentTypeUtil.getContentTypeName("application/unknown"));
	    }
}
